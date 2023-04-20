package com.mindware.appform.util;

import com.mindware.appform.entity.netbank.dto.PfmdpGbconDto;
import com.mindware.appform.service.netabank.PfmdpGbconDtoService;
import com.xandryex.WordReplacer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class WordReplaceTextProofReceiptDpf {

    @Value("${path_template}")
    private String pathTemplate;

    @Value("${path_word}")
    private String pathWord;

    @Value("${path_pdf}")
    private String pathPdf;

    @Autowired
    private PfmdpGbconDtoService pfmdpGbconDtoService;

    @Autowired
    private ConvertWordToPdf convertWordToPdf;


    public String generateProofReceipt(String pfmdpndep) throws Exception {

        PfmdpGbconDto pfmdpGbconDto = pfmdpGbconDtoService.findPfmdpGbcon(pfmdpndep);

        Map<String,String> mapProofReceiptVariable = new HashMap<>();
        mapProofReceiptVariable.put("@numero_dpf",pfmdpGbconDto.getPfmdpndep().trim());
        mapProofReceiptVariable.put("@moneda_dpf",pfmdpGbconDto.getPfmdpcmon().trim());
        mapProofReceiptVariable.put("@monto_dpf", String.format("%,.2f",pfmdpGbconDto.getPfmdpcapi()));
        mapProofReceiptVariable.put("@plazo_dpf",pfmdpGbconDto.getPfmdpplzo().toString());

        String nameWord = "CONSTANCIA-RECEPCION-DPF-" + pfmdpndep+".docx";
        String namePdf = "CONSTANCIA-RECEPCION-DPF-" + pfmdpndep+".pdf";

        File templatefile = new File(pathTemplate + "CONSTANCIA-RECEPCION-DPF.docx");

        WordReplacer wordReplacer = new WordReplacer(templatefile);

        mapProofReceiptVariable.forEach((k,v) -> {
            wordReplacer.replaceWordsInText(k,v);
        });

        wordReplacer.saveAndGetModdedFile(pathWord+nameWord);
        convertWordToPdf.convert(pathWord+nameWord, pathPdf+namePdf);

        return pathPdf + namePdf;

    }

}
