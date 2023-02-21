package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author liuqi
 * @version 1.0
 * @description 课程分类树形节点
 * @date 2023-02-20 19:48
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory implements Serializable {
    //dto继承po
    List childrenTreeNodes;
}

