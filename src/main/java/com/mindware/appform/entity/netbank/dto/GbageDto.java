package com.mindware.appform.entity.netbank.dto;

import lombok.Data;

import java.util.Date;

@Data
public class GbageDto {

    private Integer gbagecage;

    private String gbagenomb;

    private String gbagendid;

    private Date gbagefnac;

    private Date gbagefreg;

    private Date openingDate;

    private String accountName;

    private String accountCode;

    private String currency;

    private Integer secundaryCage; //Codigo del tutor en caso de menores o codigo cliente manejo conjunto

    private String typeAccount;

    private String civilStatus;

    private String addressHome1;

    private String addressHome2;

    private Integer typeSavingBox;
}
