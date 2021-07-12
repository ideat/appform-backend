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
}
