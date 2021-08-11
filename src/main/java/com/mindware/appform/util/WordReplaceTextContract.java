package com.mindware.appform.util;

import com.mindware.appform.entity.Contract;
import com.mindware.appform.entity.ContractData;
import com.mindware.appform.entity.VariableContract;
import com.mindware.appform.service.VariableContractService;
import com.xandryex.WordReplacer;
import com.xandryex.utils.TextReplacer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WordReplaceTextContract {
    private TextReplacer replacer;
    private WordReplacer  wordReplacer;
    private static File templateFile;
    private static File replacedFiles;

    private String compoundVariable;
    private   String variable;

    private Map<String,String> mapContractData;
    private Map<String,String> mapWithDataContractVariable;

    @Autowired
    private VariableContractService variableContractService;

    @Autowired
    private GenerateContractData generateContractData;

    @SneakyThrows
    public void generateContract(Integer codeClient, String account, String typeForm, String categoryTypeForm) throws IOException {

//        generateContractData = new GenerateContractData();


        ContractData contractData = generateContractData.getContractData(codeClient, account, typeForm, categoryTypeForm);

        replacer = new TextReplacer();
//        templateFile = new File(contract.getPathTemplate());
        //todo: reemplazar por un path cuando se guarde los contratos
        templateFile = new File("c:\\auto-form\\template\\Contrato Caja de Ahorro Megafusión - ASFI VF ampliacion .docx");
        wordReplacer = new WordReplacer(templateFile);

        mapContractData = getDataForMapContractData(contractData);
        Map<String,String> mapContractVariableSimple = getDataForMapContractVariable("SIMPLE");
        mapWithDataContractVariable = replaceDataForMapContractVariable(mapContractVariableSimple);
//        Map<String,String> mapContractVariableCompound = getDataForMapContractVariable("COMPUESTA");
//        mapWithDataContractVariable.putAll(replaceDataForMapContractVariableCompound(mapContractVariableCompound));

        mapWithDataContractVariable.forEach((k,v)->{
            if(v.equals("$us.")) v="USD";
//            if(v.contains("$")) v.replaceAll("$","S");
            wordReplacer.replaceWordsInText(k,v);
            wordReplacer.replaceWordsInTables(k,v);
        });
//        wordReplacer.saveAndGetModdedFile(contract.getPathContract());
        //todo: reemplazar por el nombre que se va a generar el contrato
        wordReplacer.saveAndGetModdedFile("c://auto-form//contract//3051756195-CA-MEGAFUSION.docx");
    }

    private Map<String,String> getDataForMapContractData(ContractData contractData) throws NoSuchFieldException, IllegalAccessException {
        Class aClass = ContractData.class;
        Field[] fields = aClass.getDeclaredFields();
        Map<String,String> map = new HashMap<>();
        for(Field f: fields ){
            Field field = contractData.getClass().getDeclaredField(f.getName());
            map.put(f.getName(),field.get(contractData)!=null?field.get(contractData).toString():"");
        }
        return map;
    }

    private Map<String,String> getDataForMapContractVariable(String typeVariable){
        Map<String,String> mapContractVariable= new HashMap<>();
        List<VariableContract> contractVariableList = variableContractService.findAll();

        for(VariableContract cv:contractVariableList){
            mapContractVariable.put(cv.getName(),cv.getVariable());
        }
        return mapContractVariable;

    }

    private Map<String,String> replaceDataForMapContractVariable(Map<String,String> mapContractVariable){
        Map<String,String> resultMap = mapContractVariable;
        mapContractVariable.forEach((k,v)->{ //(k,v) -> @nombreCompleto,fullName
            String value= mapContractData.get(v);// get value from mapContractData (Jose Luis)
            if(value!=null && !value.equals(""))
                resultMap.replace(k,v,value); // @nomberCompleto,fullName,Jose Luis

        });
        return resultMap;
    }

    private Map<String,String> replaceDataForMapContractVariableCompound(Map<String,String> mapContractVariableCompound){
        Map<String,String> resultMap = mapContractVariableCompound;
        mapContractVariableCompound.forEach((k,v)->{ //(@Garantes,@nombreCompleto @carnet
            variable = v;
            compoundVariable="";
            mapWithDataContractVariable.forEach((key,value)->{ //@nombreCompleto, Jose Luis
                if(variable.contains(key)){
                    compoundVariable = variable.replace(key,value);
                    variable = compoundVariable;
                }

            });
            resultMap.replace(k,v,compoundVariable);
        });
        return resultMap;
    }

}
