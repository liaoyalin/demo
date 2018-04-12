package com.itheima.crm.mapper;

import java.util.List;

import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;

/**  
客户信息持久化接口       
 */
public interface CustomerMapper {
    //根据查询条件，分页查询数据列表
    List<Customer> queryCustomerByQueryVo(QueryVo vo);
    //根据查询条件，统计总记录数
    Integer queryCountByQueryVo(QueryVo vo);

    //根据id查询客户(客户的回显)
    Customer queryCustomerById(Integer id);
    
    //更新客户信息
    void update(Customer customer);
    //删除
    void delete(Integer id);
    
}
  
