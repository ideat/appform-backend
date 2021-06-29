package com.mindware.appform.service;

import com.mindware.appform.entity.TypeForm;
import com.mindware.appform.repository.TypeFormMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TypeFormService {

    @Autowired
    TypeFormMapper mapper;

    public TypeForm add(TypeForm typeForm){
        UUID id = UUID.randomUUID();
        typeForm.setId(id.toString());
        return mapper.add(typeForm);
    }

    public TypeForm update(TypeForm typeForm){
        return mapper.update(typeForm);
    }

    public List<TypeForm> findAll() {
        return mapper.findAll();
    }
}
