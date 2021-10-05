package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.GbageDto;
import com.mindware.appform.repository.netbank.GbageDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        List<GbageDto> gbageDtoList2 = mapper.findGbagePftitByCage(cage);
        gbageDtoList.addAll(gbageDtoList2);

        List<GbageDto> result = gbageDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
        return result;
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
        List<GbageDto> gbageDtoList2 = mapper.findGbagePftitByCardNumber(cardNumber);
        gbageDtoList.addAll(gbageDtoList2);
        List<GbageDto> result = gbageDtoList.stream()
                .distinct()
                .collect(Collectors.toList());
        return result;
    }

    public List<GbageDto> findGbageByCardNumber(String cardNumber){
        List<GbageDto> gbageDtoList = mapper.findGbageByCardNumber(cardNumber);
        return gbageDtoList;
    }
}
