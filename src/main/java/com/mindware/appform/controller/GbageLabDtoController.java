package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.GbageLabDto;
import com.mindware.appform.entity.netbank.Gblab;
import com.mindware.appform.service.netabank.GbageDtoService;
import com.mindware.appform.service.netabank.GbageLabDtoService;
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
public class GbageLabDtoController {

    @Autowired
    private GbageLabDtoService service;

    private String idcard;
    private Integer cage;

    @GetMapping(value ="/v1/gbageLabDto/findGblabDtoByIdCard", name ="Lista datos beneficiarios netbank")
    ResponseEntity<List<GbageLabDto>> findGbageLabDtoByIdCard(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("search")) idcard = value;
        });
        List<GbageLabDto> gblabList = service.findGbageLabDtoByIdCard(idcard);
        return new ResponseEntity<>(gblabList, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/gbageLabDto/findGblabDtoByCage", name ="Lista datos beneficiarios netbank")
    ResponseEntity<List<GbageLabDto>> findGbageLabDtoByCage(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("search")) cage = Integer.parseInt(value);
        });
        List<GbageLabDto> gblabList = service.findGbageLabDtoByCage(cage);
        return new ResponseEntity<>(gblabList, HttpStatus.OK);
    }
}
