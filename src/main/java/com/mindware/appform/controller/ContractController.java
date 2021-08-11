package com.mindware.appform.controller;

import com.mindware.appform.util.WordReplaceTextContract;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class ContractController {

    @Value("${path_contract}")
    private String path;

    @Autowired
    WordReplaceTextContract wordReplaceTextContract;

    private String pathContract;
    private Integer codeClient;
    private String account;
    private String typeForm;
    private String categoryTypeForm;

    @GetMapping(value="/v1/contract/getFileContract", name = "Obtiene el contrato generado")
    public @ResponseBody byte[] getFileContract(@RequestHeader Map<String,String> headers) throws IOException {
        headers.forEach((key,value)->{
            if(key.equals("code-client")) codeClient = Integer.valueOf(value) ;
            if(key.equals("account")) account = value;
            if(key.equals("type-form")) typeForm = value;
            if(key.equals("category-type-form")) categoryTypeForm = value;
        });

        wordReplaceTextContract.generateContract(codeClient, account, typeForm, categoryTypeForm);


        Path path = Paths.get("c://auto-form//contract//3051756195-CA-MEGAFUSION.pdf");

        byte[] bFile = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(bFile);
        return IOUtils.toByteArray(is);
    }
}
