package com.itheima.crm.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.crm.pojo.BaseDict;
import com.itheima.crm.pojo.Customer;
import com.itheima.crm.pojo.QueryVo;
import com.itheima.crm.service.BaseDictService;
import com.itheima.crm.service.CustomerService;
import com.itheima.crm.utils.Page;

/**  
 * ClassName:CustomerController <br/>  
 * Function:  <br/>  
 * Date:     2018年4月12日 下午3:25:40 <br/>       
 */
@Controller
@RequestMapping("customer")
public class CustomerController {
    
    private Logger logger=Logger.getLogger(CustomerController.class);
    @Autowired
     private BaseDictService baseDictService;
    @Autowired
    private CustomerService customerService;
    
    @Value("${CUSTOMER_FROM_TYPE}")
    private String CUSTOMER_FROM_TYPE;
    
    @Value("${CUSTOMER_INDUSTRY_TYPE}")
    private String CUSTOMER_INDUSTRY_TYPE;
    
    @Value("${CUSTOMER_LEVEL_TYPE}")
    private String CUSTOMER_LEVEL_TYPE;
    
    @RequestMapping("list")
    public String list(Model model ,QueryVo vo){
      //来源信息查询
        List<BaseDict> fromType = baseDictService.getBaseDictByCode(CUSTOMER_FROM_TYPE);
      //客户行业查询
        List<BaseDict> industryType = baseDictService.getBaseDictByCode(CUSTOMER_INDUSTRY_TYPE);
      //客户级别查询
        List<BaseDict> levelType = baseDictService.getBaseDictByCode(CUSTOMER_LEVEL_TYPE);
        
        //分页查询数据
        Page<Customer> page = customerService.queryCustomerByQueryVo(vo);
        model.addAttribute("page", page);
        model.addAttribute("fromType", fromType);
        model.addAttribute("industryType", industryType);
        model.addAttribute("levelType", levelType);
       //回显下拉框数据
        model.addAttribute("vo", vo);
        return "customer";
    }
    
    //回显数据
    @RequestMapping("edit")
    @ResponseBody
    public Customer queryCustomerById(Integer id){
        Customer customer = customerService.queryCustomerById(id);
        return customer;
    }
    
    //更新
    @RequestMapping("update")
    @ResponseBody
    public String update(Customer customer){
        String msg="0";
        try {
            customerService.update(customer);
        } catch (Exception e) {
              msg="1";
              logger.error("修改客户信息发生了异常",e);
        }
        return msg;
    }
    
    //删除
    @RequestMapping("delete")
    @ResponseBody
    public String delete(Integer id){
        String msg="0";
        try {
            customerService.delete(id);
        } catch (Exception e) {
              msg="1";
              logger.error("修改客户信息发生了异常",e);
        }
        return msg;
    }

}
  
