package com.test.query.web.school;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/schoolGradeInfo")
public class SchoolGradeInfoController {
	
	
	@RequestMapping(value = "index", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView schoolGradeInfoIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("/view/schoolGradeInfo/schoolGradeInfoIndex");
		return mv;
	}
	
	
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findschoolGradeInfoList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String pageNum =request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");
		Map<String, Object> map = new HashMap<String,Object>();
		ModelAndView mv = new ModelAndView();
		mv.addObject("page", null);
		mv.setViewName("/view/schoolGradeInfo/schoolGradeInfoList");
		return mv;
	}

}
