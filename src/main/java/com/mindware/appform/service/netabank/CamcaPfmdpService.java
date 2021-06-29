package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.dto.CamcaPfmdp;
import com.mindware.appform.repository.netbank.CamcaPfmpdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamcaPfmdpService {

    @Autowired
    private CamcaPfmpdMapper mapper;

    public List<CamcaPfmdp> findCamcaPfmdpByCage(Integer cage){
        return mapper.findCamcaPfmdpByCage(cage);
    }
}
