package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Gbpmt;
import com.mindware.appform.repository.netbank.GbpmtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GbpmtService {

    @Autowired
    private GbpmtMapper mapper;

    public Gbpmt findAll(){
        return mapper.findAll();
    }
}
