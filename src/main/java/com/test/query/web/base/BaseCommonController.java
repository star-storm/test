package com.test.query.web.base;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
@SuppressWarnings("all")
public abstract class BaseCommonController {

	protected static final Logger log = Logger.getLogger(BaseCommonController.class);
	
	protected final static int DEFAULT_PAGE_NO = 1;
	protected final static int DEFAULT_PAGE_SIZE = 10;
	protected final static int DEFAULT_PAGE_MAXSIZE = 20;

	/**
	 * 获取当前登陆用户信息
	 */
//	protected User getUser(HttpServletRequest request){
//		User user = (User) request.getSession().getAttribute("user");
//		return user;
//	}
//
//	protected Long getCustId(HttpServletRequest request){
//		PlatformUserInfo user = (PlatformUserInfo) request.getSession().getAttribute("user");
//		if(user == null){
//			return null;
//		}
//		return user.getCustId();
//	}
	
	/**
	 * 判断是否登录
	 */
	public boolean isLogin(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (null == session || null==session.getAttribute("user")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 跳转登录
	 */
	protected boolean sendLogin(HttpServletRequest request,
			HttpServletResponse response) {
		String url = request.getRequestURI();
		request.getSession().setAttribute("returnUrl", url);
		try {
			response.sendRedirect("/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 向页面输出字节流
	 * 
	 * @param output
	 * @return
	 */
	protected void out(byte[] data,HttpServletResponse response) {
		OutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(data);
		} catch (Exception e) {
			log.error("向页面输出数据出错", e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {

				}
		}
	}
	
	/**
	 * 向页面输出字符
	 * 
	 * @param output
	 * @return
	 */
	protected void out(String data,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			out=response.getWriter();
			out.print(data);
		} catch (Exception e) {
			log.error("向页面输出数据出错", e);
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (Exception e) {

				}
		}
	}

	/**
	 * 跳转未授权
	 */
	protected boolean sendNotPermission(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setStatus(403);
			request.getSession().getServletContext()
					.getRequestDispatcher("/403").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 跳转页面不存在
	 */
	protected boolean sendNotFound(HttpServletRequest request,
			HttpServletResponse response, String path) {
		try {
			response.setStatus(404);
			request.getSession().getServletContext().getRequestDispatcher(path)
					.forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 导出下载
	 */
	public void writeDownload(String fileName, Workbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
		workbook.write(response.getOutputStream());
	}
	
	
	/**
	 * 获取请求参数 转换map
	 */
	protected Map<String, Object> getParameterMap(HttpServletRequest request) {
		// 参数Map
		Map<String, String[]> properties = request.getParameterMap();
		// 返回值Map
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator entries = properties.entrySet().iterator();

		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			if(StringUtils.isBlank(value)){
				value = null;
			}
			returnMap.put(name, value);
		}
		
		return returnMap;
	}
	

	protected Map<String, String> getParameterMapByTrim(HttpServletRequest request) {
		// 参数Map
		Map<String, String[]> properties = request.getParameterMap();
		// 返回值Map
		Map<String, String> returnMap = new HashMap<String, String>();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}

			if (StringUtils.isNotBlank(value)) {
				log.info("" + name + ":" + value);
				returnMap.put(name, value);
			}
		}
		return returnMap;
	}
}
