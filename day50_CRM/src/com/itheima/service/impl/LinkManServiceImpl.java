package com.itheima.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bean.LinkMan;
import com.itheima.bean.PageBean;
import com.itheima.dao.LinkManDao;
import com.itheima.service.LinkManService;
@Transactional
public class LinkManServiceImpl implements LinkManService {
	private LinkManDao linkManDao;
	public void setLinkManDao(LinkManDao linkManDao) {
		this.linkManDao = linkManDao;
	}

	@Override
	public void save(LinkMan linkMan) {
		linkManDao.save(linkMan);

	}

	@Override
	public PageBean<LinkMan> findByPage(DetachedCriteria criteria, int currentPage, int pageSize) {
		//totalSize总记录数找dao去查询
		int totalSize=linkManDao.findCount(criteria);
		List<LinkMan> list =linkManDao.findByPage(criteria,currentPage,pageSize);
		int totalPage=(int) Math.ceil(totalSize*1.0/pageSize);
		PageBean<LinkMan>pageBean=new PageBean<LinkMan>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.setTotalSize(totalSize);
		return pageBean;
	}

	@Override
	public void delete(LinkMan linkMan) {
		linkManDao.delete(linkMan);
	}

	@Override
	public LinkMan findById(Long lkm_id) {
		return linkManDao.findById(lkm_id);
		
		
	}

	@Override
	public void update(LinkMan linkMan) {
		linkManDao.update(linkMan);
		
	}

	@Override
	public List<LinkMan> findByCid(DetachedCriteria criteria) {
		
		return linkManDao.findByCid(criteria);
	}

}
