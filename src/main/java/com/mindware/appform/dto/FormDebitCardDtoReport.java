package com.mindware.appform.dto;

import com.mindware.appform.entity.Service;
import lombok.Data;

import java.util.List;

@Data
public class FormDebitCardDtoReport {

    private String createDate;

    private String deliverDate;

    private String hourCreate;

    private String officeName;

    private String userCreate;

    private String fullNameClient;

    private String homeAddress;

    private String idCard;

    private String cellphone;

    private String homePhone;

    private List<Service> listService;

    private String reason;

    private String accounts;

    private String accountSavingBank;

}
