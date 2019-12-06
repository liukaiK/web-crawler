package com.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author liukai
 */
@ServletComponentScan
@SpringBootApplication
public class WebCrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebCrawlerApplication.class, args);
    }

}
