package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import com.mindware.appform.repository.netbank.GbageLabDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GbageLabDtoService {

    @Autowired
    GbageLabDtoMapper mapper;

    public List<GbageLabDto> findGbageLabByCage(Integer cage){
        List<GbageLabDto> gbageDtoList = mapper.findGbageLabByCage(cage);

        return gbageDtoList;
    }
}
