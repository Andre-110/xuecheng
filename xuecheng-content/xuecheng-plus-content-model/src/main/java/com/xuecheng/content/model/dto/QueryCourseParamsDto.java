package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author liuqi
 * @version 1.0
 * @description 课程查询参数Dto
 * @date 2023-02-15 22:45
 */
@Data
@ToString
public class QueryCourseParamsDto {

    //审核状态
    private String auditStatus;
    //课程名称
    private String courseName;
    //发布状态
    private String publishStatus;
}

