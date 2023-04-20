package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Catca;
import com.mindware.appform.repository.netbank.CatcaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatcaService {

    @Autowired
    CatcaMapper mapper;

    public List<Catca> findAll(){
        return mapper.findAll();
    }
}
