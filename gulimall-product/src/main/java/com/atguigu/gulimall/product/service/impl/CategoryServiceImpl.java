package com.atguigu.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.atguigu.gulimall.product.vo.Catalog2VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {
    @Autowired
    CategoryBrandRelationService categoryBrandRelationService;
//    @Autowired
//    StringRedisTemplate redisTemplate;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
//        1.查出所有分类
        List<CategoryEntity> entities = baseMapper.selectList(null);
//        2.组装成父子的树形结构

//        2.1找到所有的一级分类
//        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) ->
//                categoryEntity.getParentCid() == 0
//        ).collect(Collectors.toList());
//        return level1Menus;

//        2.2给一级分类children插入值并排序
        List<CategoryEntity> level1Menus = entities.stream().filter((categoryEntity) ->
                categoryEntity.getParentCid() == 0
        )
                .map((menu) -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted(
                        (menu1, menu2) -> {
                            return (menu1.getSort() == null ? 0 : menu1.getSort() - (menu2.getSort() == null ? 0 : menu2.getSort()));
                        }
                )
                .collect(Collectors.toList());
        return level1Menus;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {

//TODO        检查id是否被其他地方引用
        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        ArrayList<Long> paths = new ArrayList<>();
        List<Long> parantPath = findParantPaths(catelogId, paths);
        Collections.reverse(parantPath);
        return  parantPath.toArray(new Long[parantPath.size()]);
    }

    /**
     * 级联更新所有关联数据
     * @param category
     */
    @Transactional
    @Override
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());

    }

    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        List<CategoryEntity> categoryEntities = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid" , 0));
        return categoryEntities;
    }

    private List<Long> findParantPaths(Long catelogId, List<Long> paths) {
//        1.收集当前节点Id
        paths.add(catelogId);
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParantPaths(byId.getParentCid(), paths);
        }
        return paths;
    }


    /**
     * 查询三级分类并封装成Map返回
     * 使用SpringCache注解方式简化缓存设置
     */
    @Cacheable(value = {"category"}, key = "'getCatalogJson'", sync = true)
    @Override
    public Map<String, List<Catalog2VO>> getCatalogJsonWithSpringCache() {
        // 未命中缓存
        // 1.double check，占锁成功需要再次检查缓存（springcache使用本地锁）
        // 查询非空即返回
//        String catlogJSON = redisTemplate.opsForValue().get("getCatalogJson");
//        if (!StringUtils.isEmpty(catlogJSON)) {
//            // 查询成功直接返回不需要查询DB
//            Map<String, List<Catalog2VO>> result = JSON.parseObject(catlogJSON, new TypeReference<Map<String, List<Catalog2VO>>>() {
//            });
//            return result;
//        }

        // 2.查询所有分类，按照parentCid分组
        Map<Long, List<CategoryEntity>> categoryMap = baseMapper.selectList(null).stream()
                .collect(Collectors.groupingBy(key -> key.getParentCid()));

        // 3.获取1级分类
        List<CategoryEntity> level1Categorys = categoryMap.get(0L);

        // 4.封装数据
        Map<String, List<Catalog2VO>> result = level1Categorys.stream().collect(Collectors.toMap(key -> key.getCatId().toString(), l1Category -> {
            // 5.查询2级分类，并封装成List<Catalog2VO>
            List<Catalog2VO> catalog2VOS = categoryMap.get(l1Category.getCatId())
                    .stream().map(l2Category -> {
                        // 7.查询3级分类，并封装成List<Catalog3VO>
                        List<Catalog2VO.Catalog3Vo> catalog3Vos = categoryMap.get(l2Category.getCatId())
                                .stream().map(l3Category -> {
                                    // 封装3级分类VO
                                    Catalog2VO.Catalog3Vo catalog3Vo = new Catalog2VO.Catalog3Vo(l2Category.getCatId().toString(), l3Category.getCatId().toString(), l3Category.getName());
                                    return catalog3Vo;
                                }).collect(Collectors.toList());
                        // 封装2级分类VO返回
                        Catalog2VO catalog2VO = new Catalog2VO(l1Category.getCatId().toString(), catalog3Vos, l2Category.getCatId().toString(), l2Category.getName());
                        return catalog2VO;
                    }).collect(Collectors.toList());
            return catalog2VOS;
        }));
        return result;
    }
    /**
     * 递归查找所有菜单的子菜单
     */
    public List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == root.getCatId();
        }).map(categoryEntity -> {
//            1.找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));
            return categoryEntity;
        }).sorted((menu1, menu2) -> {
//            2.菜单的排序
            return (menu1.getSort() == null ? 0 : menu1.getSort()) - (menu2.getSort() == null ? 0 : menu2.getSort());
        }).collect(Collectors.toList());
        return children;
    }
}