package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbModule;
import org.springframework.stereotype.Repository;

@Repository
public interface TbModuleDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbModule record);

    int insertSelective(TbModule record);

    TbModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbModule record);

    int updateByPrimaryKey(TbModule record);
}