package com.mindware.appform.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AccountServiceOperation {
    private String id;

    private Date createDate;

    private String account;

    private String services; //json parameter

    private String user;

    private String reasonOpening;

    private Double maxDailyLimit;

    private Double maxLimitExtension;
}
