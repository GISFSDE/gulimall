package com.atguigu.gulimall.member.dao;

import com.atguigu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author lxl
 * @email 714416426@qq.com
 * @date 2022-05-09 19:12:55
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
