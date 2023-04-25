package com.mindware.appform.service;

import com.mindware.appform.entity.TemplateContract;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.TemplateContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TemplateContractService {

    @Autowired
    TemplateContractMapper mapper;

    public TemplateContract add(TemplateContract templateContract){
        Optional<TemplateContract> result = mapper.findByFileName(templateContract.getFileName());

        if(result.isPresent()){
            throw new AppException("Nombre Contrato ya esta registrado", HttpStatus.BAD_REQUEST);
        }
        UUID id = UUID.randomUUID();
        templateContract.setId(id.toString());
        mapper.add(templateContract);

        return templateContract;
    }

    public TemplateContract update(TemplateContract templateContract){
        Optional<TemplateContract> result = mapper.findByFileName(templateContract.getFileName());
        if(result.isPresent() && !result.get().getId().equals(templateContract.getId())){
            throw new AppException("Nombre de archivo de contrato ya esta registrado", HttpStatus.BAD_REQUEST);
        }
        mapper.update(templateContract);

        return templateContract;
    }

    public TemplateContract updateActive(TemplateContract templateContract){

        mapper.update(templateContract);
        return templateContract;
    }

    public List<TemplateContract> findAll() {
        return mapper.findAll();
    }

    public List<TemplateContract> findByCategory(String category){
        return mapper.findByCategory(category);
    }

    public Optional<TemplateContract> findByFileName(String fileName){
        return mapper.findByFileName(fileName);
    }

    public Optional<TemplateContract> findByTypeSavingBox(String typeSavingBox){
        return mapper.findByTypeSavingBox(typeSavingBox);
    }
}
