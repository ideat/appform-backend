package com.mindware.appform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.appform.dto.FormDigitalBankDtoReport;
import com.mindware.appform.entity.AccountServiceOperation;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.Parameter;
import com.mindware.appform.entity.netbank.Gbpmt;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.service.netabank.AdusrOfiService;
import com.mindware.appform.service.netabank.DataFormDtoService;
import com.mindware.appform.service.netabank.GbpmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormsDigitalBankDtoReportService {

    @Autowired
    FormsService formsService;

    @Autowired
    DataFormDtoService dataFormDtoService;

    @Autowired
    AdusrOfiService adusrOfiService;

    @Autowired
    ParameterService parameterService;

    @Autowired
    GbpmtService gbpmtService;

    public FormDigitalBankDtoReport generate(Integer codClient, String typeForm, String categoryForm, String idAccountServiceOperation) throws JsonProcessingException {

        FormDigitalBankDtoReport result = new FormDigitalBankDtoReport();

        List<DataFormDto> dataFormDtoList = dataFormDtoService.findDataFormForDigitalBank(codClient);

        List<Parameter> parameterList = parameterService.findByCategory("LIMITES BANCA DIGITAL");

        Double dailyLimitSus = Double.parseDouble(parameterList.stream()
                .filter(p -> p.getName().equals("LIMITE MAXIMO DIARIO"))
                .collect(Collectors.toList()).get(0).getValue());
        Double extendMaxLimitSus = Double.parseDouble(parameterList.stream()
                .filter(p -> p.getName().equals("LIMITE MAXIMO CON AMPLIACION"))
                .collect(Collectors.toList()).get(0).getValue());

        Gbpmt gbpmt = gbpmtService.findAll();

        Forms formsDigitalBanking = formsService.findByIdClientAndTypeFormAndCategoryTypeForm(codClient,typeForm,categoryForm).get();
        List<AccountServiceOperation> accountServiceOperationList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        accountServiceOperationList = mapper.readValue(formsDigitalBanking.getAccountServiceOperation(), new TypeReference<List<AccountServiceOperation>>() {});
        AccountServiceOperation accountServiceOperation = accountServiceOperationList.stream()
                .filter(f -> f.getId().equals(idAccountServiceOperation))
                .collect(Collectors.toList()).get(0);


        result.setCreateDate(accountServiceOperation.getCreateDate());
        result.setOfficeName(dataFormDtoList.get(0).getOfficeName());
        result.setUserCreate(formsDigitalBanking.getIdUser());
        result.setFullNameClient(dataFormDtoList.get(0).getFullNameClient());
        result.setHomeAddress(dataFormDtoList.get(0).getAddressHome());
        result.setIdCard(dataFormDtoList.get(0).getIdCard());
        result.setCellphone(dataFormDtoList.get(0).getCellphone());
        result.setHomePhone(dataFormDtoList.get(0).getHomePhone());
        result.setEmail(dataFormDtoList.get(0).getEmail());
        result.setUserDigitalBank(formsDigitalBanking.getUserDigitalBank());
        result.setAccounts(accountServiceOperation.getAccount());

        result.setMaxDailyLimitSus(dailyLimitSus);
        result.setMaxExtendLimitSus(extendMaxLimitSus);
        result.setMaxDailyLimitBs(dailyLimitSus/gbpmt.getGbpmttcof());
        result.setMaxExtendLimitBs(extendMaxLimitSus/gbpmt.getGbpmttcof());

        List<com.mindware.appform.entity.Service> serviceOperationList = new ArrayList<>();
        serviceOperationList = mapper.readValue(accountServiceOperation.getServices(), new TypeReference<List<com.mindware.appform.entity.Service>>() {});

        List<com.mindware.appform.entity.Service> serviceList = serviceOperationList.stream()
                .filter(f -> f.getCategory().equals("BANCA DIGITAL, SERVICIOS"))
                .collect(Collectors.toList());

        List<com.mindware.appform.entity.Service> serviceList2 = new ArrayList<>();
        List<com.mindware.appform.entity.Service> operationList2 = new ArrayList<>();
        String imgCheck = getClass().getResource("/template-report/img/check.png").getPath();
        String imgUnchecked = getClass().getResource("/template-report/img/unchecked.png").getPath();
        for(com.mindware.appform.entity.Service s : serviceList){
            if(s.getName().equals("AMPLIACION DE LIMITE HASTA") && s.getChecked().equals("SI")){
                s.setAmount(accountServiceOperation.getExtensionAmount());
                s.setAmountSus(accountServiceOperation.getExtensionAmount()/gbpmt.getGbpmttcof());
            }
            if(s.getName().equals("DISMINUCION DE LIMITE HASTA") && s.getChecked().equals("SI")){
                s.setAmount(accountServiceOperation.getDecreaseAmount());
                s.setAmountSus(accountServiceOperation.getDecreaseAmount()/gbpmt.getGbpmttcof());
            }
//            if(s.getChecked().equals("SI")){
//                s.setChecked(imgCheck);
//            }else{
//                s.setChecked(imgUnchecked);
//            }
            serviceList2.add(s);
        }
        List<com.mindware.appform.entity.Service> operationList = serviceOperationList.stream()
                .filter(f -> f.getCategory().equals("BANCA DIGITAL, OPERACIONES"))
                .collect(Collectors.toList());
        for(com.mindware.appform.entity.Service s : operationList){
//            if(s.getChecked().equals("SI")){
//                s.setChecked(imgCheck);
//            }else{
//                s.setChecked(imgUnchecked);
//            }
            operationList2.add(s);
        }

        result.setListService(serviceList2);
        result.setListOperation(operationList2);
        result.setReason(accountServiceOperation.getReasonOpening());

        return result;
    }
}
