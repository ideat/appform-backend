package com.mindware.appform.controller;

import com.mindware.appform.entity.TemplateContract;
import com.mindware.appform.service.TemplateContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class TemplateContractController {

    @Value("${path_template}")
    private String pathTemplate;

    @Autowired
    private TemplateContractService service;

    @PostMapping(value = "/v1/template-contract/create", name = "Crear Template")
    ResponseEntity<TemplateContract> create(@RequestBody TemplateContract templateContract){
        Path path = Paths.get(pathTemplate +templateContract.getFileName());
        templateContract.setPathContract(path.toString());
        if(templateContract.getId()==null){
            return  new ResponseEntity<>(service.add(templateContract), HttpStatus.CREATED);
        }else{
            return  new ResponseEntity<>(service.update(templateContract), HttpStatus.OK);
        }
    }

    @PutMapping(value = "/v1/template-contract/updatestate", name = "Actualiza el estado")
    ResponseEntity<TemplateContract> updateActive(@RequestBody TemplateContract templateContract){
        return  new ResponseEntity<>(service.updateActive(templateContract),HttpStatus.OK);
    }

    @GetMapping(value = "/v1/template-contract/findAll", name = "Obtiene todas las plantillas")
    ResponseEntity<List<TemplateContract>> findAll() {
        List<TemplateContract> templateContractList = service.findAll();
        return new ResponseEntity<>(templateContractList,HttpStatus.OK);
    }

    @PostMapping(value = "/v1/template-contract/upload", name = "Sube plantilla contrato")
    public ResponseEntity<String> uploadTemplate(@RequestParam("file") MultipartFile uploadFile, @RequestParam("filename") String filename){

        if (uploadFile.isEmpty()) {
            return new ResponseEntity("Por favor seleccione un archivo", HttpStatus.OK);
        }

        try {

            saveUploadedFiles(Arrays.asList(uploadFile),filename);

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadFile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    private void saveUploadedFiles(List<MultipartFile> files, String filename) throws IOException {

        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue; //next pls
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(pathTemplate +filename);
            Files.write(path, bytes);

        }

    }

    @GetMapping(value = "/v1/template-contract/getByFileName/{filename}", name = "Obtiene plantilla por Nombre plantilla")
    ResponseEntity<TemplateContract> getByFileName(@PathVariable("filename")String fileName){
        Optional<TemplateContract> templateContract = service.findByFileName(fileName);
        if(templateContract.isPresent()) {
            return new ResponseEntity<>(templateContract.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new TemplateContract(), HttpStatus.OK);
        }
    }


}
