package com.itheima.web.action;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.util.StringUtils;

import com.itheima.bean.Customer;
import com.itheima.bean.PageBean;
import com.itheima.bean.User;
import com.itheima.service.CustomerService;
import com.itheima.utils.Constant;
import com.itheima.utils.MyFileUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.accessibility.internal.resources.accessibility;
import com.sun.org.apache.bcel.internal.generic.AALOAD;

/**
 * @author Administrator
 *
 */
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
	private Customer customer;
	private CustomerService customerService;
	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	@Override
	public Customer getModel() {
		if(customer==null){
			customer=new Customer();
		}
		return customer;
	}
	
	
	
	private int currentPage=1;//默认拿第一页的数据
	private int pageSize=5;//默认每页显示5条数据
	//提供set方法以便页面修改了获取的具体页码数
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	/**
	 * 分页显示客户列表的方法
	 * 
	 * 有两个地方会走这个方法
	 * 1.点击左侧的客户列表会走
	 * 2.点击右上角的筛选也会走这个方法
	 */
	public String findByPage(){
		//离线对象是QBC查询语法的重要组件，它能够让我们在dao层之上的两层进行查询条件的封装，当然，也可以不封装。
		
		//只会使用离线对象的两个方法  setXXX（针对聚合查询 总记录数、最大值、最小值、）  addXXX(针对where条件的设置)
		DetachedCriteria criteria=DetachedCriteria.forClass(Customer.class);
		//判断条件(实现筛选的功能)
		//校验客户名称 QBC查询 
		if(!StringUtils.isEmpty(customer.getCust_name())){
			criteria.add(Restrictions.like("cust_name", "%"+customer.getCust_name()+"%"));
		}
		
		//校验客户级别
		/**
		 * 注意：在开发中，为了体现代码的严谨性，一般有  (a对象.b对象.c对象)这种情况下，都下进行非空判断
		 */
		if(customer.getCust_level()!=null&&!StringUtils.isEmpty(customer.getCust_level().getDict_id())){
			criteria.add(Restrictions.eq("cust_level.dict_id",customer.getCust_level().getDict_id() ));
		}
		//校验客户来源
		if(customer.getCust_source()!=null&&!StringUtils.isEmpty(customer.getCust_source().getDict_id())){
			criteria.add(Restrictions.eq("cust_source.dict_id",customer.getCust_source().getDict_id() ));
		}
		//校验客户行业
		if(customer.getCust_industry()!=null&&!StringUtils.isEmpty(customer.getCust_industry().getDict_id())){
			criteria.add(Restrictions.eq("cust_industry.dict_id",customer.getCust_industry().getDict_id() ));
		}
		//校验电话
		if(!StringUtils.isEmpty(customer.getCust_phone())){
			criteria.add(Restrictions.like("cust_phone", "%"+customer.getCust_phone()+"%"));
		}
		
		
		
		PageBean<Customer> pageBean =customerService.findByPage(criteria,currentPage,pageSize);
		//把PageBean存到作用域 值栈
		ActionContext.getContext().getValueStack().push(pageBean);
		return Constant.PAGE_SUCCESS;
		
	}
	
	//文件上传
	
		//要想获取文件数据，需要这么声明。
		private File upload;  //属性名称就是页面的 name属性值  <input type="file" name="upload"/>
		private String uploadContentType;  //文件类型  = name属性值 + ContentType 
		private String uploadFileName;  //文件名称 = name属性值 + FileName
		
		public void setUpload(File upload) {
			this.upload = upload;
		}
		public void setUploadContentType(String uploadContentType) {
			this.uploadContentType = uploadContentType;
		}
		public void setUploadFileName(String uploadFileName) {
			this.uploadFileName = uploadFileName;
		}
	//保存客户
	public String save() throws IOException{
		//对所有数据进行校验
		/*System.out.println("file="+upload);
		System.out.println("fileName="+uploadFileName);
		System.out.println("fileType="+uploadContentType);
		
		String fileName=MyFileUtil.getFileName(uploadFileName);
		File file=new File("D:/CRM_FileUpload/img",fileName);
		FileUtils.copyFile(upload, file);*/
		
		if(StringUtils.isEmpty(customer.getCust_name())){
			addActionError("客户名称不能为空！");
			return Constant.INPUT_ERROR;
		}
		
		if(StringUtils.isEmpty(customer.getCust_industry().getDict_id())){
			addActionError("所属行业不能为空！");
			return Constant.INPUT_ERROR;
		}
		
		if(StringUtils.isEmpty(customer.getCust_source().getDict_id())){
			addActionError("信息来源不能为空！");
			return Constant.INPUT_ERROR;
		}
		
		if(StringUtils.isEmpty(customer.getCust_level().getDict_id())){
			addActionError("客户级别不能为空！");
			return Constant.INPUT_ERROR;
		}
		
		if(StringUtils.isEmpty(customer.getCust_address())){
			addActionError("联系地址不能为空！");
			return Constant.INPUT_ERROR;
		}
		
		if(StringUtils.isEmpty(customer.getCust_phone())){
			addActionError("联系电话不能为空！");
			return Constant.INPUT_ERROR;
		}
		//设置创建人和负责人(谁添加的用户谁就是创建人和负责人)
		
		User user=(User) ServletActionContext.getRequest().getSession().getAttribute("user");
		customer.setCust_create_id(user);
		customer.setCust_user_id(user);
		
		if(upload != null){
			//存储文件 tmp---jpg | png
			String fileName  = MyFileUtil.getFileName(uploadFileName);
			File file  = new File("D:/CRM_FileUpload/img" , fileName);
			FileUtils.copyFile(upload, file);
			
			
			customer.setCust_image("img/"+fileName);
			System.out.println("文件名============"+fileName);
		}
		customerService.save(customer);
		return Constant.SAVE_SUCCESS;
	}
	//删除客户
	public String delete(){
		customerService.delete(customer);
		
		return Constant.DELETE_SUCCESS;
	}
	//使用属性的方式存到值栈
	private Customer editCustomer;
	public Customer getEditCustomer() {
		return editCustomer;
	}
	//修改客户信息
	//1.第一步，仅仅是根据客户的id来查询客户的数据，然后跳转到修改页面表现出来
	
	/**
	 * 根据id查询客户数据
	 */
	public String edit(){
		editCustomer=customerService.findById(customer.getCust_id());
		
		//存储数据到值栈1.push | 2.set  | 3.属性的方式(提供全局变量，提供get())
		return Constant.EDIT_SUCCESS;
	}
	//1.第二步，更新数据
	public String update(){
		//customer.getCust_create_id().getUser_id()==null
		// customer.setCust_create_id(null)
		
		customerService.update(customer);
		return Constant.UPDATE_SUCCESS;
		
	}
	
	List<Customer>list;
	public List<Customer> getList() {
		return list;
	}
	
	public String findAll(){
	list=customerService.findAll();
		return Constant.JSON_SUCCESS;
		
	}

}
