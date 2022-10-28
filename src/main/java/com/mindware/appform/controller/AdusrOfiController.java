package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.dto.AdusrOfi;
import com.mindware.appform.service.netabank.AdusrOfiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class AdusrOfiController {

    @Autowired
    private AdusrOfiService service;

    private String login;
    @GetMapping(value = "/v1/adusrofi/findByLogin", name = "Obtiene usuario")
    ResponseEntity<AdusrOfi> findByLogin(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if (key.equals("login")) login = value;
        });

        AdusrOfi result = service.findByLogin(login);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


}
