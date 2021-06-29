package com.mindware.appform.service;

import com.mindware.appform.entity.Rol;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.RolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RolService {

    @Autowired
    RolMapper mapper;

    public Rol add(Rol rol){
        Optional<Rol> result = findByName(rol.getName());

        if(result.isPresent()){
            throw new AppException("Nombre Rol ya esta registrado", HttpStatus.BAD_REQUEST);
        }

        UUID id = UUID.randomUUID();
        rol.setId(id.toString());
        mapper.add(rol);
        return rol;
    }

    public Rol update(Rol rol){
        Optional<Rol> result = findByName(rol.getName());
        if(result.isPresent() && !result.get().getId().equals(rol.getId())){
            throw new AppException("Nombre Rol esta registrado en otro ROL", HttpStatus.BAD_REQUEST);
        }
        mapper.update(rol);
        return rol;
    }

    public List<Rol> findAll() {
        return mapper.findAll();
    }

    public Optional<Rol> findByName(String name){
        return mapper.findByName(name);

    }


}
