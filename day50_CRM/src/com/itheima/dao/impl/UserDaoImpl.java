package com.itheima.dao.impl;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import com.itheima.bean.User;
import com.itheima.dao.UserDao;
@Transactional
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
//保存用户（注册）
	@Override
	public void save(User user) {
		getHibernateTemplate().save(user);

	}
//登录
	@Override
	public User findUser(User user) {
		String hql="from User where user_code=? and user_password=? and user_state=1";
		List<User> list = (List<User>) getHibernateTemplate().find(hql,user.getUser_code(),user.getUser_password());
		if(list.size()>0){
			return list.get(0);
		}
		return null;
		
	}

}
