package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbHolidays;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TbHolidaysDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbHolidays record);

    int insertSelective(TbHolidays record);

    TbHolidays selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbHolidays record);

    int updateByPrimaryKey(TbHolidays record);
}