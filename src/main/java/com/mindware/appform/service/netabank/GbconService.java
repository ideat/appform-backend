package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Gbcon;
import com.mindware.appform.repository.netbank.GbconMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GbconService {
    @Autowired
    GbconMapper mapper;

    public List<Gbcon> findAll(){
        return mapper.findAll();
    }
}


