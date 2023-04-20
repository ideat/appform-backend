package com.mindware.appform.controller;

import com.mindware.appform.entity.netbank.Catca;
import com.mindware.appform.service.netabank.CatcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/rest", produces = {"application/json"})
public class CatcaController {

    @Autowired
    private CatcaService catcaService;

    @GetMapping(value = "/v1/catca/findAll", name = "Tipo de cajas de ahorro")
    ResponseEntity<List<Catca>> findAll(){
        return new ResponseEntity<>(catcaService.findAll(), HttpStatus.OK);
    }
}
