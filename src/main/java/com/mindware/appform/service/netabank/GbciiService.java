package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Gbcii;
import com.mindware.appform.repository.netbank.GbciiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GbciiService {

    @Autowired
    GbciiMapper mapper;

    public List<Gbcii> getAll() {
        return mapper.getAll();
    }

    public Optional<Gbcii> findByGbcii(Integer gbciiciiu){
        return mapper.findByGbciiu(gbciiciiu);
    }
}
