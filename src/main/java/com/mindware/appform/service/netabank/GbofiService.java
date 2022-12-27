package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.Gbofi;
import com.mindware.appform.repository.netbank.GbofiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GbofiService {

    @Autowired
    GbofiMapper mapper;

    public Optional<Gbofi> findByNofi(Integer gbofinofi){
        return mapper.findByNofi(gbofinofi);
    }
}
