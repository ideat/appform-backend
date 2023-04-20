package com.mindware.appform.util;

import com.mindware.appform.dto.DataContractSavingBankDto;
import com.mindware.appform.entity.ContractData;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.TemplateContract;
import com.mindware.appform.entity.VariableContract;
import com.mindware.appform.entity.netbank.Catca;
import com.mindware.appform.entity.netbank.dto.CamcaCatcaDto;
import com.mindware.appform.entity.netbank.dto.PfmdpGbageDto;
import com.mindware.appform.exceptions.AppException;
import com.mindware.appform.service.DataContractDtoService;
import com.mindware.appform.service.FormsService;
import com.mindware.appform.service.TemplateContractService;
import com.mindware.appform.service.VariableContractService;
import com.mindware.appform.service.netabank.CamcaCatcaDtoService;
import com.mindware.appform.service.netabank.CatcaService;
import com.xandryex.WordReplacer;
import com.xandryex.utils.TextReplacer;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

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

    @Value("${path_template}")
    private String pathContract;

    @Value("${path_word}")
    private String pathWord;

    @Value("${path_pdf}")
    private String pathPdf;

    @Autowired
    private VariableContractService variableContractService;

    @Autowired
    private GenerateContractData generateContractData;

    @Autowired
    private ConvertWordToPdf convertWordToPdf;

    @Autowired
    private DataContractDtoService dataContractDtoService;

    @Autowired
    private CamcaCatcaDtoService camcaCatcaDtoService;

    @Autowired
    private TemplateContractService templateContractService;

    @Autowired
    private FormsService formsService;

    @Autowired
    private CatcaService catcaService;

    public String generateContractSavingBank(Integer codeClient, String login, String account, String typeAccount, Integer plaza,
                                             String typeForm, String categoryTypeForm, String isTutor, String isYunger, Integer typeSavingBox) throws Exception {

        String nameContract="";
        DataContractSavingBankDto dataContractSavingBankDto = new DataContractSavingBankDto();
        if(categoryTypeForm.equals("CAJA-AHORRO")) {
            dataContractSavingBankDto = dataContractDtoService.getDataContractSavingBank(account, login, isYunger, plaza.toString());
            CamcaCatcaDto camcaCatcaDto = camcaCatcaDtoService.findByAccount(account);

            nameContract = createNameContract(categoryTypeForm, typeAccount, dataContractSavingBankDto.getTotalParticipants(),
                    camcaCatcaDto.getProductName().trim(),isYunger,typeSavingBox);
        }else{
            dataContractSavingBankDto = dataContractDtoService.getDatacontractDpf2(account,login,plaza);
            nameContract = createNameContract(categoryTypeForm, typeAccount, dataContractSavingBankDto.getTotalParticipants(),
                    "",isYunger, typeSavingBox);
        }

        Optional<TemplateContract> templateContract = templateContractService.findByFileName(nameContract);
        Optional<Forms> forms = formsService.findByIClientIdAccountAndTypeFormAndCategoryTypeForm(codeClient.toString(),account,"FORMULARIO APERTURA",categoryTypeForm);

        if(templateContract.isPresent()){
            if(templateContract.get().getActive().equals("NO")) {
                throw new AppException(String.format("Plantilla %s no activa, consulte con el administrador", nameContract), HttpStatus.BAD_REQUEST);
            }
        }else{

            throw new AppException(String.format("Plantilla %s no registrada, consulte con el administrador", nameContract), HttpStatus.BAD_REQUEST);
        }

        if(forms.isPresent()){
           dataContractSavingBankDto.setNameClientVinculation(forms.get().getNameClientVinculation());
           dataContractSavingBankDto.setDocumentClientVinculation(forms.get().getDocumentClientVinculation());
        }

        try {
            templateFile = new File(pathContract + nameContract);
            replacer = new TextReplacer();
            wordReplacer = new WordReplacer(templateFile);
        }catch (Exception e){
            throw new AppException("Plantilla de nombre: " + nameContract +
                    ", no encontrada, verifique que la plantilla este cargada", HttpStatus.BAD_REQUEST);
        }


        mapContractData = getMapDataContract(dataContractSavingBankDto);
        Map<String,String> mapDataContractVariableSimple = getDataForMapContractVariable("SIMPLE");
        mapWithDataContractVariable = replaceDataForMapContractVariable(mapDataContractVariableSimple);

        mapWithDataContractVariable.forEach((k,v)->{
            if(v.equals("$us.") || v.equals("$US")) v="USD";
//            if(v.contains("$")) v.replaceAll("$","S");
            wordReplacer.replaceWordsInText(k,v);
            wordReplacer.replaceWordsInTables(k,v);
        });
       String currentDate =  Util.formatDate(new Date(),"ddMMyyyyHHmm");

        String nameWord = account + "-"+currentDate+".docx";
        String namePdf = account + "-"+currentDate+".pdf";
        wordReplacer.saveAndGetModdedFile(pathWord + nameWord);
        convertWordToPdf.convert(pathWord + nameWord,pathPdf + namePdf);
        return  pathPdf + namePdf;
    }

    private String createNameContract(String categoryTypeForm, String typeAccount,
                                      Integer totalParticipants, String product,
                                      String isYunger, Integer typeSavingBox){
        String nameContract = "";
        if(categoryTypeForm.equals("CAJA-AHORRO")){
//            nameContract="CAH-" + typeSavingBox.toString();
            //TODO: implement select product accordin code product sending for client

            if(isYunger.equals("NO")) {
//                if (typeAccount.equals("INDIVIDUAL")) nameContract = nameContract + "-IND";
//                if (typeAccount.equals("CONJUNTA")) nameContract = nameContract + "-CON";
//                if (typeAccount.equals("ALTERNA")) nameContract = nameContract + "-ALT";

                nameContract = nameContract + "-" + typeAccount;

            }else{
                nameContract = nameContract + "-MENORES";
            }
//            if(product.equals("CAJA DE AHORRO EFICIENTE") || product.equals("EFICIENTE EMPLEADOS")) product = "EFICIENTE";
//            if(product.equals("CAJA DE AHORRO FUTURO") || product.equals("FUTURO EMPLEADOS")) product = "FUTURO";
//            if(product.equals("CAJA DE AHORRO DINAMICA") || product.equals("DINAMICA EMPLEADOS")) product = "DINAMICA";
//            if(product.equals("CAJA DE AHORRO PROACTIVA") || product.equals("PROACTIVA EMPLEADOS")) product = "PROACTIVA";
//
//            if(!product.equals("EFICIENTE") && !product.equals("FUTURO") && !product.equals("DINAMICA")  && !product.equals("PROACTIVA")){
//                product = "TRADICIONAL";
//            }

            List<TemplateContract> templateContractList = templateContractService.findAll();
            boolean finded = false;
            for(TemplateContract templateContract:templateContractList){
                List<String> list = Arrays.asList(templateContract.getTypeSavingBox().split(","));
                for(String s:list){
                    Integer code = Integer.valueOf(s.split("-")[0]);
                    if(code.equals(typeSavingBox)) {
                        nameContract = templateContract.getFileName();
                        finded = true;
                    }
                }
                if(finded) break;
            }

//            nameContract = nameContract+"-"+totalParticipants.toString()+".docx";

        }else{
            nameContract = "DPF" + "-"+totalParticipants.toString() +".docx";
        }

        return nameContract;
    }

    private Map<String,String> getMapDataContract(DataContractSavingBankDto dataContractSavingBankDto) throws NoSuchFieldException, IllegalAccessException {
        Class aClass = DataContractSavingBankDto.class;
        Field[] fields = aClass.getDeclaredFields();
        Map<String,String> map = new HashMap<>();
        for(Field f: fields ){
            Field field = dataContractSavingBankDto.getClass().getDeclaredField(f.getName());
            map.put(f.getName(),field.get(dataContractSavingBankDto)!=null?field.get(dataContractSavingBankDto).toString():"");
        }
        return map;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SneakyThrows
    public void generateContract(Integer codeClient, String account, String typeForm, String categoryTypeForm, String isTutor) throws IOException {

//        generateContractData = new GenerateContractData();


        ContractData contractData = generateContractData.getContractData(codeClient, account, typeForm, categoryTypeForm, isTutor);

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
        String pathWord = "c://auto-form//contract//3051756195-CA-MEGAFUSION.docx";
        String pathPdf = "c://auto-form//contract//3051756195-CA-MEGAFUSION.pdf";
        wordReplacer.saveAndGetModdedFile(pathWord);
        convertWordToPdf.convert(pathWord,pathPdf);
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
                resultMap.replace(k,v,value.trim().replace("¥","Ñ")); // @nomberCompleto,fullName,Jose Luis

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
