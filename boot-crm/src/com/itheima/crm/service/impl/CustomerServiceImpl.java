package com.itheima.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.crm.mapper.CustomerMapper;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.service.CustomerService;
import com.itheima.crm.utils.Page;

/**  
 * ClassName:CustomerServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年4月12日 下午5:39:02 <br/>       
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;
    

    @Override
    public Page<Customer> queryCustomerByQueryVo(QueryVo vo) {
        Page<Customer> page=new Page<>();
      //查询总记录数
        Integer total = customerMapper.queryCountByQueryVo(vo);
        page.setTotal(total);
        
        //设置当前页的数据
        page.setPage(vo.getPage());
        page.setSize(vo.getRows());
        
      //查询数据列表
        Integer start = (vo.getPage()-1) * vo.getRows();
        vo.setStart(start);
        List<Customer> customers = customerMapper.queryCustomerByQueryVo(vo);
        page.setRows(customers);

        return page;
    }


    @Override
    public Customer queryCustomerById(Integer id) {
          
        return customerMapper.queryCustomerById(id);
    }


    @Override
    public void update(Customer customer) {
          
        customerMapper.update(customer);
    }


    @Override
    public void delete(Integer id) {
        customerMapper.delete(id);
        
    }
    
    

}
  
