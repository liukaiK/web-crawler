package com.crawler.system.controller;

import com.crawler.common.TransformUtil;
import com.crawler.config.VoiceConfig;
import com.crawler.system.Config;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 验证码控制器
 */
@Controller
@RequestMapping("/manage")
public class VerifyCodeController {

    private static Logger logger = Logger.getLogger(VerifyCodeController.class);
    // 验证码图片的宽度。
    private int width = 60;
    // 验证码图片的高度。
    private int height = 20;

    @RequestMapping("/verifycode")
    public void code(HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类。
        Random random = new Random();
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Times New Roman", Font.PLAIN, 18);
        // 设置字体。
        g.setFont(font);

        // 画边框。
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, width - 1, height - 1);

        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.GRAY);
        for (int i = 0; i < 10; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // 随机产生4位数字的验证码。
        for (int i = 0; i < 4; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(random.nextInt(10));

            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(200);
            green = random.nextInt(200);
            blue = random.nextInt(200);

            // 产生随机高度 13至height之间
            float imght = 0;
            while (imght <= 12) {
                imght = Float.parseFloat(String.valueOf(random.nextInt(height)));
            }
            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, 13 * i + 6, imght);

            // 将产生的四个随机数组合在一起。

            randomCode.append(strRand);
        }
        // log.debug(randomCode);
        // 将四位数字的验证码保存到Session中。
        session.removeAttribute(Config.VERIFYCODE);
        session.setAttribute(Config.VERIFYCODE, randomCode.toString());

        // 禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        try {
            ServletOutputStream sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
        } catch (Exception e) {
            logger.error("后台登录验证码生成出现错误!");
        }
    }


//    @RequestMapping("/getCode")
//    @ResponseBody
//    public Map<String, String> getCode(HttpServletRequest request) {
//        Map<String, String> map = new HashMap<String, String>();
//        HttpSession session = request.getSession();
//        String code = (String) session.getAttribute("randomCode");
//        VoiceConfig voiceConfig = TransformUtil.getVoiceConfig(request, code);
//        try {
//            String httpPath = TransformUtil.getVoiceHttpPath(voiceConfig, false);
//            map.put("path", httpPath);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return map;
//    }
}
