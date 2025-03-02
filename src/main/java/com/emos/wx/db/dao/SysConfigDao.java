package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.SysConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface SysConfigDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SysConfig record);

    int insertSelective(SysConfig record);

    SysConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysConfig record);

    int updateByPrimaryKey(SysConfig record);
}