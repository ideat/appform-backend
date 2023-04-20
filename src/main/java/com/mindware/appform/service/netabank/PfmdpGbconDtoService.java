package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.PfmdpGbconDto;
import com.mindware.appform.repository.netbank.PfmdpGbconDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PfmdpGbconDtoService {

    @Autowired
    private PfmdpGbconDtoMapper mapper;

    public PfmdpGbconDto findPfmdpGbcon(String pfmdpndep){
        return mapper.getPfmdpGbcon(pfmdpndep);
    }
}
