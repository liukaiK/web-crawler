package com.webcrawler.interceptor;

import com.webcrawler.config.BaseConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * 用户登陆过滤器
 *
 * @author liukaiK
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

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
