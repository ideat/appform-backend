package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.CamcaCatcaDto;
import com.mindware.appform.repository.netbank.CamcaCatcaDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CamcaCatcaDtoService {

    @Autowired
    private CamcaCatcaDtoMapper mapper;

    public CamcaCatcaDto findByAccount(String account){
        return mapper.findByAccount(account);
    }
}
