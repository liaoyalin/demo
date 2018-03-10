package com.itheima.web.action;

import org.apache.struts2.ServletActionContext;

import com.itheima.bean.User;
import com.itheima.service.UserService;
import com.itheima.utils.Constant;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
/**
 * @author Administrator
 *
 */
public class UserAction extends ActionSupport implements ModelDriven<User> {
	private User user;
	private UserService userService;
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public User getModel() {
		if(user==null){
			user=new User();
		}
		return user;
	}
	
	/**
	 * 用户注册
	 */
	public String register(){
		user.setUser_state('1');
		userService.register(user);
		
		return NONE;
		
	}
	
	/**
	 * 用户登录
	 */
	public String login(){
		User loginUser =userService.login(user);
		if(loginUser!=null){
			//如果成功，跳转页面，将数据存到作用域里面
			ServletActionContext.getRequest().getSession().setAttribute("user", loginUser);
			return Constant.LOGIN_SUCCESS;
		}
		//3. 登录失败处理  1.跳转页面 ，2.回显错误信息
		
				//存储到值栈 push | set  | 成员变量 [0] 
				//set背后是： 把值存储到一个map集合， 然后push这个map集合到栈顶上。
				ActionContext.getContext().getValueStack().set( "msg" , "账号或者密码错误!");
		//采用struts的方法回显错误
		//addFieldError("msg", "账号或者密码错误!");
		return Constant.LOGIN_ERROR;
	}

	

}
