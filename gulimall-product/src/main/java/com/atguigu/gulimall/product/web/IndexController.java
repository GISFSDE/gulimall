package com.atguigu.gulimall.product.web;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.Catalog2VO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: lxl
 * @Date: 2022/09/09/19:12
 * @Description:
 */
@Controller
public class IndexController {
    @Autowired
    CategoryService categoryService;
//    @Autowired
//    RedissonClient redissonClient;

    @GetMapping({"/", "index.html"})
    public String indexPage(Model model) {

        // 查询所有1级分类
        List<CategoryEntity> categoryEntitys = categoryService.getLevel1Categorys();

        //
        model.addAttribute("categorys", categoryEntitys);


        // 解析器自动拼装classpath:/templates/  + index +  .html =》 classpath:/templates/index.html
        // classpath表示类路径，编译前是resources文件夹，编译后resources文件夹内的文件会统一存放至classes文件夹内
        return "index";
    }

    /**
     * 查询三级分类
     * {
     *     "一级分类ID": [
     *         {
     *             "catalog1Id": "一级分类ID",
     *             "id": "二级分类ID",
     *             "name": "二级分类名",
     *             "catalog3List":[
     *                 {
     *                     "catalog2Id": "二级分类ID",
     *                     "id": "三级分类ID",
     *                     "name": "三级分类名"
     *                 }
     *             ]
     *         }
     *     ],
     *     "一级分类ID": [],
     *     "一级分类ID": []
     * }
     */
    @ResponseBody
    @GetMapping("/index/catalog.json")
    public Map<String, List<Catalog2VO>> getCatalogJson() {
        Map<String, List<Catalog2VO>> map = categoryService.getCatalogJson();
        return map;
    }

    @ResponseBody
    @GetMapping("/hello")
    public String hello() {

        return "Hello";
    }


}
