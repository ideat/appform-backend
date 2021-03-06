package com.mindware.appform.controller;

import com.mindware.appform.dto.FormDebitCardDtoReport;
import com.mindware.appform.dto.FormDigitalBankDtoReport;
import com.mindware.appform.dto.FormVerifyIdCardDtoReport;
import com.mindware.appform.dto.FormsDtoReport;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.repository.FormsMapper;
import com.mindware.appform.service.*;
import com.mindware.appform.service.netabank.DataFormDtoService;
import com.mindware.appform.util.PrinterReportJasper;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
@RequestMapping(value ="/rest",  produces = {"application/json"})
public class FormController {
    private Integer idClient;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String isTutor;
    private String nameOffice;

    @Autowired
    private FormsMapper mapper;

    @Autowired
    private FormsService service;

    @Autowired
    private DataFormDtoService dataFormDtoService;

    @Autowired
    FormsDtoReportService formsDtoReportService;

    @Autowired
    FormsDebitCardDtoReportService formsDebitCardDtoReportService;

    @Autowired
    FormsDigitalBankDtoReportService formsDigitalBankDtoReportService;

    @Autowired
    FormsVerifyIdCardDtoReportService formsVerifyIdCardDtoReportService;

    @Value("${path_image}")
    private String path;

    @PostMapping(value = "/v1/form/create", name = "Crear formulario")
    ResponseEntity<Forms> create (@RequestBody Forms forms){
        if (forms.getId()==null){
            return new ResponseEntity<>(service.create(forms), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
        }

    }

    @PutMapping(value = "/v1/form/update", name = "Actualiza formulario")
    ResponseEntity<Forms> update (@RequestBody Forms forms){

        return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByIdClient", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findById(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
        });

        Forms forms = mapper.findByIdCliente(idClient);

        return new ResponseEntity<>(forms, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByIdAccountAndTypeFormAndCategoryTypeForm", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findByIdAccountAndTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_account")) value1 = value;
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        Optional<Forms> forms = mapper.findByIdAccountAndTypeFormAndCategoryTypeForm(value1,value2,value3);
        Forms result = forms.isPresent()?forms.get():new Forms();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByIdClientAndTypeFormAndCategoryTypeForm", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findByIdClientAndTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        Optional<Forms> forms = mapper.findByIdClientAndTypeFormAndCategoryTypeForm(idClient,value2,value3);
        Forms result = forms.isPresent()?forms.get():new Forms();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByTypeFormAndCategoryTypeForm", name ="Formulario por tipo y categoria")
    ResponseEntity<List<Forms>> findByTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        List<Forms> result = service.findByTypeFormAndCategoryTypeForm(value2,value3);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/findByUserTypeFormAndCategoryTypeForm", name ="Formulario por tipo y categoria")
    ResponseEntity<List<Forms>> findByUserTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("user")) value1 = value;
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        List<Forms> result = service.findByUserTypeFormAndCategoryTypeForm(value2,value3,value1);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value ="/v1/form/findDataFormDtoFormSavingBoxOrDpfByCageAndAccount", name ="Datos para formulario de cajas de ahorro")
    ResponseEntity<DataFormDto> findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("cage")) idClient = Integer.parseInt(value);
            if(key.equals("account")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
            if(key.equals("is-tutor")) isTutor = value;
        });

        DataFormDto result = dataFormDtoService.findDataFormDtoFormSavingBoxOrDpfByCageAndAccount(idClient, value2,value3,isTutor);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping(value ="/v1/form/findDataFormForDigitalBank", name ="Datos  de cajas de ahorro")
    ResponseEntity<List<DataFormDto>> findDataFormForDigitalBank(@RequestHeader Map<String,String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("cage")) idClient = Integer.parseInt(value);
        });

        List<DataFormDto> result = dataFormDtoService.findDataFormForDigitalBank(idClient);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value ="/v1/form/getFormSavingBankAndDpfReport", name ="Reporte formulario apertura")
    public @ResponseBody byte[] getFormSavingBankAndDpfReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("code_client")) idClient = Integer.parseInt(value);
            if(key.equals("id_account")) value2 = value;
            if(key.equals("type_form")) value3 = value;
            if(key.equals("category_type_form")) value4 = value;
            if(key.equals("is-tutor")) isTutor = value;
        });

        FormsDtoReport result = formsDtoReportService.generate(idClient, value2, value3, value4,isTutor);
        List<FormsDtoReport> collection = new ArrayList<>();

        collection.add(result);
        InputStream stream = getClass().getResourceAsStream("/template-report/saving-bank-dpf/openingSavingBankDpf.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String check = path +"/check.png";
        String unchecked = path + "/unchecked.png";
        String pathSubreport ="template-report/saving-bank-dpf/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);
        params.put("check",check);
        params.put("unchecked",unchecked);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

    @GetMapping(value ="/v1/form/getFormDigitalBankDtoReport", name ="Reporte formulario banca digital")
    public @ResponseBody byte[] getFormDigitalBankDtoReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("code_client")) idClient = Integer.parseInt(value);
            if(key.equals("id_account_service_operation")) value2 = value;
            if(key.equals("type_form")) value3 = value;
            if(key.equals("category_type_form")) value4 = value;
            if(key.equals("name-office")) nameOffice = value;
        });

        FormDigitalBankDtoReport result = formsDigitalBankDtoReportService.generate(idClient, value3, value4, value2);
        result.setOfficeName(nameOffice);
        List<FormDigitalBankDtoReport> collection = new ArrayList<>();

        collection.add(result);
        InputStream stream = getClass().getResourceAsStream("/template-report/digital-bank/digitalBank.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String check = path +"/check.png";
        String unchecked = path + "/unchecked.png";
        String pathSubreport ="template-report/digital-bank/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);
        params.put("check",check);
        params.put("unchecked",unchecked);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

    @GetMapping(value ="/v1/form/getFormDebitCardDtoReport", name ="Reporte formulario Servicios TD")
    public @ResponseBody byte[] getFormDebitCardDtoReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("code_client")) idClient = Integer.parseInt(value);
            if(key.equals("id_account_service_operation")) value2 = value;
            if(key.equals("type_form")) value3 = value;
            if(key.equals("category_type_form")) value4 = value;
            if(key.equals("name-office")) nameOffice = value;
        });

        FormDebitCardDtoReport result = formsDebitCardDtoReportService.generate(idClient, value3, value4, value2);
        result.setOfficeName(nameOffice);
        List<FormDebitCardDtoReport> collection = new ArrayList<>();

        collection.add(result);
        InputStream stream = getClass().getResourceAsStream("/template-report/debit-card/debitCardService.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String check = path +"/check.png"; //getClass().getResource("/template-report/img/check.png").getPath();
        String unchecked = path + "/unchecked.png";  //getClass().getResource("/template-report/img/unchecked.png").getPath();
//        String check =  "C:/auto-form/check.png";
//        String unchecked = "C:/auto-form/unchecked.png";
        String pathSubreport ="template-report/debit-card/";
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("path_subreport", pathSubreport);
        params.put("check",check);
        params.put("unchecked",unchecked);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

    @GetMapping(value ="/v1/form/getFormDeliverDebitCardReport", name ="Reporte formulario Servicios TD")
    public @ResponseBody byte[] getFormDeliverDebitCardReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("code_client")) idClient = Integer.parseInt(value);
            if(key.equals("id_account_service_operation")) value2 = value;
            if(key.equals("type_form")) value3 = value;
            if(key.equals("category_type_form")) value4 = value;
            if(key.equals("name-office")) nameOffice = value;
        });

        FormDebitCardDtoReport result = formsDebitCardDtoReportService.generate(idClient, value3, value4, value2);
        result.setOfficeName(nameOffice);
        List<FormDebitCardDtoReport> collection = new ArrayList<>();

        collection.add(result);
        InputStream stream = getClass().getResourceAsStream("/template-report/debit-card/deliverDebitCard.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        String check = path +"/check.png"; //getClass().getResource("/template-report/img/check.png").getPath();
        String unchecked = path + "/unchecked.png"; //getClass().getResource("/template-report/img/unchecked.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        params.put("check",check);
        params.put("unchecked",unchecked);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }


    @GetMapping(value ="/v1/form/getFormVerifyIdCardDtoReport", name ="Reporte Verificacion SEGIP")
    public @ResponseBody byte[] getFormVerifyIdCardDtoReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("id")) value2 = value;
            if(key.equals("login")) value3 = value;

        });

        List<FormVerifyIdCardDtoReport> collection = formsVerifyIdCardDtoReportService.generate(value2, value3);
//        List<FormDebitCardDtoReport> collection = new ArrayList<>();

//        collection.add(result);
        InputStream stream = getClass().getResourceAsStream("/template-report/verification-idcard/verificationIdCard.jrxml");
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();
        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,collection,params);
        InputStream is = new ByteArrayInputStream(b);

        return IOUtils.toByteArray(is);

    }

    //SERVICIOS SISTEMA DE KIOSCO
    @PostMapping(value = "/v1/kiosco/form/create", name = "Crear formulario")
    ResponseEntity<Forms> createFromKiosco (@RequestBody Forms forms){
        if (forms.getId()==null){
            return new ResponseEntity<>(service.create(forms), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(service.update(forms),HttpStatus.OK);
        }

    }

    @GetMapping(value ="/v1/kiosco/form/findFromKioscoByIdClientAndTypeFormAndCategoryTypeForm", name ="Formulario por ID Cliente")
    ResponseEntity<Forms> findFromKioscoByIdClientAndTypeFormAndCategoryTypeForm(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
            if(key.equals("name_type_form")) value2 = value;
            if(key.equals("category_type_form")) value3 = value;
        });

        Optional<Forms> forms = mapper.findByIdClientAndTypeFormAndCategoryTypeForm(idClient,value2,value3);
        Forms result = forms.isPresent()?forms.get():new Forms();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
