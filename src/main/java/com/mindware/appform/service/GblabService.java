package com.mindware.appform.service;

import com.mindware.appform.entity.netbank.Gblab;
import com.mindware.appform.repository.netbank.GblabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GblabService {

    @Autowired
    GblabMapper mapper;

    public List<Gblab> findGblabByCage(Integer cage){
        return mapper.findGblabByCage(cage);
    }

}
