package com.mindware.appform.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindware.appform.dto.FormToSelectReportDto;
import com.mindware.appform.service.FormToSelectReportDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class FormToSelectReportDtoController {


    @Autowired
    FormToSelectReportDtoService formToSelectReportDtoService;

    @GetMapping(value="/v1/formtoselectreport/findByIdClient/{idclient}")
    ResponseEntity<List<FormToSelectReportDto>> findFormSelectReportByIdclient(@PathVariable("idclient") Integer idClient) throws JsonProcessingException {
        return new ResponseEntity<>(formToSelectReportDtoService.getFormToSelectReportDto(idClient), HttpStatus.OK);
    }


}
