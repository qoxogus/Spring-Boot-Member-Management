package com.server.MemberManagement.service;

public interface EmailService {

    void sendMail(String to, String sub, String text);
}
