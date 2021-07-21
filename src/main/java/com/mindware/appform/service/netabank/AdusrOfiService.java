package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.AdusrOfi;
import com.mindware.appform.repository.netbank.AdusrOfiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdusrOfiService {

    @Autowired
    AdusrOfiMapper mapper;

    public AdusrOfi findByLogin(String login){
        return mapper.findByLogin(login);
    }
}
