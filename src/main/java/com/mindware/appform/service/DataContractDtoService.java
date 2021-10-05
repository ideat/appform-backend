package com.mindware.appform.service;

import com.mindware.appform.dto.DataContractSavingBankDto;
import com.mindware.appform.entity.Signatory;
import com.mindware.appform.entity.netbank.dto.AdusrOfi;
import com.mindware.appform.entity.netbank.dto.CamcaCafirGbageDto;
import com.mindware.appform.entity.netbank.dto.GbageDto;
import com.mindware.appform.entity.netbank.dto.PfmdpGbageDto;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.SignatoryMapper;
import com.mindware.appform.repository.netbank.AdusrOfiMapper;
import com.mindware.appform.repository.netbank.CamcaCafirGbageDtoMapper;
import com.mindware.appform.repository.netbank.GbageDtoMapper;
import com.mindware.appform.repository.netbank.PfmdpGbageDtoMapper;
import com.mindware.appform.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DataContractDtoService {

    @Autowired
    CamcaCafirGbageDtoMapper camcaCafirGbageDtoMapper;

    @Autowired
    AdusrOfiMapper adusrOfiMapper;

    @Autowired
    SignatoryMapper signatoryMapper;

    @Autowired
    PfmdpGbageDtoMapper pfmdpGbageDtoMapper;

    @Autowired
    GbageDtoMapper gbageDtoMapper;


    public DataContractSavingBankDto getDataContractSavingBank(String account, String login, String isYunger){
        int i = 1;
        List<CamcaCafirGbageDto> list = new ArrayList<>();
        if(isYunger.equals("SI")) {
            list = new ArrayList(camcaCafirGbageDtoMapper.getDataContractSavingBankTutor(account));
        } else{
            list = new ArrayList(camcaCafirGbageDtoMapper.getDataContractSavingBank(account));
        }
        DataContractSavingBankDto data = new DataContractSavingBankDto();

//        if(isYunger.equals("SI")){
//            list = list.stream()
//                    .filter(value -> value.getCamcancta().equals(account))
//                    .collect(Collectors.toList());
//        }

        for(CamcaCafirGbageDto c : list){
            if(i==1) {
                data.setFullNameClient1(c.getGbagenomb());
                data.setIdCardClient1(c.getGbagendid());
            }else  if(i==2) {
                data.setFullNameClient2(c.getGbagenomb());
                data.setIdCardClient2(c.getGbagendid());
            }else  if(i==3) {
                data.setFullNameClient3(c.getGbagenomb());
                data.setIdCardClient3(c.getGbagendid());
            } else  if(i==4) {
                data.setFullNameClient4(c.getGbagenomb());
                data.setIdCardClient4(c.getGbagendid());
            } else  if(i==4) {
                data.setFullNameClient4(c.getGbagenomb());
                data.setIdCardClient4(c.getGbagendid());
            } else  if(i==5) {
                data.setFullNameClient5(c.getGbagenomb());
                data.setIdCardClient5(c.getGbagendid());
            } else  if(i==6) {
                data.setFullNameClient6(c.getGbagenomb());
                data.setIdCardClient6(c.getGbagendid());
            }
            i++;
        }
        data.setTotalParticipants(list.size());
        data.setCurrencyName(list.get(0).getGbcondesc());
        data.setCurrencyAbre(list.get(0).getGbconabre());
        data.setAccount(list.get(0).getCamcancta());

        AdusrOfi adusrOfi = adusrOfiMapper.findByLogin(login);
        String plaza[] = adusrOfi.getGbofides4().split("-");
        data.setPlaza(plaza[0].trim());
        data.setCurrentDate(Util.formatDate(new Date(),"dd 'de' MMMM 'de' yyyy"));
//        Optional<Signatory> signatory = signatoryMapper.findByPlaza(Integer.valueOf(adusrOfi.getAdusrplaz()));
//        if(signatory.isPresent()){
//            data.setLegalRepresentative(signatory.get().getFullName());
//            data.setIdCardLegalRepresentative(signatory.get().getIdCard());
//            data.setPosition(signatory.get().getPosition());
//            data.setDatePowerNotary(Util.formatDate(signatory.get().getDatePowerNotary(),"dd 'de' MMMM 'de' yyyy"));
//            data.setNumberNotary(signatory.get().getNumberNotary().toString());
//            data.setNotaryName(signatory.get().getNotaryName());
//
//        }else{
//            throw new AppException("Representante legal no registrado", HttpStatus.BAD_REQUEST);
//        }

        return data;
    }

    public DataContractSavingBankDto getDatacontractDpf2(String account, String login){
        int i = 0;
        List<PfmdpGbageDto> pfmdpGbageDtoList = pfmdpGbageDtoMapper.getListDataContractDpf(Double.parseDouble(account));
        DataContractSavingBankDto data = new DataContractSavingBankDto();

        for(PfmdpGbageDto p: pfmdpGbageDtoList){

            if(i==0){
                data.setFullNameClient1(p.getGbagenomb());
                data.setIdCardClient1(p.getGbagendid());
                GbageDto dataGbage1 = gbageDtoMapper.findGbageByCage(p.getPfmdpcage()).get(0);
                data.setCivilStatus1(dataGbage1.getCivilStatus());
                String address1 = (dataGbage1.getAddressHome1()!= null?dataGbage1.getAddressHome1():"")  +
                        (dataGbage1.getAddressHome2()!= null?dataGbage1.getAddressHome2():"");
                data.setAddressHome1(address1);
                i++;
                if(i==1 && p.getPfmdpcag2()!=null && !p.getPfmdpcage().equals(p.getPfmdpcag2())){
                    GbageDto dataGbage2 = gbageDtoMapper.findGbageByCage(p.getPfmdpcag2()).get(0);
                    data.setFullNameClient2(dataGbage2.getGbagenomb());
                    data.setIdCardClient2(dataGbage2.getGbagendid());

                    data.setCivilStatus2(dataGbage2.getCivilStatus());
                    String address2 = (dataGbage2.getAddressHome1()!= null?dataGbage2.getAddressHome1():"")  +
                            (dataGbage2.getAddressHome2()!= null?dataGbage2.getAddressHome2():"");
                    data.setAddressHome2(address2);
                    i++;
                }

            }else
            if(i==1 && !p.getPfmdpcage().equals(p.getPfmdpcag2())){
                GbageDto dataGbage2 = gbageDtoMapper.findGbageByCage(p.getPfmdpcag2()).get(0);
                data.setFullNameClient2(dataGbage2.getGbagenomb());
                data.setIdCardClient2(dataGbage2.getGbagendid());

                data.setCivilStatus2(dataGbage2.getCivilStatus());
                String address2 = (dataGbage2.getAddressHome1()!= null?dataGbage2.getAddressHome1():"")  +
                        (dataGbage2.getAddressHome2()!= null?dataGbage2.getAddressHome2():"");
                data.setAddressHome2(address2);
                i++;
            }else
            if(i==2 && !p.getPfmdpcage().equals(p.getPfmdpcag2())){
                GbageDto dataGbage3 = gbageDtoMapper.findGbageByCage(p.getPfmdpcag2()).get(0);
                data.setFullNameClient3(dataGbage3.getGbagenomb());
                data.setIdCardClient3(dataGbage3.getGbagendid());

                data.setCivilStatus3(dataGbage3.getCivilStatus());
                String address3 = (dataGbage3.getAddressHome1()!= null?dataGbage3.getAddressHome1():"")  +
                        (dataGbage3.getAddressHome2()!= null?dataGbage3.getAddressHome2():"");
                data.setAddressHome3(address3);
                i++;
            }else
            if(i==3 && !p.getPfmdpcage().equals(p.getPfmdpcag2())){
                GbageDto dataGbage4 = gbageDtoMapper.findGbageByCage(p.getPfmdpcag2()).get(0);
                data.setFullNameClient4(dataGbage4.getGbagenomb());
                data.setIdCardClient4(dataGbage4.getGbagendid());

                data.setCivilStatus4(dataGbage4.getCivilStatus());
                String address4 = (dataGbage4.getAddressHome1()!= null?dataGbage4.getAddressHome1():"")  +
                        (dataGbage4.getAddressHome2()!= null?dataGbage4.getAddressHome2():"");
                data.setAddressHome4(address4);
                i++;
            }

        }
        data.setTotalParticipants(i);
        data.setCurrencyName(pfmdpGbageDtoList.get(0).getGbcondesc());
        data.setCurrencyAbre(pfmdpGbageDtoList.get(0).getGbconabre());
        data.setAccount(pfmdpGbageDtoList.get(0).getPfmdpndep());

        AdusrOfi adusrOfi = adusrOfiMapper.findByLogin(login);
        String plaza[] = adusrOfi.getGbofides4().split("-");
        data.setPlaza(plaza[0].trim());
        data.setCurrentDate(Util.formatDate(new Date(),"dd 'de' MMMM 'de' yyyy"));

        return data;
    }

    public DataContractSavingBankDto getDataContractDpf(String account, String login){
        int i = 1;

        PfmdpGbageDto pfmdpGbageDto = pfmdpGbageDtoMapper.getDataContractDpf(Double.parseDouble(account));
        DataContractSavingBankDto data = new DataContractSavingBankDto();

        Integer gbage2 = pfmdpGbageDto.getPfmdpcag2();
        Integer gbage3 = pfmdpGbageDto.getPfmdpcag3();
        Integer gbage4 = pfmdpGbageDto.getPfmdpcag4();

        data.setFullNameClient1(pfmdpGbageDto.getGbagenomb());
        data.setIdCardClient1(pfmdpGbageDto.getGbagendid());

        GbageDto dataGbage1 = gbageDtoMapper.findGbageByCage(pfmdpGbageDto.getPfmdpcage()).get(0);
        data.setCivilStatus1(dataGbage1.getCivilStatus());
        String address1 = (dataGbage1.getAddressHome1()!= null?dataGbage1.getAddressHome1():"")  +
                (dataGbage1.getAddressHome2()!= null?dataGbage1.getAddressHome2():"");
        data.setAddressHome1(address1);

        if(gbage2!=null && !gbage2.equals(pfmdpGbageDto.getPfmdpcage())) {
            GbageDto dataGbage2 = gbageDtoMapper.findGbageByCage(gbage2).get(0);
            data.setFullNameClient2(dataGbage2.getGbagenomb());
            data.setIdCardClient2(dataGbage2.getGbagendid());

            data.setCivilStatus2(dataGbage2.getCivilStatus());
            String address2 = (dataGbage2.getAddressHome1()!= null?dataGbage2.getAddressHome1():"")  +
                    (dataGbage2.getAddressHome2()!= null?dataGbage2.getAddressHome2():"");
            data.setAddressHome2(address2);

            i++;
        }
        if(gbage3!=null && !gbage3.equals(pfmdpGbageDto.getPfmdpcage())) {
            GbageDto dataGbage3 = gbageDtoMapper.findGbageByCage(gbage3).get(0);
            data.setFullNameClient3(dataGbage3.getGbagenomb());
            data.setIdCardClient3(dataGbage3.getGbagendid());

            data.setCivilStatus3(dataGbage3.getCivilStatus());
            String address3 = (dataGbage3.getAddressHome1()!= null?dataGbage3.getAddressHome1():"")  +
                    (dataGbage3.getAddressHome2()!= null?dataGbage3.getAddressHome2():"");
            data.setAddressHome3(address3);
            i++;
        }
        if(gbage4!=null && !gbage4.equals(pfmdpGbageDto.getPfmdpcage())) {
            GbageDto dataGbage4 = gbageDtoMapper.findGbageByCage(gbage4).get(0);
            data.setFullNameClient4(dataGbage4.getGbagenomb());
            data.setIdCardClient4(dataGbage4.getGbagendid());

            data.setCivilStatus4(dataGbage4.getCivilStatus());
            String address4 = (dataGbage4.getAddressHome1()!= null?dataGbage4.getAddressHome1():"")  +
                    (dataGbage4.getAddressHome2()!= null?dataGbage4.getAddressHome2():"");
            data.setAddressHome4(address4);
            i++;
        }

        data.setTotalParticipants(i);
        data.setCurrencyName(pfmdpGbageDto.getGbcondesc());
        data.setCurrencyAbre(pfmdpGbageDto.getGbconabre());
        data.setAccount(pfmdpGbageDto.getPfmdpndep());

        AdusrOfi adusrOfi = adusrOfiMapper.findByLogin(login);
        String plaza[] = adusrOfi.getGbofides4().split("-");
        data.setPlaza(plaza[0].trim());
        data.setCurrentDate(Util.formatDate(new Date(),"dd 'de' MMMM 'de' yyyy"));

        return data;
    }
}
