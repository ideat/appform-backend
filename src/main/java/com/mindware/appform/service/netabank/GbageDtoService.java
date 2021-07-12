package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.GbageDto;
import com.mindware.appform.repository.netbank.GbageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GbageDtoService {

    @Autowired
    private GbageDtoMapper mapper;

    public List<GbageDto> findGbageCamcaByCage(Integer cage){
        List<GbageDto> gbageDtoList = mapper.findGbageCamcaByCage(cage);
        return gbageDtoList;
    }

    public List<GbageDto> findGbagePfmdpByCage(Integer cage){
        List<GbageDto> gbageDtoList = mapper.findGbagePfmdpByCage(cage);
        return gbageDtoList;
    }

    public List<GbageDto> findGbageByCage(Integer cage){
        List<GbageDto> gbageDtoList = mapper.findGbageByCage(cage);
        return gbageDtoList;
    }

    public List<GbageDto> findGbageCamcaByCardNumber(String cardNumber){
        List<GbageDto> gbageDtoList = mapper.findGbageCamcaByCardNumber(cardNumber);
        return gbageDtoList;
    }

    public List<GbageDto> findGbagePfmdpByCardNumber(String cardNumber){
        List<GbageDto> gbageDtoList = mapper.findGbagePfmdpByCardNumber(cardNumber);
        return gbageDtoList;
    }

    public List<GbageDto> findGbageByCardNumber(String cardNumber){
        List<GbageDto> gbageDtoList = mapper.findGbageByCardNumber(cardNumber);
        return gbageDtoList;
    }
}
