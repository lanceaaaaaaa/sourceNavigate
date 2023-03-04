package com.luban.test.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;



public interface TbBrandMapper {

    List<TbBrand> selectByExample(TbBrandExample example);

    int selectByExample(int i);

}