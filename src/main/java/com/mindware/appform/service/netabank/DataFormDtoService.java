package com.mindware.appform.service.netabank;

import com.mindware.appform.entity.netbank.GbageLabDto;
import com.mindware.appform.entity.netbank.Gblab;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.repository.netbank.DataFormDtoMapper;
import com.mindware.appform.repository.netbank.GbageLabDtoMapper;
import com.mindware.appform.repository.netbank.GblabMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataFormDtoService {

    @Autowired
    GblabMapper gblabMapper;

    @Autowired
    GbageLabDtoMapper gbageLabDtoMapper;

    @Autowired
    DataFormDtoMapper dataFormDtoMapper;

    public DataFormDto findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(Integer cage, String account, String category){
        DataFormDto dataFormDto = new DataFormDto();
        if(category.equals("CAJA-AHORRO")){
            dataFormDto = dataFormDtoMapper.findDataFormForSavingBank(cage,account);
        }else{
            dataFormDto = dataFormDtoMapper.findDataFormForDpf(cage,account);
        }


        Double income = 0.0;
        List<Gblab> gblabList = gblabMapper.findGblabByCage(cage);

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

        if(dataFormDto.getActivity1()==null || dataFormDto.getActivity1().trim().equals("")){
            if(gblabList.size()>1) {
                dataFormDto.setActivity1(gblabList.get(0).getGblabdact());
                dataFormDto.setActivity2(gblabList.get(1).getGblabdact());
            }
            if(gblabList.size()==1){
                dataFormDto.setActivity1(gblabList.get(0).getGblabdact());
            }
        }
        if(dataFormDto.getCodeSpouse()!=null) {
            List<GbageLabDto> gbageLabDtoList = gbageLabDtoMapper.findGbageLabByCage(dataFormDto.getCodeSpouse());
            String name = gbageLabDtoList.get(0).getGbagenomb()!=null?gbageLabDtoList.get(0).getGbagenomb():"";
            name = name.replace("¥","Ñ");
            dataFormDto.setFullNameSpouse(name);
            dataFormDto.setActivitySpouse(gbageLabDtoList.get(0).getGblabdact()!=null?gbageLabDtoList.get(0).getGblabdact():"");
        }
        dataFormDto.setIncomeMountly(income);

        return dataFormDto;
    }
}
