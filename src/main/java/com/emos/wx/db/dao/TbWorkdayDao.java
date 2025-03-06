package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbWorkday;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TbWorkdayDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbWorkday record);

    int insertSelective(TbWorkday record);

    TbWorkday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbWorkday record);

    int updateByPrimaryKey(TbWorkday record);

    Integer searchTodayIsWorkday();
}