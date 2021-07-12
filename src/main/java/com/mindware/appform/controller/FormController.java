package com.mindware.appform.controller;

import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.repository.FormsMapper;
import com.mindware.appform.service.FormsService;
import com.mindware.appform.service.netabank.DataFormDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class FormController {
    private Integer idClient;
    private String value1;
    private String value2;
    private String value3;

    @Autowired
    private FormsMapper mapper;

    @Autowired
    private FormsService service;

    @Autowired
    private DataFormDtoService dataFormDtoService;

    @PostMapping(value = "/v1/form/create", name = "Crear formulario")
    ResponseEntity<Forms> create (@RequestBody Forms forms){
        if (forms.getId()==null){
            return new ResponseEntity<>(service.create(forms), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
        }

    }

    @PutMapping(value = "/v1/form/update", name = "Actualiza formulario")
    ResponseEntity<Forms> update (@RequestBody Forms forms){

        return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByIdClient", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findById(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
        });

        Forms forms = mapper.findByIdCliente(idClient);

        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByIdAccountAndTypeFormAndCategoryTypeForm", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findByIdAccountAndTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_account")) value1 = value;
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        Optional<Forms> forms = mapper.findByIdAccountAndTypeFormAndCategoryTypeForm(value1,value2,value3);
        Forms result = forms.isPresent()?forms.get():new Forms();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findDataFormDtoFormSavingBoxOrDpfByCageAndAccount", name ="Datos para formulario de cajas de ahorro")
    ResponseEntity<DataFormDto> findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("cage")) idClient = Integer.parseInt(value);
            if(key.equals("account")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        DataFormDto result = dataFormDtoService.findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(idClient, value2,value3);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }


}
