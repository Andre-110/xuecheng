package com.xuecheng.content.api;

import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqi
 * @version 1.0
 * @description 课程信息编辑接口
 * @date 2023-02-15 23:03
 */
@Api(value="课程信息编辑接口",tags="课程信息编辑接口")
//将结果转为json格式
@RestController
public class CourseBaseInfoController {

    @Autowired CourseBaseInfoService courseBaseInfoService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    //pageParams分页参数通过url的key/value传入
    //queryCourseParams通过json数据传入，使用@RequestBody将json转化为QueryCourseParamsDto对象
    PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamsDto queryCourseParams){
//        //model po类中的courseBase，并设置名称和时间
//        //model dto类中的变量，利用起来
//
//        //调用service获取数据，service再去调用mapper(dao,在service层)
//        //先写mapper再去写service
//        CourseBase courseBase = new CourseBase();
//        courseBase.setName("测试名称");
//        courseBase.setCreateDate(LocalDateTime.now());
//        //放入集合中
//        List<CourseBase> courseBases = new ArrayList<>();
//        courseBases.add(courseBase);
//        //页面返回值的参数为页面值的集合，总记录数，页码，每页记录数
//        PageResult pageResult = new PageResult<CourseBase>(courseBases,10,1,10);
//        return pageResult;
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);

        return  courseBasePageResult;
    }

    @ApiOperation("新增课程基本信息")
    @PostMapping("/course")
    CourseBaseInfoDto createCourseBase(@RequestBody @Validated AddCourseDto addCourseDto){
        Long companyId = 22L;
        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);
    }

    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }

    @ApiOperation("修改课程基本信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){

        //机构id
        Long companyId=1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);

    }
}

