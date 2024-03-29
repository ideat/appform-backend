package com.mindware.appform.util;

import com.mindware.appform.dto.FormsDtoReport;
import com.mindware.appform.entity.Contract;
import com.mindware.appform.entity.ContractData;
import com.mindware.appform.service.FormsDtoReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateContractData {

    @Autowired
    FormsDtoReportService formsDtoReportService;

    public ContractData getContractData(Integer codeClient, String account, String typeForm, String categoryTypeForm, String isTutor){

        FormsDtoReport formsDtoReport = formsDtoReportService.generate(codeClient,account, typeForm, categoryTypeForm, isTutor);

        ContractData contractData = new ContractData();
        String fullName = formsDtoReport.getNames().trim().concat(" ")
                .concat( formsDtoReport.getLastName()!=null && !formsDtoReport.getLastName().trim().equals("") ?formsDtoReport.getLastName().trim():"").concat(" ")
                .concat(formsDtoReport.getMotherLastName()!=null && !formsDtoReport.getMotherLastName().trim().equals("") ?formsDtoReport.getMotherLastName().trim():"").concat(" ")
                .concat(formsDtoReport.getMarriedLastName()!=null && !formsDtoReport.getMarriedLastName().trim().equals("") ?"de " +formsDtoReport.getMarriedLastName().trim():"");

        contractData.setFullNameClient(fullName);
        contractData.setFullNamePartner("");
        contractData.setFullNameTutor(formsDtoReport.getNameClientVinculation());
        contractData.setIdCardTutor(formsDtoReport.getDocumentClientVinculation());
        contractData.setIdCardClient(formsDtoReport.getIdCard());
        contractData.setProductName(formsDtoReport.getProduct());
        contractData.setCurrency(formsDtoReport.getCurrency().trim());
        contractData.setCurrencyName("");
        contractData.setRate("");
        contractData.setAddressClientHome(formsDtoReport.getHomeAddress().trim());
        contractData.setNameClientVinculation(formsDtoReport.getNameClientVinculation());
        contractData.setDocumentClientVinculation(formsDtoReport.getDocumentClientVinculation());

        return contractData;
    }
}
