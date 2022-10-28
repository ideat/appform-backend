package com.mindware.appform.dto.ldap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataDto {

    private String cn;

    private String department;

    private String sn;

    private String memberOf;
}
