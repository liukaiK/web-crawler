package com.esd.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.esd.config.BaseConfig;


/**
 * 用户登陆过滤器
 *
 * @author liukaiK
 */
public class LoginInterceptor implements HandlerInterceptor {

    private Logger logger = Logger.getLogger(LoginInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) {
        if (request.getRequestURI().contains("/admin")) {
            Object obj = request.getSession().getAttribute(BaseConfig.USER);
            if (obj == null) {
                try {
                    response.setContentType("text/html");
                    response.setCharacterEncoding("utf-8");
                    PrintWriter out = response.getWriter();
                    String builder = "<script type=\"text/javascript\" charset=\"UTF-8\">" + "alert(\"请重新登陆！\");" + "window.location.href=\"/login\"" + "</script>";
                    out.print(builder);
                    out.close();
                    return false;
                } catch (IOException e) {
                    logger.error(e.getMessage());
                    return false;
                }
            }
        }
        return true;
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3) {

    }

    public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3) {
    }
}
