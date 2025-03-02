package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbCity;
import org.springframework.stereotype.Repository;

@Repository
public interface TbCityDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TbCity record);

    int insertSelective(TbCity record);

    TbCity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbCity record);

    int updateByPrimaryKey(TbCity record);
}