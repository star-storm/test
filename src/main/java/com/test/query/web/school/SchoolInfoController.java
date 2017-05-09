package com.test.query.web.school;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/schoolinfo")
public class SchoolInfoController {

	@RequestMapping(value = "index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView schoolinfoIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/schoolinfo/schoolinfoIndex");
		return mv;
	}

	/*
	 * 基础管理 学校管理列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public Map<String,Object> findschoolinfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		return null;
	}

	
}
