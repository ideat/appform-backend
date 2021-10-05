package com.mindware.appform.util;

import com.mindware.appform.dto.DataContractSavingBankDto;
import com.mindware.appform.entity.TemplateContract;
import com.mindware.appform.entity.netbank.dto.CamcaCatcaDto;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.service.DataContractDtoService;
import com.mindware.appform.service.TemplateContractService;
import com.mindware.appform.service.netabank.CamcaCatcaDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
public class NameTemplateContract {

    @Value("${path_template}")
    private String pathContract;

    @Autowired
    private DataContractDtoService dataContractDtoService;

    @Autowired
    private CamcaCatcaDtoService camcaCatcaDtoService;

    @Autowired
    private TemplateContractService templateContractService;

    private static File templateFile;

    public String generateNameTemplateContract(String login, String account, String typeAccount, Integer plaza,
                                                String categoryTypeForm, String isYunger) throws IOException {
        String nameContract="";
        DataContractSavingBankDto dataContractSavingBankDto = new DataContractSavingBankDto();

        if(categoryTypeForm.equals("CAJA-AHORRO")) {
            dataContractSavingBankDto = dataContractDtoService.getDataContractSavingBank(account, login, isYunger);
            CamcaCatcaDto camcaCatcaDto = camcaCatcaDtoService.findByAccount(account);

            nameContract = createNameContract(categoryTypeForm, typeAccount, dataContractSavingBankDto.getTotalParticipants(),
                    camcaCatcaDto.getProductName().trim(), isYunger);
        }else{
            dataContractSavingBankDto = dataContractDtoService.getDatacontractDpf2(account,login);
            nameContract = createNameContract(categoryTypeForm, typeAccount, dataContractSavingBankDto.getTotalParticipants(),"",isYunger);
        }

        Optional<TemplateContract> templateContract = templateContractService.findByFileName(nameContract);
        if(templateContract.isPresent()){
            if(templateContract.get().getActive().equals("NO")) {
               nameContract ="invalid";
            }
        }else{
            nameContract ="invalid";
//            throw new AppException(String.format("Plantilla %s no registrada, consulte con el administrador", nameTemplate), HttpStatus.BAD_REQUEST);
        }

        return nameContract;

    }

    private String createNameContract(String categoryTypeForm, String typeAccount, Integer totalParticipants, String product, String isYunger){
        String nameContract = "";
        if(categoryTypeForm.equals("CAJA-AHORRO")){
            nameContract="CAH";
            if(isYunger.equals("NO")) {
                if (typeAccount.equals("INDIVIDUAL")) nameContract = nameContract + "-IND";
                if (typeAccount.equals("CONJUNTA")) nameContract = nameContract + "-CON";
                if (typeAccount.equals("ALTERNA")) nameContract = nameContract + "-ALT";
            }else{
                nameContract = nameContract + "-MEN";
            }
            if(product.equals("CAJA DE AHORRO EFICAZ")) product = "EFICAZ";
            if(product.equals("CAJA DE AHORRO FUTURO")) product = "FUTURO";
            if(product.equals("CAJA DE AHORRO DINAMICA")) product = "DINAMICA";
            if(product.equals("CAJA DE AHORRO PROACTIVA")) product = "PROACTIVA";

            if(!product.equals("EFICAZ") && !product.equals("FUTURO") && !product.equals("DINAMICA")  && !product.equals("PROACTIVA")){
                product = "TRADICIONAL";
            }

            nameContract = nameContract+"-"+totalParticipants.toString();
            nameContract = nameContract +"-"+product  +".docx";
        }else{
            nameContract = "DPF" + "-"+totalParticipants.toString() +".docx";
        }

        return nameContract;
    }
}
