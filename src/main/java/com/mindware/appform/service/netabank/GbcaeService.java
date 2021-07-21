package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Gbcae;
import com.mindware.appform.repository.netbank.GbcaeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GbcaeService {

    @Autowired
    GbcaeMapper mapper;

    public List<Gbcae> getAll() {
        return mapper.getAll();
    }

    public Optional<Gbcae> findByCiiu(Integer gbciiciiu){
        return mapper.findByCiiu(gbciiciiu);
    }
}
