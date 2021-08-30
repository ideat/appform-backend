package com.mindware.appform.util;


import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConvertWordToPdf {

    public void convert(String inputFile, String outputFile) throws IOException {
        FileInputStream in=new FileInputStream(inputFile);
        XWPFDocument document=new XWPFDocument(in);
        File outFile=new File(outputFile);
        OutputStream out=new FileOutputStream(outFile);
        PdfOptions options= PdfOptions.create();
        PdfConverter.getInstance().convert(document,out,options);

    }

    public byte[] downloadPdf(String pathFile) throws IOException {
        File file = new File(pathFile);
        FileInputStream fis = new FileInputStream(file);
        byte [] data = new byte[(int)file.length()];
        fis.read(data);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        data = bos.toByteArray();
        return data;
    }

}
