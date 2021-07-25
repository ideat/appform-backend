package com.mindware.appform.dto;

import com.mindware.appform.entity.Service;
import lombok.Data;


import java.util.List;

@Data
public class FormDigitalBankDtoReport {


    private String createDate;

    private String officeName;

    private String userCreate;

    private String fullNameClient;

    private String homeAddress;

    private String idCard;

    private String cellphone;

    private String homePhone;

    private String email;

    private String userDigitalBank;

    private List<Service> listService;

    private List<Service> listOperation;

    private String reason;

    private String accounts;

    private Double maxDailyLimitBs;

    private Double maxExtendLimitBs;

    private Double maxDailyLimitSus;

    private Double maxExtendLimitSus;
}
