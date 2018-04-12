package com.itheima.crm.service;

import java.util.List;

import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.utils.Page;

/**  
客户信息业务逻辑接口       
 */
public interface CustomerService {
    //根据查询条件，分页查询数据列表
    Page<Customer> queryCustomerByQueryVo(QueryVo vo);
    
  //根据id查询客户(客户的回显)
    Customer queryCustomerById(Integer id);
    
    //更新客户信息
    void update(Customer customer);

    void delete(Integer id);
   

}
  
