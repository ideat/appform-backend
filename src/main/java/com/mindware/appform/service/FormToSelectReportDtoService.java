package com.mindware.appform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.appform.dto.FormToSelectReportDto;
import com.mindware.appform.entity.AccountServiceOperation;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.repository.FormsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FormToSelectReportDtoService {

   @Autowired
   FormsMapper mapper;

   ObjectMapper objectMapper = new ObjectMapper();


   public List<FormToSelectReportDto> getFormToSelectReportDto(Integer idClient) throws JsonProcessingException {
      List<Forms> formsList = mapper.findByIdCliente(idClient);

      List<FormToSelectReportDto> formToSelectReportDtoList = new ArrayList<>();
      for (Forms forms : formsList) {
         if (forms.getNameTypeForm().equals("FORMULARIO APERTURA")) {
            FormToSelectReportDto formToSelectReportDto = new FormToSelectReportDto();
            formToSelectReportDto.setId(forms.getId());
            formToSelectReportDto.setIdClient(forms.getIdClient());
            formToSelectReportDto.setIdAccount(forms.getIdAccount());
            formToSelectReportDto.setAccount(forms.getAccounts());
            formToSelectReportDto.setNameTypeForm(forms.getNameTypeForm());
            formToSelectReportDto.setCategoryTypeForm(forms.getCategoryTypeForm());
            formToSelectReportDtoList.add(formToSelectReportDto);
         } else if (forms.getNameTypeForm().equals("BANCA DIGITAL")) {
            List<AccountServiceOperation> accountServiceOperationList = objectMapper
                    .readValue(forms.getAccountServiceOperation(), new TypeReference<List<AccountServiceOperation>>() {
                    });

            for (AccountServiceOperation accountServiceOperation : accountServiceOperationList) {
               if (accountServiceOperation.getAccount() != null) {
                  FormToSelectReportDto formToSelectReportDto = new FormToSelectReportDto();
                  formToSelectReportDto.setId(accountServiceOperation.getId());
                  formToSelectReportDto.setIdClient(forms.getIdClient());
                  formToSelectReportDto.setIdAccount(accountServiceOperation.getId());
                  formToSelectReportDto.setAccount(accountServiceOperation.getAccount());
                  formToSelectReportDto.setNameTypeForm(forms.getNameTypeForm());
                  formToSelectReportDto.setCategoryTypeForm(forms.getCategoryTypeForm());
                  formToSelectReportDtoList.add(formToSelectReportDto);
               }
            }
         } else if (forms.getNameTypeForm().equals("SERVICIOS TD")) {
            List<AccountServiceOperation> accountServiceOperationList = objectMapper
                    .readValue(forms.getAccountServiceOperation(), new TypeReference<List<AccountServiceOperation>>() {
                    });
            for (AccountServiceOperation accountServiceOperation : accountServiceOperationList) {
               if (accountServiceOperation.getAccountSavingBank() != null) {
                  FormToSelectReportDto formToSelectReportDto = new FormToSelectReportDto();
                  formToSelectReportDto.setId(accountServiceOperation.getId());
                  formToSelectReportDto.setIdClient(forms.getIdClient());
                  formToSelectReportDto.setIdAccount(accountServiceOperation.getId());
                  formToSelectReportDto.setAccount(accountServiceOperation.getAccountSavingBank());
                  formToSelectReportDto.setNameTypeForm(forms.getNameTypeForm());
                  formToSelectReportDto.setCategoryTypeForm(forms.getCategoryTypeForm());
                  formToSelectReportDtoList.add(formToSelectReportDto);
               }
            }
         }

      }
      return formToSelectReportDtoList;
   }
}
