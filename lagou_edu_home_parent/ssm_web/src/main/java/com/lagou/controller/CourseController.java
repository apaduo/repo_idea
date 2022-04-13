package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseVO;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /*
        多条件课程列表查询
     */
    @RequestMapping("/findCourseByCondition")
    public ResponseResult findCourseCondition(@RequestBody CourseVO courseVO) {
        //调用service
        List<Course> courses = courseService.findCourseByCondition(courseVO);
        return new ResponseResult(true, 200, "响应成功", courses);
    }

    /*
        课程图片上传
     */
    @RequestMapping("/courseUpload")
    public ResponseResult fileUpload(@RequestParam("file") MultipartFile file,
                                     HttpServletRequest request) throws IOException {
        //1.判断接收到的上传文件是否为空
        if (file.isEmpty()){
            throw  new RuntimeException();
        }

        //2.获取项目部署路径
        //D:\apache-tomcat-8.5.56\webapps\ssm_web\
        String realPath = request.getServletContext().getRealPath("/");
        //D:\apache-tomcat-8.5.56\webapps
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));

        //3.获取原文件名
        String originalFilename = file.getOriginalFilename();

        //4.生成新文件名
        String newFileName = System.currentTimeMillis() +
                originalFilename.substring(originalFilename.lastIndexOf("."));

        //5.文件上传
        String uploadPath = substring + "upload\\";

        File filePath = new File(uploadPath, newFileName);

        //如果目录不存在就创建目录
        if (!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录:" + filePath);
        }

        //图片进行了真正的上传
        file.transferTo(filePath);

        //6.将文件名和文件路径返回,进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload/" + newFileName);

        return new ResponseResult(true,200,"图片上传成功",map);
    }

    /*
        新增课程信息及讲师信息
        新增课程信息和修改课程信息要写在同一个方法中
     */
    @RequestMapping("/saveOrUpdateCourse")
    public ResponseResult saveOrUpdateCourse(@RequestBody CourseVO courseVO) throws InvocationTargetException, IllegalAccessException {
        if (courseVO.getId() == null){
            //调用service
            courseService.saveCourseOrTeacher(courseVO);
            return new ResponseResult(true,200,"新增成功",null);
        }else{
            //调用service
            courseService.updateCourseOrTeacher(courseVO);
            return new ResponseResult(true,200,"更新成功",null);
        }
    }

    /*
        回显课程信息(根据ID查询课程信息及关联的讲师信息)
     */
    @RequestMapping("/findCourseById")
    public ResponseResult findCourseById(Integer id){
        //调用service
        CourseVO courseVO = courseService.findCourseById(id);

        return new ResponseResult(true,200,"根据ID查询课程信息成功",courseVO);
    }

    /*
        更新课程状态
     */
    @RequestMapping("/updateCourseStatus")
    public ResponseResult updateCourseStatus(Integer id,Integer status){
        courseService.updateCourseStatus(id,status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"课程状态更新成功",map);
    }
}
