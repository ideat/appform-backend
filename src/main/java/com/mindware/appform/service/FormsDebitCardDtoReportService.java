package com.mindware.appform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.appform.dto.FormDebitCardDtoReport;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FormsDebitCardDtoReportService {

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

    public FormDebitCardDtoReport generate(Integer codClient, String typeForm, String categoryForm, String idAccountServiceOperation) throws JsonProcessingException {

        FormDebitCardDtoReport result = new FormDebitCardDtoReport();

        List<DataFormDto> dataFormDtoList = dataFormDtoService.findDataFormForDigitalBank(codClient);

        Gbpmt gbpmt = gbpmtService.findAll();

        Forms formsDebitCard = formsService.findByIdClientAndTypeFormAndCategoryTypeForm(codClient,typeForm,categoryForm).get();
        List<AccountServiceOperation> accountServiceOperationList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        accountServiceOperationList = mapper.readValue(formsDebitCard.getAccountServiceOperation(), new TypeReference<List<AccountServiceOperation>>() {});
        AccountServiceOperation accountServiceOperation = accountServiceOperationList.stream()
                .filter(f -> f.getId().equals(idAccountServiceOperation))
                .collect(Collectors.toList()).get(0);


        result.setCreateDate(accountServiceOperation.getCreateDate());
        result.setDeliverDate(accountServiceOperation.getDeliverDate()!=null?accountServiceOperation.getDeliverDate():"");
        result.setHourCreate(accountServiceOperation.getHourCreate());
        result.setOfficeName(dataFormDtoList.get(0).getOfficeName());
        result.setUserCreate(formsDebitCard.getIdUser());
        result.setFullNameClient(dataFormDtoList.get(0).getFullNameClient());
        result.setHomeAddress(dataFormDtoList.get(0).getAddressHome());
        result.setIdCard(dataFormDtoList.get(0).getIdCard());
        result.setCellphone(dataFormDtoList.get(0).getCellphone());
        result.setHomePhone(dataFormDtoList.get(0).getHomePhone());

        result.setAccountSavingBank(accountServiceOperation.getAccountSavingBank());

        String numberDebitCard="";
        char[] array = accountServiceOperation.getAccount().toCharArray();
        for(char c:array){
            numberDebitCard = numberDebitCard + c +" | ";
        }
        result.setAccounts(numberDebitCard);

        List<com.mindware.appform.entity.Service> serviceOperationList = new ArrayList<>();
        serviceOperationList = mapper.readValue(accountServiceOperation.getServices(), new TypeReference<List<com.mindware.appform.entity.Service>>() {});

        List<com.mindware.appform.entity.Service> serviceList2 = new ArrayList<>();

        String imgCheck = getClass().getResource("/template-report/img/check.png").getPath();
        String imgUnchecked = getClass().getResource("/template-report/img/unchecked.png").getPath();
        for(com.mindware.appform.entity.Service s : serviceOperationList){
            if(s.getName().equals("AMPLIACION DE LIMITE HASTA") && s.getChecked().equals("SI")){
                s.setAmount(accountServiceOperation.getExtensionAmount());
                s.setAmountSus(accountServiceOperation.getExtensionAmount()/gbpmt.getGbpmttcof());
            }
            if(s.getName().equals("DISMINUCION DE LIMITE HASTA") && s.getChecked().equals("SI")){
                s.setAmount(accountServiceOperation.getDecreaseAmount());
                s.setAmountSus(accountServiceOperation.getDecreaseAmount()/gbpmt.getGbpmttcof());
            }
            if(s.getChecked().equals("SI")){
                s.setChecked(imgCheck);
            }else{
                s.setChecked(imgUnchecked);
            }
            serviceList2.add(s);
        }
        serviceList2.sort(Comparator.comparing(com.mindware.appform.entity.Service::getName));

        result.setListService(serviceList2);
        result.setReason(accountServiceOperation.getReasonOpening());

        return result;
    }
}
