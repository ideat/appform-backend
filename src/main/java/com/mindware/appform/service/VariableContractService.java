package com.mindware.appform.service;

import com.mindware.appform.entity.VariableContract;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.VariableContractMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VariableContractService {

    @Autowired
    private VariableContractMapper mapper;

    public VariableContract create(VariableContract variableContract){
        Optional<VariableContract> result = mapper.findByName(variableContract.getName());

        if(result.isPresent()){
            throw new AppException("Nombre Variable ya esta registrado", HttpStatus.BAD_REQUEST);
        }
        UUID id = UUID.randomUUID();
        variableContract.setId(id.toString());
        mapper.add(variableContract);
        return variableContract;
    }

    public VariableContract update(VariableContract variableContract){
        Optional<VariableContract> result = mapper.findByName(variableContract.getName());
        if(result.isPresent() && !result.get().getId().equals(variableContract.getId())){
            throw new AppException("Nombre de variable de contrato ya esta registrado", HttpStatus.BAD_REQUEST);
        }
        mapper.update(variableContract);

        return variableContract;
    }

    public List<VariableContract> findAll() {
        return mapper.findAll();
    }


}
