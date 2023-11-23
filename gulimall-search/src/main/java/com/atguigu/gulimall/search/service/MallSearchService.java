package com.atguigu.gulimall.search.service;

import com.atguigu.gulimall.search.vo.SearchParam;
import com.atguigu.gulimall.search.vo.SearchResult;

public interface MallSearchService {
    //检索的所有参数,得到最终的检索结果
    SearchResult search(SearchParam param);
}
