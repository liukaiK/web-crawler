package com.crawler.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crawler.config.BaseConfig;

@Controller
@RequestMapping("/admin")
public class TimerController {

	private Logger log = Logger.getLogger(TimerController.class);

	@RequestMapping(value = "/setTimeTask", method = RequestMethod.POST)
	public void setTimeTask(@RequestParam String time, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (time == null || "".equals(time)) {
			out.print("0`时间不能为空");
			out.flush();
			out.close();
			return;
		}
		// 检验时间格式
		if (!time.trim().matches("^([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$")) {
			out.print("0`时间格式不正确");
			out.flush();
			out.close();
			return;
		}
		out.print("1`设置定时任务成功");
		out.flush();
		out.close();
		BaseConfig.time = time;
	}

	@RequestMapping(value = "/cancelTimeTask", method = RequestMethod.GET)
	public void cancelTimeTask(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.print("1`取消定时任务成功");
			out.flush();
			out.close();
			BaseConfig.time = "";
		} catch (IOException e) {
			log.debug("--------------------取消定时任务失败--------------");
		}
	}
}
