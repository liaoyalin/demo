package com.itheima.crm.mapper;

import java.util.List;

import com.itheima.crm.pojo.BaseDict;

/**  
 字典表持久化接口     
 */
public interface BaseDictMapper {
    List<BaseDict> getBaseDictByCode(String code);

}
  
