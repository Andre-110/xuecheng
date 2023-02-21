package com.xuecheng.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.Teachplan;

import java.util.List;

/**
 * <p>
 * 课程计划 Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface TeachplanMapper extends BaseMapper<Teachplan> {


    /**
     * @description 查询某课程的课程计划
     * @param courseId
     * return com.xuecheng.content.model.dto.TeachplanDto
     * @author 86191
     * @date 2023-02-21 10:11
    */

    public List<TeachplanDto> selectTreeNodes(long courseId);

}
