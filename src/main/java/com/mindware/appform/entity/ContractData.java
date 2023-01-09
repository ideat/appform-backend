package com.mindware.appform.entity;

import lombok.Data;

@Data
public class ContractData {

    public Integer codeClient;

    public String account;

    public String typeForm;

    public String categoryTypeForm;

    public String fullNameClient;

    public String fullNamePartner;

    public String fullNameTutor;

    public String fullNameChild;

    public String idCardClient;

    public String idCardPartner;

    public String idCardTutor;

    public String idCardChild;

    public String addressClientHome;

    public String productName;

    public String currency;

    public String currencyName;

    public String rate;

    private String nameClientVinculation;

    private String documentClientVinculation;
}
