package com.xuecheng.content.model.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author liuqi
 * @version 1.0
 * @description 保存课程计划Dto,包括新增和修改
 * @date 2023-02-21 11:14
 */
@Data
@ToString
public class SaveTeachplanDto {
    private Long id;
    private String pname;
    private Long parentid;
    private Integer grade;
    private String mediaType;
    private Long courseId;
    private Long coursePubId;
    private String isPreviw;
}

