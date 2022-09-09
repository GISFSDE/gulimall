package com.atguigu.gulimall.search.service;


import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;

/**
 * <p>Title: MasllService</p>
 * Description：
 * date：2020/6/12 23:05
 */
public interface MallService {

	/**
	 * 检索所有参数
	 * @return
	 */
	SearchResult search(SearchParam Param);
}
