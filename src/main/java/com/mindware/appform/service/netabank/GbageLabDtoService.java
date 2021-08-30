package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import com.mindware.appform.repository.netbank.GbageLabDtoMapper;
import com.mindware.appform.repository.netbank.GbcaeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GbageLabDtoService {

    @Autowired
    GbageLabDtoMapper mapper;

    public List<GbageLabDto> findGbageLabDtoByCage(Integer cage){
        List<GbageLabDto> gbageDtoList = mapper.findGbageLabDtoByCage(cage);

        return gbageDtoList;
    }

    public List<GbageLabDto> findGbageLabDtoByIdCard(String idcard){
        List<GbageLabDto> gbageDtoList = mapper.findGbageLabDtoByIdCard(idcard);

        return gbageDtoList;
    }
}
