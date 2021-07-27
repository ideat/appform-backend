package com.mindware.appform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.appform.dto.FormVerifyIdCardDtoReport;
import com.mindware.appform.entity.DataIdCard;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.netbank.dto.AdusrOfi;
import com.mindware.appform.service.netabank.AdusrOfiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormsVerifyIdCardDtoReportService {

    @Autowired
    FormsService formsService;

    @Autowired
    AdusrOfiService adusrOfiService;

    public List<FormVerifyIdCardDtoReport>  generate(String id, String login){

        Forms forms = formsService.findById(id);
        AdusrOfi adusrOfi = adusrOfiService.findByLogin(login);
        List<FormVerifyIdCardDtoReport> result = new ArrayList<>();

        List<DataIdCard> dataIdCardList = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        try {
            dataIdCardList = mapper.readValue(forms.getIdCardForVerification(), new TypeReference<List<DataIdCard>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        String city = adusrOfi.getGbofides4()!=null?adusrOfi.getGbofides4().split("-")[0]:"";

        for(DataIdCard d:dataIdCardList){
            FormVerifyIdCardDtoReport r = new FormVerifyIdCardDtoReport();
            r.setFullName(d.getFullName());
            r.setIdCard(d.getNumberId());
            r.setExtension(d.getExtension());
            r.setCity(city);
            r.setCreationDate(forms.getCreationDate());
            result.add(r);
        }

        return result;
    }
}
