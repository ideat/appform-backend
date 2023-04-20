package com.mindware.appform.controller;


import com.mindware.appform.service.netabank.PfmdpGbconDtoService;
import com.mindware.appform.util.WordReplaceTextProofReceiptDpf;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@RestController
@RequestMapping(value = "/rest", produces = {"application/json"})
public class ProofReceiptDpfController {

    @Value("${path_template}")
    private String path;

    @Autowired
    private PfmdpGbconDtoService pfmdpGbconDtoService;

    @Autowired
    private WordReplaceTextProofReceiptDpf wordReplaceTextProofReceiptDpf;
    private String pfmdpndep;

    @GetMapping(value = "/v1/proofreceiptdpf")
    public @ResponseBody byte[] getFileProofReceipt(@RequestHeader Map<String,String> headers) throws Exception {
        headers.forEach((key,value) -> {
            if(key.equals("pfmdpndep")) pfmdpndep = value;
        });

        String pathGenerated = wordReplaceTextProofReceiptDpf.generateProofReceipt(pfmdpndep);

        Path path = Paths.get(pathGenerated);
        byte[] bFile = Files.readAllBytes(path);
        InputStream is = new ByteArrayInputStream(bFile);
        return IOUtils.toByteArray(is);
    }

}
