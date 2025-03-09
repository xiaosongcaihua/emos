package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbCheckin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Mapper
public interface TbCheckinDao {
    int deleteByPrimaryKey(Integer id);

    void insert(TbCheckin entity);

    int insertSelective(TbCheckin record);

    TbCheckin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCheckin record);

    int updateByPrimaryKey(TbCheckin record);

    Integer haveCheckin(HashMap param);
}