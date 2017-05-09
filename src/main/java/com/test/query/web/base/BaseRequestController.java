package com.test.query.web.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseRequestController {

	/**
	 * @Description： 获取request中的json字符串，分组返回的json数据。
	 * @author: yyf 
	 * @since: 2017年3月20日 下午5:17:59
	 */
	public static String getRequest(HttpServletRequest request) throws IOException {
		String reqStr = "";
		BufferedReader reqIn;
		try {
			reqIn = new BufferedReader(new InputStreamReader(
					request.getInputStream(), "utf-8"));
			String line;
			while ((line = reqIn.readLine()) != null) {
				reqStr += line;
			}
			// System.out.println("getRequstStr : " + reqStr);
			if (StringUtils.isBlank(reqStr)) {
				reqStr = "{}";
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reqStr;
	}
	
	//获取request头
	public static JSONObject getRequest(JSONObject sobj) {
		JSONObject requestJson = (JSONObject) sobj.get("request");
		return requestJson;
	}
	//获取header头
	public static JSONObject getHeader(JSONObject sobj) {
		JSONObject headerJson = (JSONObject) sobj.get("header");
		return headerJson;
	}
	//获取params头
	public static JSONObject getParams(JSONObject sobj) {
		JSONObject requestJson = (JSONObject) sobj.get("request");
		JSONObject paramsJson = (JSONObject) requestJson.get("params");
		return paramsJson;
	}
	//获取params中的数组
	public static JSONArray getDataArray(JSONObject params) {
		JSONArray jsonArray=params.getJSONArray("dataArray");
		return jsonArray;
	}	
	
	
	
	//分装header头信息
	public static Map<String,String> setHeaderValueJson() {
		Map<String,String> headerValueMap = new HashMap<String, String>();
		headerValueMap.put("device", "01");
		headerValueMap.put("platform", "01");
		headerValueMap.put("version", "1.0");
//		String json = JSON.toJSONString(headerMap,true); //转成JSON数据  
//		JSONObject jobj=JSON.parseObject(json);//将字符串转为json格式
		return headerValueMap;
	}
	//分装response头信息
	public static Map<String,Object> setResponseJson(String message,String status,Map<String,Map<String,Object>> resultValueMap) {
		Map<String,Object> responseValueMap = new HashMap<String, Object>();
		responseValueMap.put("message", message);
		responseValueMap.put("status", status);
		responseValueMap.put("result", resultValueMap);
//		String json = JSON.toJSONString(responseMap,true); //转成JSON数据  
//		JSONObject jobj=JSON.parseObject(json);//将字符串转为json格式
		return responseValueMap;
	}
	
	//分装data数据
	public static Map<String,Map<String,Object>> setDataJson(String dataName,List list,int pageCount,int recordCount) {
		Map<String,Map<String,Object>> responseValueMap = new HashMap<String, Map<String,Object>>();
			Map<String,Object> dataMap = new HashMap<String, Object>();
			dataMap.put("data", list);
			dataMap.put("pageCount", pageCount);
			dataMap.put("recordCount", recordCount);
		responseValueMap.put(dataName, dataMap);
		return responseValueMap;
	}
	//分装多个data数据
	public static Map<String,Map<String,Object>> setMoreDataJson(List<Map<String,Object>> dataList) {
		Map<String,Map<String,Object>> responseValueMap = new HashMap<String, Map<String,Object>>();
		for(int i=0;i<dataList.size();i++){
			Map<String,Object> newMap = new HashMap<String, Object>();
				newMap.put("data", dataList.get(i).get("list"));
				newMap.put("pageCount", dataList.get(i).get("pageCount"));
				newMap.put("recordCount", dataList.get(i).get("recordCount"));
			responseValueMap.put(dataList.get(i).get("dataName")+"", newMap);
		}
		return responseValueMap;
	}
	//分装返回的json参数
	public static JSONObject setReturnJson(String message,String status,Map<String,Map<String,Object>> resultValueMap) {
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("header", setHeaderValueJson());
		returnMap.put("response", setResponseJson(message, status, resultValueMap));
		/**
		 * WriteNullNumberAsZero	数值字段如果为null,输出为0,而非null
		 * WriteMapNullValue	是否输出值为null的字段,默认为false
		 */
		String json = JSONObject.toJSONString(returnMap,SerializerFeature.WriteMapNullValue,SerializerFeature.WriteNullNumberAsZero	); //转成JSON数据  
		JSONObject jobj=JSON.parseObject(json);//将字符串转为json格式
		return jobj;
	}
	//已流的形式向客户的返回json数据
	 public static void getPrint(HttpServletResponse response,JSONObject jsonObject) throws IOException{
 		 response.setCharacterEncoding("UTF-8");     //
 	     PrintWriter out = response.getWriter();  //
 	     out.print(jsonObject);
 	     out.flush();
 		 out.close();
 	 }
	/**
	 * 
	 * @Description： 向移动端返回单个主节点的数据（单个list的json数据）。
	 * 单个主节点返回列子：
	 * {
		    "header": {
		        "device": "01", 
		        "platform": "01", 
		        "version": "1.0"
		    }, 
		    "response": {
		        "message": "成功!", 
		        "result": {
		            "mainData": {
		                "data": [
		                    {
		                        …… 查看接口说明参数列表           
		         }
		                ], 
		                "pageCount ": "页数-----这个和记录数相同。", 
		                "recordCount ": "记录数"
		            }
		        }, 
		        "status": "成功标识"
		    }
		}
	 *
	 *	message:返回的消息
	 *	status：成功标识
	 *	list：返回查到的数据
	 *	pageType:是否分页标识，0：否，1：是，
	 *	pageCount:当前页数
	 *	
	 * @author: yyf
	 * @since: 2017年4月18日 上午11:42:58
	 */
	public static JSONObject getReturnOneListJson(String message,String status,List list,int pageType,int pageCount) {
		if(pageCount==999999){
			pageCount=1;
		}
		JSONObject jsonObject=new JSONObject();
//		if(status.equals(ConstantsPad.success)){
//			//判断是否有分页信息
//			if(pageType==ConstantsPad.pageType1){
//				jsonObject=setReturnJson(
//						message,
//						status,
//						BaseRequestController.setDataJson(
//								"mainData",
//								list,
//								pageCount,
//								list.size())
//						);
//			}else{
//				jsonObject=setReturnJson(
//						message,
//						status,
//						BaseRequestController.setDataJson(
//								"mainData",
//								list,
//								list.size(),
//								list.size())
//						);
//			}
//			//返回没有查到数据的json
//		}else if(status.equals(ConstantsPad.prompt99)){
//			//返回失败信息。
//			jsonObject=setReturnJson(
//					message,
//					ConstantsPad.success,
//					new HashMap<String,Map<String,Object>>()
//					);
//			//返回提示信息的json
//		}else{
//			//返回失败信息。
//			jsonObject=setReturnJson(
//					message,
//					status,
//					new HashMap<String,Map<String,Object>>()
//					);
//		}
		return jsonObject;
	}
	/**
	 * 
	 * @Description： 向移动端返回多个主节点的数据（多个list的json数据）。
	 * 多个主节点返回报文格式列子：
	 * {
		    "header": {
		        "device": "01", 
		        "platform": "01", 
		        "version": "1.0"
		    }, 
		    "response": {
		        "message": "成功!", 
		        "result": {
		            "mainData": {
		                "data": [
		                    {
		                        …… 查看接口说明参数列表           
		         			}
		                ], 
		                "pageCount ": "页数", 
		                "recordCount ": "记录数"
		            }，
					"主节点二": {
		                "data": [
		                    {
		                        …… 查看接口说明参数列表           
		         			}
		                ], 
		                "pageCount ": "页数", 
		                "recordCount ": "记录数"
		            }
		        }, 
		        "status": "成功标识"
		    }
		}
	 *
	 *	message:返回的消息
	 *	status：成功标识
	 *	list：返回查到的数据
	 * @author: yyf
	 * @since: 2017年4月18日 上午11:42:58
	 */
	public static JSONObject getReturnMonreListJson(String message,String status,List<Map<String,Object>> list) {
		JSONObject jsonObject=new JSONObject();
//		if(status.equals(ConstantsPad.success)){
//			//判断是否有分页信息
//			jsonObject=BaseRequestController.setReturnJson(
//					message,
//					status,
//					BaseRequestController.setMoreDataJson(list)
//					);
//		}else{
//			//返回失败信息。
//			jsonObject=setReturnJson(
//					message,
//					status,
//					new HashMap<String,Map<String,Object>>()
//					);
//		}
		return jsonObject;
	}
}
