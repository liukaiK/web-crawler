package com.crawler;

import com.crawler.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Test1 {

    public static void main(String[] args) {
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();

        Session session = factory.openSession();

        Transaction t = session.beginTransaction();

        User user = new User();
        user.setId("qws");
        user.setPassword("123123");
        user.setUsername("123123");
        user.setEmail("zhizhufan@foxmail.com");
        user.setSex(0);


        session.save(user);

        t.commit();
        session.close();
        factory.close();
        System.out.println("successfully saved");
    }
}
