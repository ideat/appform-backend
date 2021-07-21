package com.mindware.appform.dto;

import com.mindware.appform.entity.Beneficiary;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FormsDtoReport {
    private String category;
    private String product;
    private String currency;
    private String office;
    private Date openingDate;
    private Integer codeClient;
    private String lastName;
    private String motherLastName;
    private String marriedLastName;
    private String names;
    private String typeIdCard;
    private String idCard;
    private Date expirateDate;
    private String gender;
    private String civilStatus;
    private Date birthDate;
    private String fullNameSpouse;
    private String activitySpouse;
    private String departament;
    private String province;
    private String homeAddress;
    private String profession;
    private Double incomeMounthly;
    private String zone;
    private String city;
    private String economicActivity1;
    private String economicActivity2;
    private String homeTelephone;
    private String cellphone;
    private String reasonOpening;
    private String typeVinculation;
    private String isFinalBeneficiary;
    private List<Beneficiary> beneficiaryList;
    private String country;
    private String nameClientVinculation;
    private String documentClientVinculation;
    private String label1;
    private String label2;


}
