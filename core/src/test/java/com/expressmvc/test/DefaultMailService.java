package com.expressmvc.test;

public class DefaultMailService implements MailService{
    @Override
    public void sendNotificationMailToReader() {
        System.out.println("---fake mail send out---");
    }
}
