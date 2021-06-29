package com.mindware.appform.service;

import com.mindware.appform.entity.Forms;
import com.mindware.appform.repository.FormsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class FormsService {

    @Autowired
    FormsMapper mapper;


    public Forms create(Forms forms){
        UUID id = UUID.randomUUID();
        forms.setId(id.toString());
        mapper.add(forms);
        return forms;
    }

    public Forms update(Forms forms){

        return mapper.update(forms);
    }

    public List<Forms> findFormsByIdUser(String idUser){
        return mapper.findByIdUser(idUser);
    }


}
