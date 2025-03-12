package com.emos.wx.service;

import java.util.ArrayList;
import java.util.HashMap;

public interface CheckinService {
    String validCanCheckIn(int userId, String date);
    void checkin(HashMap param);
    void createFaceModel(int userId, String path);

    public HashMap searchTodayCheckin(int userId);
    public long searchCheckinDays(int userId);
    public ArrayList<HashMap> searchWeekCheckin(HashMap param);
}
