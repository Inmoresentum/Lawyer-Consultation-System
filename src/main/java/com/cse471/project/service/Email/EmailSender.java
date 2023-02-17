package com.cse471.project.service.Email;

public interface EmailSender {
    void send(String to, String subject, String email);
}
