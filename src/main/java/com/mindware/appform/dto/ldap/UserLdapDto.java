package com.mindware.appform.dto.ldap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLdapDto {

    private String departament;

    private String division;

    private String title;

    private String memberOf;

    private String cn;

    private String sn;

    private String email;

    private String employeeNumber;
}
