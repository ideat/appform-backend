package com.mindware.appform.controller;

import com.mindware.appform.entity.TemplateContract;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.service.TemplateContractService;
import com.mindware.appform.util.NameTemplateContract;
import com.mindware.appform.util.WordReplaceTextContract;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class ContractController {

    @Value("${path_contract}")
    private String path;

    @Autowired
    private WordReplaceTextContract wordReplaceTextContract;

    @Autowired
    private NameTemplateContract nameTemplateContract;

    @Autowired
    private TemplateContractService templateContractService;

    private String pathContract;
    private Integer codeClient;
    private String account;
    private String typeForm;
    private String categoryTypeForm;
    private String isTutor;
    private String login;
    private String typeAccount;
    private Integer plaza;
    private String isYunger;

    private Integer typeSavingBox;

    @GetMapping(value="/v1/contract/getFileContract", name = "Obtiene el contrato generado")
    public @ResponseBody byte[] getFileContract(@RequestHeader Map<String,String> headers) throws Exception {
        headers.forEach((key,value)->{
            if(key.equals("code-client")) codeClient = Integer.valueOf(value) ;
            if(key.equals("account")) account = value;
            if(key.equals("type-form")) typeForm = value;
            if(key.equals("category-type-form")) categoryTypeForm = value;
            if(key.equals("is-tutor")) isTutor = value;
            if(key.equals("login")) login = value;
            if(key.equals("type-account")) typeAccount = value;
            if(key.equals("plaza")) plaza = Integer.valueOf(value);
            if(key.equals("is-yunger")) isYunger = value;
            if(key.equals("type-saving-box")) typeSavingBox = Integer.valueOf(value);
        });

//        wordReplaceTextContract.generateContract(codeClient, account, typeForm, categoryTypeForm, isTutor);

        String pathGenerate= wordReplaceTextContract.generateContractSavingBank(codeClient,login,account,typeAccount,
                plaza, typeForm, categoryTypeForm,isTutor, isYunger,typeSavingBox);

//        Path path = Paths.get("c://auto-form//contract//3051756195-CA-MEGAFUSION.pdf");
        Path path = Paths.get(pathGenerate);
        byte[] bFile = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(bFile);
        return IOUtils.toByteArray(is);
    }

    @GetMapping(value="/v1/contract/getTemplateContract", name = "Obtiene la plantilla de contrato")
    public @ResponseBody byte[] getTemplateContract(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value)->{
            if(key.equals("code-client")) codeClient = Integer.valueOf(value) ;
            if(key.equals("account")) account = value;
            if(key.equals("type-form")) typeForm = value;
            if(key.equals("category-type-form")) categoryTypeForm = value;
            if(key.equals("is-tutor")) isTutor = value;
            if(key.equals("login")) login = value;
            if(key.equals("type-account")) typeAccount = value;
            if(key.equals("plaza")) plaza = Integer.valueOf(value);
            if(key.equals("is-yunger")) isYunger = value;
        });


        String nameTemplate = nameTemplateContract.generateNameTemplateContract(login,account,typeAccount,plaza,categoryTypeForm, isYunger);
        Optional<TemplateContract> templateContract = templateContractService.findByFileName(nameTemplate);
        String pathTemplate="";
        if(templateContract.isPresent()){
            if(templateContract.get().getActive().equals("SI")) {
                pathTemplate = templateContract.get().getPathContract();
            }else{
//                throw new AppException(String.format("Plantilla %s no se encuentra activa, consulte con el administrador", nameTemplate), HttpStatus.BAD_REQUEST);
            return null;
            }
        }else{
//            throw new AppException(String.format("Plantilla %s no registrada, consulte con el administrador", nameTemplate), HttpStatus.BAD_REQUEST);
            return null;
        }

        Path path = Paths.get(pathTemplate);
        byte[] bFile = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(bFile);
        return IOUtils.toByteArray(is);
    }

}
