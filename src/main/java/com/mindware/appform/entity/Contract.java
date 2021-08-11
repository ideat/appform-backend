package com.mindware.appform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Contract {

    private String id;

    private Integer codeClient;

    private String typeAccount;

    private String account;

    private Date dateContract;

    private String fileName;

    private String pathContract;

    private String pathTemplate;

    private String numberPower;

    private String legalRepresentative;

    private String idCardLegalRepresentative;

    private String notaryNumber;

    private String notaryName;


}
