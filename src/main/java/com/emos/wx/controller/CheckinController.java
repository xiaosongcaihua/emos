package com.emos.wx.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.emos.wx.common.util.R;
import com.emos.wx.config.SystemConstants;
import com.emos.wx.config.shiro.JwtUtil;
import com.emos.wx.controller.form.CheckinForm;
import com.emos.wx.exception.EmosException;
import com.emos.wx.service.CheckinService;
import com.emos.wx.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

@RequestMapping("/checkin")
@RestController
@Api("签到模块Web接口")
@Slf4j
public class CheckinController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CheckinService checkinService;

    @Value("${emos.image-folder}")
    private String imageFolder;

    @Autowired
    private UserService userService;
    @Autowired
    private SystemConstants systemConstants;

    @GetMapping("/validCanCheckIn")
    @ApiOperation("查看用户今天是否可以签到")
    public R validCanCheckIn(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        String result = checkinService.validCanCheckIn(userId, DateUtil.today());
        return R.ok(result);
    }
    @PostMapping("/createFaceModel")
    @ApiOperation("创建人脸模型")
    public R createFaceModel(@RequestParam("photo") MultipartFile file, @RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        if (file==null) {
            return R.error("没有上传文件");
        }
        String fileName = file.getOriginalFilename().toLowerCase();
        String path = imageFolder + "/" + fileName;
        if (!fileName.endsWith(".jpg")) {
            return R.error("必须提交JPG格式图片");
        } else {
            try {
                file.transferTo(Paths.get(path));
                checkinService.createFaceModel(userId, path);
                return R.ok("人脸建模成功");
            } catch (IOException e) {
                log.error(e.getMessage());
                throw new EmosException("保存图片错误");
            } finally {
                FileUtil.del(path);
            }
        }
    }
    @PostMapping("/checkin")
    @ApiOperation("签到")
    public R checkin(@Valid CheckinForm form, @RequestParam("photo") MultipartFile file, @RequestHeader("token") String token){
        if(file==null){
            return R.error("没有上传文件");
        }
        int userId=jwtUtil.getUserId(token);
        String fileName=file.getOriginalFilename().toLowerCase();
        if(!fileName.endsWith(".jpg")){
            return R.error("必须提交JPG格式图片");
        }
        else{
            String path=imageFolder+"/"+fileName;
            try{
                file.transferTo(Paths.get(path));
                HashMap param=new HashMap();
                param.put("userId",userId);
                param.put("path",path);
                param.put("city",form.getCity());
                param.put("district",form.getDistrict());
                param.put("address",form.getAddress());
                param.put("country",form.getCountry());
                param.put("province",form.getProvince());
                checkinService.checkin(param);
                return R.ok("签到成功");
            }catch (IOException e){
                log.error(e.getMessage(),e);
                throw new EmosException("图片保存错误");
            }
            finally {
                FileUtil.del(path);
            }

        }
    }
    @GetMapping("/searchTodayCheckin")
    @ApiOperation("查询用户当日签到数据")
    /**
     *  1、关联用户表、部门表、当日签到表查询当前用户当前签到信息
     *  2、查询当前用户所有的历史签到天数
     *  3、查询这个周的起始时间和结束时间，如果入职时间小于当前这个周的起始时间，将起始时间更新为入职时间
     *  4、查询这个月的签到情况封装为一个list
     */
    public R searchTodayCheckin(@RequestHeader("token") String token) {
        int userId = jwtUtil.getUserId(token);
        HashMap map = checkinService.searchTodayCheckin(userId);
        map.put("attendanceTime", systemConstants.attendanceTime);
        map.put("closingTime", systemConstants.closingTime);
        long days = checkinService.searchCheckinDays(userId);
        map.put("checkinDays", days);
        DateTime hiredate = DateUtil.parse(userService.searchUserHiredate(userId));
        DateTime startDate = DateUtil.beginOfWeek(DateUtil.date());
        if (startDate.isBefore(hiredate)) {
            startDate = hiredate;
        }
        DateTime endDate = DateUtil.endOfWeek(DateUtil.date());
        HashMap param = new HashMap();
        param.put("startDate", startDate.toString());
        param.put("endDate", endDate.toString());
        param.put("userId", userId);
        ArrayList<HashMap> list = checkinService.searchWeekCheckin(param);
        map.put("weekCheckin", list);
        return R.ok().put("result", map);
    }
}