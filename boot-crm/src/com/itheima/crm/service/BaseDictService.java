package com.itheima.crm.service;

import java.util.List;


import com.itheima.crm.pojo.BaseDict;

/**  字典表数据业务逻辑接口
 */

public interface BaseDictService {
    List<BaseDict> getBaseDictByCode(String code);

}
  
