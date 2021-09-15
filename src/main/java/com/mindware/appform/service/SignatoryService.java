package com.mindware.appform.service;

import com.mindware.appform.entity.Signatory;
import com.mindware.appform.repository.SignatoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SignatoryService {

    @Autowired
    private SignatoryMapper mapper;

    public Signatory add(Signatory signatory){
        UUID id = UUID.randomUUID();
        signatory.setId(id.toString());
        mapper.add(signatory);
        return signatory;
    }

    public Signatory update(Signatory signatory){
        mapper.update(signatory);
        return signatory;
    }

    public List<Signatory> findAll(){
        return mapper.findAll();
    }

    public Optional<Signatory> findByPlaza(Integer plaza){
        return mapper.findByPlaza(plaza);
    }


}
