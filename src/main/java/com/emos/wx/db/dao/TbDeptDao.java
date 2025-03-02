package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbDept;
import org.springframework.stereotype.Repository;

@Repository
public interface TbDeptDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbDept record);

    int insertSelective(TbDept record);

    TbDept selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbDept record);

    int updateByPrimaryKey(TbDept record);
}