package com.lagou.controller;

import com.lagou.domain.Course;
import com.lagou.domain.CourseLesson;
import com.lagou.domain.CourseSection;
import com.lagou.domain.ResponseResult;
import com.lagou.service.CourseContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/courseContent")
public class CourseContentController {

    @Autowired
    private CourseContentService courseContentService;
    /*
        查询课程内容(章节+课时)
     */
    @RequestMapping("/findSectionAndLesson")
    public ResponseResult findSectionAndLesson(Integer courseId){
        List<CourseSection> sections = courseContentService.findSectionAndLessonByCourseId(courseId);
        return new ResponseResult(true,200,"章节及课时内容查询成功",sections);
    }

    /*
        回显章节对应的课程信息
     */
    @RequestMapping("/findCourseByCourseId")
    public ResponseResult findCourseByCourseId(Integer courseId){
        Course course = courseContentService.findCourseByCourseId(courseId);
        return new ResponseResult(true,200,"查询课程信息成功",course);
    }

    /*
        新增&更新章节信息
     */
    @RequestMapping("/saveOrUpdateSection")
    public ResponseResult saveOrUpdateSection(@RequestBody CourseSection courseSection){
        //判断是否携带了章节ID
        if (courseSection.getId() == null){
            courseContentService.saveSection(courseSection);
            return new ResponseResult(true,200,"新增章节成功",null);
        }else{
            courseContentService.updateSection(courseSection);
            return new ResponseResult(true,200,"更新章节成功",null);
        }
    }

    /*
        更新章节状态
     */
    @RequestMapping("/updateSectionStatus")
    public ResponseResult updateSectionStatus(Integer id,Integer status){
        courseContentService.updateSectionStatus(id,status);
        HashMap<String, Integer> map = new HashMap<>();
        map.put("status",status);
        return new ResponseResult(true,200,"响应成功",map);
    }

    /*
        新建&更新课时信息
     */
    @RequestMapping("/saveOrUpdateLesson")
    public ResponseResult saveLesson(@RequestBody CourseLesson courseLesson){
        if (courseLesson.getId() == null){
            courseContentService.saveLesson(courseLesson);
            return new ResponseResult(true,200,"新建课时成功",null);
        }else{
            courseContentService.updateLesson(courseLesson);
            return new ResponseResult(true,200,"更新课时成功",null);

        }
    }
}
