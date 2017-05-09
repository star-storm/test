package com.test.query.web.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/** 
 * 通用请求处理控制器
 */
@Controller
@RequestMapping
public class CommonController {
	
	private static final String PROV_NAME = "_provName";
	
//	@Autowired
//	private GetCodeUtils getCodeUtils;
	
	/**
	 * 访问未授权
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/403")
	public String noPermission(HttpServletResponse response) {
		response.setStatus(403);
		return "/common/403";
	}
	
	/**
	 * 页面未找到
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/404")
	public String notFound(HttpServletResponse response) {
		response.setStatus(404);
		return "/common/404";
	}

	/**
	 * 服务器内部错误
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/500")
	public String serverException(HttpServletResponse response) {
		response.setStatus(500);
		return "/common/500";
	}
	
	
	@RequestMapping("/setProvince")
	@ResponseBody
	public String setProvince(
		@RequestParam("provName")String provName,
		HttpServletRequest request, HttpServletResponse response){
		try {
			String _provName = URLDecoder.decode(provName, "UTF-8");
			HttpSession session = request.getSession();
			session.setAttribute(PROV_NAME, _provName);
			Cookie c = new Cookie(PROV_NAME, provName);
			c.setMaxAge(3*3600);
			c.setPath("/");
			response.addCookie(c);
			return "success";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "failed";
		}
	}
	
//
//	@RequestMapping("/getProvince")
//	@ResponseBody
//	public String getProvince(HttpServletRequest request, HttpServletResponse response){
//		String _provName = null;
//		try {
//			//先由session中取
//			HttpSession session = request.getSession();
//			Object provName = session.getAttribute(PROV_NAME);
//			if(provName!=null){
//				_provName = (String) provName;
//				_provName = URLDecoder.decode(_provName, "UTF-8");
//				return _provName;
//			}
//			//session中没有由cookie中取
//			Cookie[] cookies = request.getCookies();
//	        if (cookies!=null && cookies.length>0) {
//	            for (Cookie c:cookies) {
//	                if (c.getName().equals(PROV_NAME)) {
//	                	_provName = c.getValue();
//	                	_provName = URLDecoder.decode(_provName, "UTF-8");
//	        			session.setAttribute(PROV_NAME, _provName);
//	    				return _provName;
//	                }
//	            }
//	        }
//	        //cookie中还没有取码表省份第一个
//	        Map<String, String> map = getCodeUtils.getProvinceMap();
//	        Set<Map.Entry<String, String>> set = map.entrySet();
//	        for(Map.Entry<String, String> one:set){
//	        	_provName = one.getValue();
//	        	session.setAttribute(PROV_NAME, one.getValue());
//	        	return _provName;
//	        }
//	        return null;
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
	
}
