package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Cacon;
import com.mindware.appform.repository.netbank.CaconMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaconService {

    @Autowired
    private CaconMapper mapper;

    public List<Cacon> getAll(){
        return mapper.getAll();
    }

    public List<Cacon> getByPref(Integer caconpref){
        return mapper.getByPref(caconpref);
    }
}
