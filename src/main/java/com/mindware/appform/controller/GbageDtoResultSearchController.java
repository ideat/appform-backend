package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.dto.GbageDto;
import com.mindware.appform.service.netabank.GbageDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class GbageDtoResultSearchController {

    @Autowired
    private GbageDtoService service;
    private String searchBy;
    private String criteria;

    @GetMapping(value="/v1/search", name = "Busca datos cliente por carnet o codigo cliente")
    public ResponseEntity<List<GbageDto>> findGbageDtoResult(@RequestHeader Map<String,String> headers){

        List<GbageDto> gbageDtoList = new ArrayList<>();
        headers.forEach((key,value) -> {
            if(key.equals("search")) searchBy = value.trim();
            if(key.equals("criteria")) criteria = value.trim();

        });
        if(searchBy.equals("cage")){
            gbageDtoList = service.findGbageByCage(Integer.parseInt(criteria));
            gbageDtoList.addAll(service.findGbageCamcaByCage(Integer.parseInt(criteria)));
            gbageDtoList.addAll(service.findGbagePfmdpByCage(Integer.parseInt(criteria)));
        }else{
            gbageDtoList = service.findGbageByCardNumber(criteria);
            gbageDtoList.addAll(service.findGbageCamcaByCardNumber(criteria));
            gbageDtoList.addAll(service.findGbagePfmdpByCardNumber(criteria));
        }

        return new ResponseEntity<>(gbageDtoList, HttpStatus.OK);
    }
}
