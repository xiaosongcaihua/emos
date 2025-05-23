package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbFaceModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface TbFaceModelDao {
    int deleteByPrimaryKey(Integer id);


    int insertSelective(TbFaceModel record);

    TbFaceModel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TbFaceModel record);

    int updateByPrimaryKey(TbFaceModel record);
    public String searchFaceModel(int userId);
    public void insert(TbFaceModel faceModelEntity);
    public int deleteFaceModel(int userId);
}