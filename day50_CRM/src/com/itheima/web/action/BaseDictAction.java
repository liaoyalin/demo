package com.itheima.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.itheima.bean.BaseDict;
import com.itheima.service.BaseDictService;
import com.itheima.utils.Constant;
import com.opensymphony.xwork2.ActionSupport;

public class BaseDictAction extends ActionSupport {
	private String dict_type_code;
	public void setDict_type_code(String dict_type_code) {
		this.dict_type_code = dict_type_code;
	}
	private BaseDictService baseDictService;

	public void setBaseDictService(BaseDictService baseDictService) {
		this.baseDictService = baseDictService;
	}
	private List< BaseDict> list;
	public List<BaseDict> getList() {
		return list;
	}
	
	//使用struts框架，写json数据
	public String findByType(){
		list=baseDictService.findByType(dict_type_code);
		return Constant.JSON_SUCCESS;
	}

	/*public String findByType(){
		
		try {
			//1.查询数据
			List< BaseDict> list=baseDictService.findByType(dict_type_code);
			System.out.println(list+"------------------");
			//2.list转json
			String json = new Gson().toJson(list);
			//3.将json数据写给页面
			HttpServletResponse response = ServletActionContext.getResponse();
			//4.处理response乱码问题
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
		
	}*/

}
