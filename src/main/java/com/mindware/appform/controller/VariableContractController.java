package com.mindware.appform.controller;

import com.mindware.appform.entity.VariableContract;
import com.mindware.appform.service.VariableContractService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class VariableContractController {

    @Autowired
    private VariableContractService service;

    @PostMapping(value = "/v1/variable-contract/create", name = "Crear Variable de contrato")
    ResponseEntity<VariableContract> create(@RequestBody VariableContract variableContract){
        if(variableContract.getId() == null){
            return new ResponseEntity<>(service.create(variableContract), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(variableContract),HttpStatus.OK);
        }
    }

    @GetMapping(value = "/v1/variable-contract/findAll", name = "Obtiene todas las variable")
    ResponseEntity<List<VariableContract>> findAll(){
        List<VariableContract> variableContractList = service.findAll();
        return new ResponseEntity<>(variableContractList,HttpStatus.OK);
    }


}
