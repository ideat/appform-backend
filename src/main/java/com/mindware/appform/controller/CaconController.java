package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.Cacon;
import com.mindware.appform.service.netabank.CaconService;
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
@RequestMapping(value = "/rest", produces = {"application/json"})
public class CaconController {

    private Integer caconpref;

    @Autowired
    private CaconService service;

    @GetMapping(value = "/v1/cacon/findCaconByPref", name="Busca parametro por prefijo")
    ResponseEntity<List<Cacon>> findCaconByPref(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("caconpref")) caconpref = Integer.valueOf(value);
        });

        List<Cacon> caconList = service.getByPref(caconpref);
        return new ResponseEntity<>(caconList, HttpStatus.OK);
    }
}
