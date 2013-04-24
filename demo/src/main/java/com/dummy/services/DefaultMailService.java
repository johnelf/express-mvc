package com.dummy.services;

public class DefaultMailService implements MailService {

    @Override
    public void sendMail(String to, String content) {
        System.out.println("--------------------fake mail service---------------");
        System.out.println("TO: " + to);
        System.out.println(content);
        System.out.println("--------------------fake mail service---------------");
    }

}
