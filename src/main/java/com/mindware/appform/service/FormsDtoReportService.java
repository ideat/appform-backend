package com.mindware.appform.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindware.appform.dto.FormsDtoReport;
import com.mindware.appform.entity.Beneficiary;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.service.netabank.DataFormDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormsDtoReportService {

    @Autowired
    FormsService formsService;

    @Autowired
    DataFormDtoService dataFormDtoService;

    public FormsDtoReport generate(Integer codeClient, String idAccount, String typeForm, String categoryTypeForm, String isTutor){
        FormsDtoReport result = new FormsDtoReport();
        Optional<Forms> formsResult =Optional.empty();
        DataFormDto dataFormDto = new DataFormDto();

        if(categoryTypeForm.equals("CAJA-AHORRO")|| categoryTypeForm.equals("DPF")){
            formsResult = formsService.findByIClientIdAccountAndTypeFormAndCategoryTypeForm(codeClient.toString(), idAccount,typeForm,categoryTypeForm);
            dataFormDto = dataFormDtoService.findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(codeClient, idAccount,categoryTypeForm, isTutor);

        }else{
            formsResult = formsService.findByIdClientAndTypeFormAndCategoryTypeForm(codeClient,typeForm,categoryTypeForm);
        }
        if(formsResult.isEmpty()){
            throw new AppException("No existe el formulario, Primero creelo", HttpStatus.BAD_REQUEST);
        }
        if(formsResult.get().getCategoryTypeForm().equals("CAJA-AHORRO")){
            result.setCategory("CAJA DE AHORRO");
        }else if (formsResult.get().getCategoryTypeForm().equals("DPF")){
            result.setCategory("DEPÓSITO A PLAZO FIJO");
        }

        result.setProduct(dataFormDto.getProduct());
        result.setCurrency(dataFormDto.getCurrency());
        result.setOffice(dataFormDto.getOfficeName());
        result.setOpeningDate(dataFormDto.getOpeningDate());
        result.setCodeClient(dataFormDto.getCodeClient());
        result.setLastName(dataFormDto.getLastName());
        result.setMotherLastName(dataFormDto.getMotherLastName());
        result.setMarriedLastName(dataFormDto.getMarriedLastName());
        result.setNames(dataFormDto.getNames());
        result.setTypeIdCard(dataFormDto.getTypeDocument());
        result.setIdCard(dataFormDto.getIdCard());
        result.setExpirateDate(dataFormDto.getExpiredDate());
        result.setGender(dataFormDto.getGender());
        result.setCivilStatus(dataFormDto.getCivilStatus());
        result.setBirthDate(dataFormDto.getBornDate());
        result.setFullNameSpouse(dataFormDto.getFullNameSpouse());
        result.setActivitySpouse(dataFormDto.getActivitySpouse());
        result.setDepartament(dataFormDto.getDepartament());
        result.setProvince(dataFormDto.getProvince());
        result.setHomeAddress(dataFormDto.getAddressHome());
        result.setProfession(dataFormDto.getProfession());
        result.setIncomeMounthly(dataFormDto.getIncomeMountly());
        result.setZone(dataFormDto.getZone());
        result.setCity(dataFormDto.getCity());
        result.setEconomicActivity1(dataFormDto.getActivity1());
        result.setEconomicActivity2(dataFormDto.getActivity2());
        result.setHomeTelephone(dataFormDto.getHomePhone());
        result.setCellphone(dataFormDto.getCellphone());
        result.setReasonOpening(dataFormDto.getReasonOpeningAccount());
        result.setTypeVinculation(formsResult.get().getLinkingAccount());
        result.setIsFinalBeneficiary(formsResult.get().getIsFinalBeneficiary());
        result.setCountry(dataFormDto.getCountry());
        result.setNameClientVinculation(formsResult.get().getNameClientVinculation());
        result.setDocumentClientVinculation(formsResult.get().getDocumentClientVinculation());
        result.setSourceFounds(formsResult.get().getSourceFounds());
        if(formsResult.get().getLinkingAccount()!=null) {
            if (formsResult.get().getLinkingAccount().equals("PADRES O TUTORES LEGALES")) {
                result.setLabel1("Nombre del Menor");
                result.setLabel2("Número de Documento");
                result.setNameClientVinculation(formsResult.get().getNameClientVinculation());
                result.setDocumentClientVinculation(formsResult.get().getDocumentClientVinculation());
            } else if (formsResult.get().getLinkingAccount().equals("APODERADOS")) {
                result.setLabel1("Nombre o Razon Social");
                result.setLabel2("Número de Documento/Nit");
            } else if (formsResult.get().getLinkingAccount().equals("REPRESENTANTE LEGAL")) {
                result.setLabel1("Empresa o Razon Social");
                result.setLabel2("Número de Documento/Nit");
            } else {
                result.setLabel1("");
                result.setNameClientVinculation("");
                result.setDocumentClientVinculation("");
            }
        }
        if(formsResult.get().getIsFinalBeneficiary()!=null) {
            if (formsResult.get().getIsFinalBeneficiary().equals("NO")) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    if (formsResult.get().getBeneficiary() != null && !formsResult.get().getBeneficiary().equals("[]")) {
                        List<Beneficiary> beneficiaryList = mapper.readValue(formsResult.get().getBeneficiary(), new TypeReference<List<Beneficiary>>() {
                        });
                        result.setBeneficiaryList(beneficiaryList);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }
}
