package com.webcrawler.controller.login;

import com.webcrawler.config.BaseConfig;
import com.webcrawler.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@Controller
public class LoginController {

    @Value("${username}")
    private String username;

    @Value("${password}")
    private String password;

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public ModelAndView goLogin(HttpServletRequest request) {
        if (request.getSession().getAttribute(BaseConfig.USER) != null) {
            return new ModelAndView("redirect:admin/access");
        } else {
            return new ModelAndView("login");
        }
    }

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password, String code, HttpServletRequest request) {
        if (StringUtil.isNotEmpty(username, password, code)) {
            if (code.equals(request.getSession().getAttribute("randomCode"))) {
                if (this.username.equals(username) || this.password.equals(password)) {
                    request.getSession().setAttribute(BaseConfig.USER, username);
                    log.debug("------------------登录成功!----------------");
                    return new ModelAndView("redirect:admin/access");
                } else {
                    request.getSession().setAttribute(BaseConfig.MESSAGE, "用户名或密码错误!");
                    log.debug("------------------用户名或密码错误!----------------");
                    return new ModelAndView("redirect:login");
                }
            } else {
                request.getSession().setAttribute(BaseConfig.MESSAGE, "验证码错误!");
                log.debug("------------------验证码错误!----------------");
                return new ModelAndView("redirect:login");
            }
        } else {
            return new ModelAndView("redirect:login");
        }
    }

    /**
     * 退出登录
     */
    @RequestMapping(value = "logout")
    public ModelAndView logout(HttpServletRequest request) {
        request.getSession().removeAttribute(BaseConfig.USER);
        log.debug("-------------------清除session 退出用户!----------------");
        return new ModelAndView("redirect:login");
    }

}
