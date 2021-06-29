package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.dto.CamcaPfmdp;
import com.mindware.appform.service.netabank.CamcaPfmdpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class CamcaPfmdpController {

    private Integer cage;

    @Autowired
    private CamcaPfmdpService service;

    @GetMapping(value = "/v1/findCamcaPfmdp", name = "Busca Caja ahorro y DPF")
    ResponseEntity<List<CamcaPfmdp>> findCamcaPfmdpByCage(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("cage")) cage = Integer.parseInt(value);
        });

        List<CamcaPfmdp> camcaPfmdpList = service.findCamcaPfmdpByCage(cage);

        return new ResponseEntity<>(camcaPfmdpList, HttpStatus.OK);
    }
}
