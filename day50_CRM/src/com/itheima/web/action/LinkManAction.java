package com.itheima.web.action;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.StringUtils;

import com.itheima.bean.Customer;
import com.itheima.bean.LinkMan;
import com.itheima.bean.PageBean;
import com.itheima.service.LinkManService;
import com.itheima.utils.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class LinkManAction extends ActionSupport implements ModelDriven<LinkMan> {
	private LinkMan linkMan;
	private LinkManService linkManService;
	public void setLinkManService(LinkManService linkManService) {
		this.linkManService = linkManService;
	}

	@Override
	public LinkMan getModel() {
		if(linkMan==null){
			linkMan=new LinkMan();
		}
		return linkMan;
	}
	
	/**
	 * 保存联系人
	 */
	public String save(){
		linkManService.save(linkMan);
		return Constant.SAVE_SUCCESS;
	}
	
	private int currentPage=1;
	private int pageSize=5;
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 分页查询
	 */
	public String findByPage(){
		//查询数据
		DetachedCriteria criteria=DetachedCriteria.forClass(LinkMan.class);
		//设置过滤条件
		//名称
		if(!StringUtils.isEmpty(linkMan.getLkm_name())){
			criteria.add(Restrictions.like("lkm_name", "%"+linkMan.getLkm_name()+"%"));
		}
		if(!StringUtils.isEmpty(linkMan.getLkm_gender())){
			criteria.add(Restrictions.eq("lkm_gender", linkMan.getLkm_gender()));
		}
		//邮箱
		if(!StringUtils.isEmpty(linkMan.getLkm_email())){
			criteria.add(Restrictions.like("lkm_email", "%"+linkMan.getLkm_email()+"%"));
		}
		//qq
		if(!StringUtils.isEmpty(linkMan.getLkm_qq())){
			criteria.add(Restrictions.like("lkm_qq", "%"+linkMan.getLkm_qq()+"%"));
		}
		//电话
		if(!StringUtils.isEmpty(linkMan.getLkm_phone())){
			criteria.add(Restrictions.like("lkm_phone", "%"+linkMan.getLkm_phone()+"%"));
		}
		
		//所属客户
		if(linkMan.getCustomer()!=null&&!StringUtils.isEmpty(linkMan.getCustomer().getCust_id())){
			criteria.add(Restrictions.eq("customer.cust_id",linkMan.getCustomer().getCust_id() ));
		}
		PageBean<LinkMan> pageBean=linkManService.findByPage(criteria,currentPage,pageSize);
		//存数据
		ActionContext.getContext().getValueStack().push(pageBean);
		return Constant.PAGE_SUCCESS;
	}
	
	
	public String delete(){
		//linkMan.setCustomer(null);
		linkManService.delete(linkMan);
		return Constant.DELETE_SUCCESS;
		
	}
	//使用属性的方式存到值栈
	
	private LinkMan editLinkMan;
	public LinkMan getEditLinkMan() {
		return editLinkMan;
	}
	//修改联系人第一步，先回显数据
	public String edit(){
		editLinkMan=linkManService.findById(linkMan.getLkm_id());
		return Constant.EDIT_SUCCESS;
		
	}
	//修改联系人第二步,直接修改
	public String update(){
		linkManService.update(linkMan);
		return Constant.UPDATE_SUCCESS;
		
	}
	private Long cid;
	public void setCid(Long cid) {
		this.cid = cid;
	}
	private List<LinkMan> list;
	public List<LinkMan> getList() {
		return list;
	}
	//根据客户id查询联系人
	public String findByCid(){
		//定义离线对象，表示查询哪张表
		DetachedCriteria criteria=DetachedCriteria.forClass(LinkMan.class);
		//添加查询条件，按照客户的id来查询联系人
		criteria.add(Restrictions.eq("customer.cust_id", cid));
		//可能查询到很多的联系人，所以这里是一个list集合， 并且页面要求的是一个json数据
		 list=linkManService.findByCid(criteria);
		return Constant.JSON_SUCCESS;
	}

}
