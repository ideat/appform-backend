package com.mindware.appform.controller;

import com.mindware.appform.entity.email.Mail;
import com.mindware.appform.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class MailController {

    @Autowired
    MailService mailService;

    @PostMapping(value = "/v1/mail/send", name = "Enviar mail")
    ResponseEntity<Mail> create(@RequestBody Mail mail, HttpServletRequest request){
        Thread tmail = new Thread(new Runnable() {
            @Override
            public void run() {
                mailService.sendMail(mail);
            }
        });
        tmail.start();

        return new ResponseEntity<>(mail,HttpStatus.OK);

    }
}
