package com.emos.wx.db.dao;

import com.emos.wx.db.pojo.TbMeeting;
import org.springframework.stereotype.Repository;

@Repository
public interface TbMeetingDao {
    int deleteByPrimaryKey(Long id);

    int insert(TbMeeting record);

    int insertSelective(TbMeeting record);

    TbMeeting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TbMeeting record);

    int updateByPrimaryKey(TbMeeting record);
}