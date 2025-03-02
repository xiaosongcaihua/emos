package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbCheckin;
import org.springframework.stereotype.Repository;

@Repository
public interface TbCheckinDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCheckin record);

    int insertSelective(TbCheckin record);

    TbCheckin selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCheckin record);

    int updateByPrimaryKey(TbCheckin record);
}