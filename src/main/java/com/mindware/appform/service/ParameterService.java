package com.mindware.appform.service;

import com.mindware.appform.entity.Parameter;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.ParameterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ParameterService {
    @Autowired
    ParameterMapper mapper;

    public Parameter create(Parameter parameter){
        Optional<Parameter> param = mapper.findByCategoryAndName(parameter.getCategory(), parameter.getName());
        if(param.isPresent()){
            throw new AppException("Categoria y/o nombre ya estan registrados", HttpStatus.BAD_REQUEST);
        }

        parameter.setId(UUID.randomUUID().toString());
        mapper.create(parameter);

        return parameter;
    }

    public Parameter update(Parameter parameter){
        Optional<Parameter> result = mapper.findByCategoryAndName(parameter.getCategory(), parameter.getName());
        if(result.isPresent() && !result.get().getId().equals(parameter.getId())){
            throw new AppException("Categoria y/o nombre se entran registrados en otro parametro", HttpStatus.BAD_REQUEST);
        }

        mapper.update(parameter);
        return parameter;
    }

    public List<Parameter> findAll() {
        return mapper.findAll();
    }


}

