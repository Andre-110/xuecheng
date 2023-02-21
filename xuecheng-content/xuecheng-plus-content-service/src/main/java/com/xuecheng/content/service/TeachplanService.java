package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
* @description 课程基本信息业务接口
* @author liuqi
* @date 2023-02-21 10:46
* @version 1.0
*/
public interface TeachplanService {
    /***
     * @description  
     * @author  liuqi
     * @date    2023-02-21 10:59
     * @param	courseId	
     * @return  java.util.List<com.xuecheng.content.model.dto.TeachplanDto>
    */
    
    public List<TeachplanDto> findTeachplayTree(long courseId);

    /***
     * @description  课程计划接口
     * @author  liuqi
     * @date    2023-02-21 11:18
     * @param	teachplanDto
     * @return  void
     */

    public void saveTeachplan(SaveTeachplanDto teachplanDto);


}
