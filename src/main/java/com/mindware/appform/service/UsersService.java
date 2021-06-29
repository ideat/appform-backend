package com.mindware.appform.service;

import com.mindware.appform.dto.SignUpDto;
import com.mindware.appform.entity.Users;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.CharBuffer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsersService {

    @Autowired
    private UsersMapper mapper;

    @Autowired
    private  PasswordEncoder passwordEncoder;

    public Users add(Users users){
        Optional<Users> result = mapper.findByLogin(users.getLogin());

        if(result.isPresent()){
            throw new AppException("Login ya existe", HttpStatus.BAD_REQUEST);
        }

        UUID id = UUID.randomUUID();
        users.setId(id.toString());
        users.setPassword(passwordEncoder.encode(CharBuffer.wrap(users.getPassword())));
        mapper.add(users);
        return users;
    }

    public Users update(Users users){

        mapper.update(users);
        return users;
    }

    public void updatePassword(Users users){
        users.setPassword(passwordEncoder.encode(CharBuffer.wrap(users.getPassword())));
        mapper.updatePassword(users);

    }

    public List<Users> findAll(){

        return mapper.findAll();
    }

    public Optional<Users> findByLogin(String login){
        Optional<Users> result = mapper.findByLogin(login);

        return result;
    }
}
