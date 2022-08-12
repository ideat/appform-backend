package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.GbdanGbageDto;
import com.mindware.appform.repository.netbank.GbdanGbageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GbdanGbageDtoService {

    @Autowired
    private GbdanGbageDtoMapper mapper;

    public Optional<GbdanGbageDto> findGbdanGbageDtoByCage(Integer cage){
        return mapper.findGbdanGbageDtoByCage(cage);
    }
}
