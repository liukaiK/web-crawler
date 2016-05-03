package com.esd.bs.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.esd.dao.MongoDBDao;
import com.esd.entity.other.Complaint;
import com.esd.entity.other.Report;

/**
 * 投诉登记单
 * 
 * @author liukai
 * 
 */
@Controller
@Scope(value = "prototype")
public class ReportController {
	
	@Resource
	private MongoDBDao mongoDBDao;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@RequestMapping(value = "/complaintSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> complaintSubmit(Complaint complaint, String code, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!randomCode.equals(code)) {
			map.put("check", false);
			return map;
		} else {
			complaint.setDate(sdf.format(new Date()));
			mongoDBDao.insert(complaint);
			map.put("message", true);
			return map;
		}
	}

	@RequestMapping(value = "admin/queryComplaint", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView queryComplaint(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Complaint> list = mongoDBDao.findAll(Complaint.class);
		map.put("result", list);
		return new ModelAndView("/queryComplaint", map);
	}

	@RequestMapping(value = "admin/queryComplaintById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView queryComplaintById(String id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Complaint complaint = mongoDBDao.findById(Complaint.class, id);
		map.put("result", complaint);
		return new ModelAndView("/queryComplaintById", map);
	}

	@RequestMapping(value = "admin/deleteComplaintById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteComplaintById(String id, HttpServletRequest request) {
		Complaint complaint = mongoDBDao.findById(Complaint.class, id);
		mongoDBDao.remove(complaint);
		return new ModelAndView("redirect:/iac/admin/queryComplaint");
	}

	@RequestMapping(value = "/reportSubmit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reportSubmit(Report report,String code, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String randomCode = (String) request.getSession().getAttribute("randomCode");
		if (!randomCode.equals(code)) {
			map.put("check", false);
			return map;
		} else {
			report.setDate(sdf.format(new Date()));
			mongoDBDao.insert(report);
			map.put("message", true);
			return map;
		}
	}

	
	@RequestMapping(value = "admin/queryReport", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView queryReport(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Report> list = mongoDBDao.findAll(Report.class);
		map.put("result", list);
		return new ModelAndView("/queryReport", map);
	}

	@RequestMapping(value = "admin/queryReportById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView queryReportById(String id, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Report report = mongoDBDao.findById(Report.class, id);
		map.put("result", report);
		return new ModelAndView("/queryReportById", map);
	}

	@RequestMapping(value = "admin/deleteReportById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteReportById(String id, HttpServletRequest request) {
		Report report = mongoDBDao.findById(Report.class, id);
		mongoDBDao.remove(report);
		return new ModelAndView("redirect:/iac/admin/queryReport");
	}

}
