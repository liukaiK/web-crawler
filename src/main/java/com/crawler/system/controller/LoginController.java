package com.crawler.system.controller;

import com.crawler.config.BaseConfig;
import com.crawler.dao.MongoDBLink;
import com.crawler.system.Config;
import com.crawler.util.Util;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author liukai
 */
@Controller
@RequestMapping(value = "/manage")
public class LoginController {

    private Logger logger = Logger.getLogger(LoginController.class);

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        if (request.getSession().getAttribute(BaseConfig.USER) != null) {
            return "redirect:admin/access";
        } else {
            return "system/login";
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(String username, String password, String verifycode, HttpSession session, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!verifycode.equals(session.getAttribute(Config.VERIFYCODE))) {
            map.put("message", "验证码错误!");
            map.put("success", false);
            map.put("fffff", "asdasd");
            return map;
        }


        if (Util.isNull(username, password, verifycode)) {
            map.put("message", "用户名和密码不能为空!");
            map.put("success", false);
            return map;
        }

        if (this.username.equals(username) || this.password.equals(password)) {
            map.put("message", "登录成功");
            map.put("success", true);
        }
        return map;

    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        session.removeAttribute(BaseConfig.USER);
        logger.debug("-------------------清除session 退出用户!----------------");
        return new ModelAndView("redirect:login");
    }

}
