package com.emos.wx.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.emos.wx.config.SystemConstants;
import com.emos.wx.db.dao.*;
import com.emos.wx.db.pojo.TbCheckin;
import com.emos.wx.db.pojo.TbFaceModel;
import com.emos.wx.db.pojo.TbUser;
import com.emos.wx.exception.EmosException;
import com.emos.wx.service.CheckinService;
import com.emos.wx.task.EmailTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
@Scope("prototype")
@Slf4j
public class CheckinServiceImpl implements CheckinService {

    @Autowired
    private SystemConstants systemConstants;

    @Autowired
    private TbHolidaysDao holidaysDao;

    @Autowired
    private TbWorkdayDao workdayDao;

    @Autowired
    private TbCheckinDao checkinDao;

    @Autowired
    private TbFaceModelDao faceModelDao;

    @Value("${emos.face.checkinUrl}")
    private String checkinUrl;
    @Value("${emos.face.createFaceModelUrl}")
    private String createFaceModelUrl;

    @Value("${emos.email.system}")
    private String systemEmail;
    @Value("${spring.mail.username}")
    private String emailFrom;


    @Autowired
    private EmailTask emailTask;

    @Autowired
    private TbUserDao userDao;
    @Value("${emos.code}")
    private String code;
    @Override
    public ArrayList<HashMap> searchMonthCheckin(HashMap param) {
        return this.searchWeekCheckin(param);
    }

    @Override
    public String validCanCheckIn(int userId, String date) {
        boolean bool_1 = holidaysDao.searchTodayIsHolidays() != null ? true : false;
        boolean bool_2 = workdayDao.searchTodayIsWorkday() != null ? true : false;
        String type = "工作日";
        if (DateUtil.date().isWeekend()) {
            type = "节假日";
        }
        if (bool_1) {
            type = "节假日";
        } else if (bool_2) {
            type = "工作日";
        }
        if (type.equals("节假日")) {
            return "节假日不需要考勤";
        } else {
            DateTime now = DateUtil.date();
            String start = DateUtil.today() + " " + systemConstants.attendanceStartTime;
            String end = DateUtil.today() + " " + systemConstants.attendanceEndTime;
            DateTime attendanceStart = DateUtil.parse(start);
            DateTime attendanceEnd = DateUtil.parse(end);
            if (now.isBefore(attendanceStart)) {
                return "没有到上班考勤开始时间";
            } else if (now.isAfter(attendanceEnd)) {
                return "超过了上班考勤结束时间";
            } else {
                HashMap map = new HashMap();
                map.put("userId", userId);
                map.put("date", date);
                map.put("start", start);
                map.put("end", end);
                boolean bool = checkinDao.haveCheckin(map) != null ? true : false;
                return bool ? "今日已经考勤， 不用重复考勤" : "可以考勤";
            }
        }
    }

    @Override
    public void checkin(HashMap param) {
        //判断签到
        Date d1 = DateUtil.date(); //当前时间
        Date d2 = DateUtil.parse(DateUtil.today() + " " + systemConstants.attendanceTime); //上班时间
        Date d3 = DateUtil.parse(DateUtil.today() + " " + systemConstants.attendanceEndTime); //签到结束时间
        int status = 1;
        if (d1.compareTo(d2) <= 0) {
            status = 1; // 正常签到
        } else if (d1.compareTo(d2) > 0 && d1.compareTo(d3) < 0) {
            status = 2; //迟到
        }
        //查询签到人的人脸模型数据
        int userId = (Integer) param.get("userId");
        String faceModel = faceModelDao.searchFaceModel(userId);
        if (faceModel == null) {
            throw new EmosException("不存在人脸模型");
        } else {
//            String path = (String) param.get("path");
//            HttpRequest request = HttpUtil.createPost(checkinUrl);
//            request.form("photo", FileUtil.file(path), "targetModel", faceModel);
//            HttpResponse response = request.execute();
//            if (response.getStatus() != 200) {
//                log.error("人脸识别服务异常");
//                throw new EmosException("人脸识别服务异常");
//            }
            int risk = 1;
            String city = (String) param.get("city");
            String district = (String) param.get("district");
            String address = (String) param.get("address");
            String country = (String) param.get("country");
            String province = (String) param.get("province");
            if (!city.equals("北京市")) {
                TbUser tbUser = userDao.searchById(userId);
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(systemEmail);
                message.setSubject("员工" + tbUser.getNickname() + "签到地点不在公司所在区域");
                message.setText("员工" + tbUser.getNickname() + "， " + DateUtil.format(new Date(), "yyyy年MM月dd日") + "处于" + address + "， 不属于公司所在地， 请及时与该员工联系");
                emailTask.sendAsync(message);
            }
            TbCheckin entity = new TbCheckin();
            entity.setUserId(userId);
            entity.setAddress(address);
            entity.setCountry(country);
            entity.setProvince(province);
            entity.setCity(city);
            entity.setDistrict(district);
            entity.setStatus((byte) status);
            entity.setRisk(risk);
            entity.setDate(DateUtil.today());
            entity.setCreateTime(d1);
            checkinDao.insert(entity);
        }
    }

    public void createFaceModel ( int userId, String path){
        HttpRequest request = HttpUtil.createPost(createFaceModelUrl);
        request.form("photo", FileUtil.file(path));
        request.form("code", code);
        HttpResponse response = request.execute();
        String body = response.body();
        if ("无法识别出人脸".equals(body) || "照片中存在多张人脸".equals(body)) {
            throw new EmosException(body);
        } else {
            TbFaceModel entity = new TbFaceModel();
            entity.setUserId(userId);
            entity.setFaceModel(body);
            faceModelDao.insert(entity);
        }
    }
    @Override
    public HashMap searchTodayCheckin(int userId) {
        HashMap map = checkinDao.searchTodayCheckin(userId);
        return map;
    }
    @Override
    public long searchCheckinDays(int userId) {
        long days = checkinDao.searchCheckinDays(userId);
        return days;
    }

    /**
     * 1、查询这个周开始到结束当前用户的签到情况
     * 2、查询这个周开始到结束的假期时间
     * 3、查询这个周开始到结束的工作日时间
     * 4、查询这个月的在这个起始时间到结束时间的天数
     * 4.1 根据这个月的时间根据之前查询到的假期时间和工作时间判断当前天的类型<工作日/节假日>
     * 4.2 如果是工作日那么根据查询的用户签到情况判断当前天是否在这个签到列表里面如果在签到列表里面将status设置为不缺勤否则设置为缺勤
     * 4.3 对今天做一个特殊判断
     * 4.4 将这个月的签到情况做一个返回
     */
    @Override
    public ArrayList<HashMap> searchWeekCheckin(HashMap param) {
        ArrayList<HashMap> checkinList = checkinDao.searchWeekCheckin(param);
        ArrayList<String> holidaysList = holidaysDao.searchHolidaysInRange(param);
        ArrayList<String> workdayList = workdayDao.searchWorkdayInRange(param);
        DateTime startDate = DateUtil.parseDate(param.get("startDate").toString());
        DateTime endDate = DateUtil.parseDate(param.get("endDate").toString());
        DateRange range = DateUtil.range(startDate, endDate, DateField.DAY_OF_MONTH);
        ArrayList list = new ArrayList();
        range.forEach(one -> {
            String date = one.toString("yyyy-MM-dd");
            //查看今天是不是假期或者工作日
            String type = "工作日";
            if (one.isWeekend()) {
                type = "节假日";
            }
            if (holidaysList != null && holidaysList.contains(date)) {
                type = "节假日";
            } else if (workdayList != null && workdayList.contains(date)) {
                type = "工作日";
            }
            String status = "";
            if (type.equals("工作日") && DateUtil.compare(one, DateUtil.date()) <= 0) {
                status = "缺勤";
                boolean flag=false;
                for (HashMap<String, String> map : checkinList) {
                    if (map.containsValue(date)) {
                        status = map.get("status");
                        flag=true;
                        break;
                    }
                }
                DateTime endTime=DateUtil.parse(DateUtil.today()+" "+systemConstants.attendanceEndTime);
                String today=DateUtil.today();
                if(date.equals(today)&&DateUtil.date().isBefore(endTime)&&flag==false){
                    status="";
                }
            }
            HashMap map = new HashMap();
            map.put("date", date);
            map.put("status", status);
            map.put("type", type);
            map.put("day", one.dayOfWeekEnum().toChinese("周"));
            list.add(map);
        });
        return list;
    }
}
