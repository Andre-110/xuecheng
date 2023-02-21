package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.Teachplan;
import com.xuecheng.content.model.po.TeachplanMedia;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * @author liuqi
 * @version 1.0
 * @description 课程计划模型类
 * @date 2023-02-21 9:58
 */
@Data
@ToString
public class TeachplanDto extends Teachplan{

    //课程计划关联的媒贷信息
    TeachplanMedia teachplanMedia;

    //子课程目录，有多个
    List<TeachplanDto> teachPlanTreeNodes;

}

