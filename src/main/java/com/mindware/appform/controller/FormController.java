package com.mindware.appform.controller;

import com.mindware.appform.dto.*;
import com.mindware.appform.entity.Forms;
import com.mindware.appform.entity.netbank.dto.DataFormDto;
import com.mindware.appform.entity.netbank.dto.GbageDto;
import com.mindware.appform.repository.FormsMapper;
import com.mindware.appform.service.*;
import com.mindware.appform.service.netabank.DataFormDtoService;
import com.mindware.appform.service.netabank.GbageDtoService;
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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
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
    private String listReports;

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

    @Autowired
    private GbageDtoService gbageDtoService;
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
    ResponseEntity<List<Forms>> findById(@RequestHeader Map<String, String> headers){
        headers.forEach((key,value) -> {
            if(key.equals("id_client")) idClient = Integer.parseInt(value);
        });

        List<Forms> forms = mapper.findByIdCliente(idClient);

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

    @GetMapping(value = "/v1/form/getSelectedReport", name = "Reportes seleccionados")
    public @ResponseBody byte[] getSelectedReport(@RequestHeader Map<String,String> headers) throws IOException, JRException {
        headers.forEach((key,value) -> {
            if(key.equals("code_client")) idClient = Integer.parseInt(value);
            if(key.equals("login")) value2 = value;
            if(key.equals("office_name")) value3 = value;
            if(key.equals("list_reports")) listReports = value;

        });
        FormsDtoReport formsDtoReport = new FormsDtoReport();
        FormDebitCardDtoReport formDebitCardDtoReport = new FormDebitCardDtoReport();
        FormDigitalBankDtoReport formDigitalBankDtoReport = new FormDigitalBankDtoReport();


        SelectedReportDto selectedReportDto = new SelectedReportDto();
        selectedReportDto.setOfficeName(value3);
        selectedReportDto.setLogin(value2);

        String idAccount = "";
        String typeForm = "";
        String categoryTypeForm = "";

        String pathSubReport1="";
        String pathSubReport2="";
        String pathSubReport3="";

        String path1="";
        String path2="";
        String path3="";
        String path4="";
        String path5="";

        String[] formsReport = listReports.split("&");
        int count=0;
        for(String formReport:formsReport){
            String[] form = formReport.split("\\*");

            String pathFormName = form[0].equals("FORMULARIO APERTURA")?"/selected/forms/openingSavingBankDpf.jasper":
                    form[0].equals("BANCA DIGITAL")?"/selected/digitalBank/digitalBank.jasper":
                    form[0].equals("SERVICIOS TD")?"/selected/debitCard/debitCardService.jasper":"";

            String path = form[0].equals("FORMULARIO APERTURA")?"/selected/forms/":
                            form[0].equals("BANCA DIGITAL")?"/selected/digitalBank/":
                            form[0].equals("SERVICIOS TD")?"/selected/debitCard/":"";

            if(form[0].equals("FORMULARIO APERTURA")){
                idAccount = form[2];
                typeForm = form[0];
                categoryTypeForm = form[1];
                if(form[1].equals("CAJA-AHORRO")){

                    formsDtoReport = formsDtoReportService.generate(idClient, idAccount, typeForm, categoryTypeForm,getIsTutor(idClient,idAccount));
                }else{
                    formsDtoReport = formsDtoReportService.generate(idClient, idAccount, typeForm, categoryTypeForm,"NO");
                }

                pathSubReport1 = "template-report"+pathFormName;
                path1 = "template-report" + path;

                List<FormsDtoReport> formsDtoReportCollection = new ArrayList<>();
                formsDtoReportCollection.add(formsDtoReport);
                selectedReportDto.setFormsDtoReports(formsDtoReportCollection);

            }else if(form[0].equals("SERVICIOS TD")){
                idAccount = form[1];
                typeForm = form[0];
                formDebitCardDtoReport = formsDebitCardDtoReportService.generate(idClient, typeForm,"VARIOS",idAccount);
                formDebitCardDtoReport.setOfficeName(value3);

                pathSubReport2 = "template-report"+pathFormName;
                path2 = "template-report" + path;

                List<FormDebitCardDtoReport> formDebitCardDtoReportList = new ArrayList<>();
                formDebitCardDtoReportList.add(formDebitCardDtoReport);
                selectedReportDto.setFormDebitCardDtoReports(formDebitCardDtoReportList);

            }else if(form[0].equals("BANCA DIGITAL")){
                idAccount = form[1];
                typeForm = form[0];
                formDigitalBankDtoReport = formsDigitalBankDtoReportService.generate(idClient,typeForm,"VARIOS",idAccount);
                formDigitalBankDtoReport.setOfficeName(value3);

                pathSubReport3 = "template-report"+pathFormName;
                path3 = "template-report" + path;


                List<FormDigitalBankDtoReport> formDigitalBankDtoReportList = new ArrayList<>();
                formDigitalBankDtoReportList.add(formDigitalBankDtoReport);
                selectedReportDto.setFormDigitalBankDtoReports(formDigitalBankDtoReportList);
            }

            count++;
        }

        String check = path +"/check.png";
        String unchecked = path + "/unchecked.png";
        String pathLogo =  getClass().getResource("/template-report/img/logo.png").getPath();

        InputStream stream = getClass().getResourceAsStream("/template-report/selected/mainReport.jrxml");


        List<SelectedReportDto> selectedReportDtos = new ArrayList<>();
        selectedReportDtos.add(selectedReportDto);

        Map<String,Object> params = new WeakHashMap<>();
        params.put("logo",pathLogo);
        if(!pathSubReport1.equals("")) {
            params.put("path_subreport1", pathSubReport1);
            params.put("path1",path1);
        }
        if(!pathSubReport2.equals("")) {
            params.put("path_subreport2", pathSubReport2);
            params.put("path2",path2);
        }
        if(!pathSubReport3.equals("")) {
            params.put("path_subreport3", pathSubReport3);
            params.put("path3",path3);
        }
        params.put("check",check);
        params.put("unchecked",unchecked);

        byte[] b = PrinterReportJasper.imprimirComoPdf(stream,selectedReportDtos,params);
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
    private int getYears(GbageDto gbageDto) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = gbageDto.getGbagefnac().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        Period period = Period.between(currentDate, birthDate);
        return Math.abs(period.getYears());
    }

    private String getIsTutor(Integer idClient, String account){
        List<GbageDto> gbageDtoList = gbageDtoService.findGbageCamcaByCage(idClient);
        GbageDto gbageDto = gbageDtoList.stream()
                .filter(g -> g.getAccountCode().equals(account))
                .findFirst().get();
        Integer year = getYears(gbageDto);
        if(year<18){
            return "NO";
        }else  if(gbageDto.getSecundaryCage().equals(gbageDto.getGbagecage())) {
            return "NO";
        }
        return "SI";
    }

}
