package com.xuecheng.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.exception.XueChengPlusException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author liuqi
 * @version 1.0
 * @description 课程信息管理业务接口实现类
 * @date 2023-02-16 0:43
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Autowired
    CourseMarketMapper courseMarketMapper;
    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Autowired
    CourseMarketServiceImpl courseMarketService;
    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        //构建查询条件，根据课程名称查询
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        //构建查询条件，根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        //构建查询条件，根据课程审核状态查询
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getPublishStatus()),CourseBase::getStatus,queryCourseParamsDto.getPublishStatus());
        //分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<CourseBase> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        PageResult<CourseBase> courseBasePageResult = new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());
        return courseBasePageResult;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto){
        //插入数据要进行校验
        //合法性校验（表单校验放置在controller层）
//        if(StringUtils.isBlank(dto.getName())){
//            XueChengPlusException.cast("课程名称为空");
////            throw new RuntimeException("课程名称为空");
//        }
//
//        if(StringUtils.isBlank(dto.getMt())){
//            XueChengPlusException.cast("课程分类为空");
////            throw new RuntimeException("课程分类为空");
//        }
//
//        if(StringUtils.isBlank(dto.getSt())){
//            XueChengPlusException.cast("课程分类为空");
////            throw new RuntimeException("课程分类为空");
//        }
//
//        if(StringUtils.isBlank(dto.getGrade())){
//            XueChengPlusException.cast("课程等级为空");
////            throw new RuntimeException("课程等级为空");
//        }
//
//        if(StringUtils.isBlank(dto.getTeachmode())){
//            XueChengPlusException.cast("教育模式为空");
////            throw new RuntimeException("教育模式为空");
//        }
//
//        if(StringUtils.isBlank(dto.getUsers())){
//            XueChengPlusException.cast("适应人群为空");
////            throw new RuntimeException("适应人群为空");
//        }
//
//        if(StringUtils.isBlank(dto.getCharge())){
//            XueChengPlusException.cast("收费规则为空");
////            throw new RuntimeException("收费规则为空");
//        }

        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填些的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto,courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus("202002");
        //设置发布状态
        courseBaseNew.setStatus("203001");
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        Long courseId = courseBaseNew.getId();
        //课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarketNew);
        courseMarketNew.setId(courseId);
        //收费规则
        String charge = dto.getCharge();

        //收费规则必须写且价格大于0
        if(charge.equals("201001")){
            Float price = dto.getPrice();
            if(price==null || price.floatValue()<=0){
                XueChengPlusException.cast("课程设置了收费价格不能为空且必须大于零");
//                throw new RuntimeException("课程设置了收费价格不能为空且必须大于零");
            }
        }
        //插入课程营销信息
        int insert1 = courseMarketMapper.insert(courseMarketNew);
        if(insert<=0||insert1<=0){
            XueChengPlusException.cast("新增课程基本信息失败");
//            throw new RuntimeException("新增课程基本信息失败");
        }
        return getCourseBaseInfo(courseId);
    }

    //根据课程id查询基本信息，包括基本信息和营销信息
    public CourseBaseInfoDto getCourseBaseInfo(long courseId){
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        if(courseBase == null){
            return null;
        }
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if(courseMarket!=null){
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }



        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;

    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto){
        //课程id
        Long courseId = dto.getId();
        CourseBase courseBaseUpdate = courseBaseMapper.selectById(courseId);
        if(courseBaseUpdate==null){
            XueChengPlusException.cast("课程不存在");
        }
        //校验是否是本机构
        if(!companyId.equals(courseBaseUpdate.getCompanyId())){
            XueChengPlusException.cast("只允许修改本机构的课程");
        }
        BeanUtils.copyProperties(dto,courseBaseUpdate);

        //更新
        courseBaseUpdate.setChangeDate(LocalDateTime.now());
        //mapper查询更新
        int i=courseBaseMapper.updateById(courseBaseUpdate);

        //查询课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if(courseMarket==null){
            courseMarket = new CourseMarket();
        }

        String charge = dto.getCharge();
        //收费课程必须写价格
        if(charge.equals("201001")){
            Float price = dto.getPrice();
            if(price==null || price.floatValue()<=0){
                XueChengPlusException.cast("课程设置了收费，故价格不能为空且必须大于0");
            }
        }

        BeanUtils.copyProperties(dto,courseMarket);
        boolean save = courseMarketService.saveOrUpdate(courseMarket);
        return getCourseBaseInfo(courseId);
        //保存课程营销信息

        //返回课程信息
    }
}

