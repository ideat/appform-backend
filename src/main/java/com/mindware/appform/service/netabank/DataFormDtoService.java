package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import com.mindware.appform.entity.netbank.Gbcae;
import com.mindware.appform.entity.netbank.Gblab;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.repository.netbank.DataFormDtoMapper;
import com.mindware.appform.repository.netbank.GbageLabDtoMapper;
import com.mindware.appform.repository.netbank.GbcaeMapper;
import com.mindware.appform.repository.netbank.GblabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataFormDtoService {

    @Autowired
    GblabMapper gblabMapper;

    @Autowired
    GbageLabDtoMapper gbageLabDtoMapper;

    @Autowired
    DataFormDtoMapper dataFormDtoMapper;

    @Autowired
    GbcaeMapper gbcaeMapper;

    public List<DataFormDto> findDataFormForDigitalBank(Integer cage){
        List<DataFormDto> dataFormDtoList = new ArrayList<>();
        dataFormDtoList = dataFormDtoMapper.findDataFormForDigitalBank(cage);

        if(dataFormDtoList.size()==0){
            throw new AppException("Cliente no tiene cuentas activas", HttpStatus.BAD_REQUEST);
        }

        Double income = 0.0;
        List<Gblab> gblabList = gblabMapper.findGblabByCage(cage);
        List<Gbcae> gbcaeList = gbcaeMapper.getAll();

        String ape1 = dataFormDtoList.get(0).getLastName()!=null?dataFormDtoList.get(0).getLastName():"";
        ape1 = ape1.replace("¥","Ñ");
        String ape2 = dataFormDtoList.get(0).getMotherLastName()!=null? dataFormDtoList.get(0).getMotherLastName():"";
        ape2 = ape2.replace("¥","Ñ");
        String ape3 = dataFormDtoList.get(0).getMarriedLastName()!=null? dataFormDtoList.get(0).getMarriedLastName():"";
        ape3 = ape3.replace("¥","Ñ");
        String names = dataFormDtoList.get(0).getNames();
        names = names.replace("¥","Ñ");

        dataFormDtoList.get(0).setLastName(ape1);
        dataFormDtoList.get(0).setMotherLastName(ape2);
        dataFormDtoList.get(0).setMarriedLastName(ape3);
        dataFormDtoList.get(0).setNames(names);

        income = gblabList.stream().filter( g-> g.getGblabmont() != null && g.getGblabmont()>0)
                .map(x -> x.getGblabmont())
                .reduce(0.0,Double::sum);
        String gcae = dataFormDtoList.get(0).getActivity1();
        List<Gbcae> gbcae = gbcaeList.stream()
                .filter(g -> g.getGbcaeciiu()!=null &&  g.getGbcaeciiu() ==Integer.parseInt(gcae))
                .collect(Collectors.toList());
        if(gbcae.size()>0){
            dataFormDtoList.get(0).setActivity1(gbcae.get(0).getGbcaedesc());
        }

        if(dataFormDtoList.get(0).getActivity1()==null || dataFormDtoList.get(0).getActivity1().trim().equals("")){
            List<Gblab> aux2 = gblabList.stream()
                    .filter(g -> g.getGblabmpri() == null || (g.getGblabmpri() != 1 && !g.getGblabdact().isEmpty()))
                    .collect(Collectors.toList());
            if (aux2.size() > 0) {
                dataFormDtoList.get(0).setActivity2(aux2.get(0).getGblabdact());
            }
        }
        if(dataFormDtoList.get(0).getCodeSpouse()!=null) {
            List<GbageLabDto> gbageLabDtoList = gbageLabDtoMapper.findGbageLabDtoByCage(dataFormDtoList.get(0).getCodeSpouse());
            String name = gbageLabDtoList.get(0).getGbagenomb()!=null?gbageLabDtoList.get(0).getGbagenomb():"";
            name = name.replace("¥","Ñ");
            dataFormDtoList.get(0).setFullNameSpouse(name);
            dataFormDtoList.get(0).setActivitySpouse(gbageLabDtoList.get(0).getGblabdact()!=null?gbageLabDtoList.get(0).getGblabdact():"");
        }
        dataFormDtoList.get(0).setIncomeMountly(income);

        return dataFormDtoList;
    }

    public DataFormDto findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(Integer cage, String account, String category, String isTutor){
        DataFormDto dataFormDto = new DataFormDto();
        if(category.equals("CAJA-AHORRO")){
            if(isTutor.equals("SI")){
                dataFormDto = dataFormDtoMapper.findDataFormForSavingBankTutor(cage, account);
            }else {
                dataFormDto = dataFormDtoMapper.findDataFormForSavingBank(cage, account);
            }
        }else{
            dataFormDto = dataFormDtoMapper.findDataFormForDpf(cage,account);
            if(dataFormDto==null){
                dataFormDto = dataFormDtoMapper.findDataFormForDpfPftit(cage,account);
            }
        }

        Double income = 0.0;
        List<Gblab> gblabList = gblabMapper.findGblabByCage(cage);
        List<Gbcae> gbcaeList = gbcaeMapper.getAll();

        String ape1 = dataFormDto.getLastName()!=null?dataFormDto.getLastName():"";
        ape1 = ape1.replace("¥","Ñ");
        String ape2 = dataFormDto.getMotherLastName()!=null? dataFormDto.getMotherLastName():"";
        ape2 = ape2.replace("¥","Ñ");
        String ape3 = dataFormDto.getMarriedLastName()!=null? dataFormDto.getMarriedLastName():"";
        ape3 = ape3.replace("¥","Ñ");
        String names = dataFormDto.getNames();
        names = names.replace("¥","Ñ");

        dataFormDto.setLastName(ape1);
        dataFormDto.setMotherLastName(ape2);
        dataFormDto.setMarriedLastName(ape3);
        dataFormDto.setNames(names);

        income = gblabList.stream().filter( g-> g.getGblabmont() != null && g.getGblabmont()>0)
                .map(x -> x.getGblabmont())
                .reduce(0.0,Double::sum);
        String gcae = dataFormDto.getActivity1();
        List<Gbcae> gbcae = gbcaeList.stream()
                .filter(g -> g.getGbcaeciiu()!=null &&  g.getGbcaeciiu() ==Integer.parseInt(gcae))
                .collect(Collectors.toList());
        if(gbcae.size()>0){
            dataFormDto.setActivity1(gbcae.get(0).getGbcaedesc());
        }

//        if(dataFormDto.getActivity1()==null || dataFormDto.getActivity1().trim().equals("")){
            List<Gblab> aux2 = gblabList.stream()
                    .filter(g -> g.getGblabmpri() == null || (g.getGblabmpri() != 1 && g.getGblabdact() != null))
                    .collect(Collectors.toList());
            if (aux2.size() > 0) {
                dataFormDto.setActivity2(aux2.get(0).getGblabdact()!=null?aux2.get(0).getGblabdact():"NO APLICA");
            }
//        }
        if(dataFormDto.getCodeSpouse()!=null) {
            List<GbageLabDto> gbageLabDtoList = gbageLabDtoMapper.findGbageLabDtoByCage(dataFormDto.getCodeSpouse());

            String name = gbageLabDtoList.get(0).getGbagenomb()!=null?gbageLabDtoList.get(0).getGbagenomb():"NO APLICA";
            name = name.replace("¥","Ñ");
            dataFormDto.setFullNameSpouse(!name.equals("")?name:"NO APLICA");
            String gcae2 = gbageLabDtoList.get(0).getGbageciiu();
            if(!gcae.isEmpty()){
                gbcae = gbcaeList.stream()
                        .filter(g -> g.getGbcaeciiu()!=null &&  g.getGbcaeciiu() ==Integer.parseInt(gcae2))
                        .collect(Collectors.toList());
            }
            if(gbcae.size() > 0){
                dataFormDto.setActivitySpouse(gbcae.get(0).getGbcaedesc()); //(gbageLabDtoList.get(0).getGblabdact()!=null?gbageLabDtoList.get(0).getGblabdact():"");
            }else{
                dataFormDto.setActivitySpouse("NO APLICA");
            }

        }else{
            dataFormDto.setFullNameSpouse("NO APLICA");
            dataFormDto.setActivitySpouse("NO APLICA");
        }
        dataFormDto.setIncomeMountly(income);

        return dataFormDto;
    }
}
