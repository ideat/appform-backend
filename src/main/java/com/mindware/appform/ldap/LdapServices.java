package com.mindware.appform.ldap;


import com.mindware.appform.dto.ldap.UserLdapDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

@Slf4j
@Service
public class LdapServices {

    @Value("${ad.domain}")
    private String AD_DOMAIN;

    @Value("${ad.url}")
    private String AD_URL;

    @Value("${ad.base}")
    private String AD_BASE;

//    @Value("${ad.user}")
//    private String AD_USER;
//
//    @Value("${ad.pass}")
//    private String AD_PASS;

    public List<UserLdapDto> getUserByDivision(String division) throws IOException, NamingException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, AD_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, AD_USER + "@" + AD_DOMAIN);
//        env.put(Context.SECURITY_CREDENTIALS, AD_PASS);

        int pageSize = 2000;
        LdapContext ctxGC = null;

        ctxGC = new InitialLdapContext(env, new Control[] {
                new PagedResultsControl(pageSize, Control.CRITICAL) });
        List<UserLdapDto> userLdapDtoList = new ArrayList<>();
        try {

            String searchFilter2 = String.format("(&(objectClass=organizationalPerson)(division=%s))",division);
            String[] reqAtt = { "cn", "sn","division","title","department","memberOf","mail","employeeNumber" };
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(reqAtt);

            NamingEnumeration users = ctxGC.search(AD_BASE, searchFilter2, controls);

            SearchResult result = null;

//        Integer i=0;

            while (users.hasMore()) {
                UserLdapDto userLdapDto = new UserLdapDto();

                result = (SearchResult) users.next();
                Attributes attr = result.getAttributes();
//                String name = attr.get("cn").get(0).toString();
                //deleteUserFromGroup(name,"Administrators");


                userLdapDto.setCn(attr.get("cn") != null ? attr.get("cn").get().toString() : "");
                userLdapDto.setSn(attr.get("sn") != null ? attr.get("sn").get().toString() : "");
                userLdapDto.setDivision(attr.get("division") != null ? attr.get("division").get().toString() : "");
                userLdapDto.setDepartament(attr.get("department") != null ? attr.get("department").get().toString() : "");
                userLdapDto.setTitle(attr.get("title") != null ? attr.get("title").get().toString() : "");
                userLdapDto.setMemberOf(attr.get("memberOf") != null ? attr.get("memberOf").get().toString() : "");
                userLdapDto.setEmail(attr.get("mail") != null ? attr.get("mail").get().toString():"");
                userLdapDto.setEmployeeNumber(attr.get("employeeNumber") !=null ? attr.get("employeeNumber").get().toString():"");
//                i++;
//                System.out.println(i);
                userLdapDtoList.add(userLdapDto);

            }
        }catch(Exception ex){
          log.info("Error: LDAP getUserByDivision:  " + ex.getMessage());
        }


        return userLdapDtoList;
    }

    public List<UserLdapDto> getUserByCriterias(String criteria, String value) throws IOException, NamingException {

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, AD_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
//        env.put(Context.SECURITY_PRINCIPAL, AD_USER + "@" + AD_DOMAIN);
//        env.put(Context.SECURITY_CREDENTIALS, AD_PASS);

        int pageSize = 2000;
        LdapContext ctxGC = null;
        List<UserLdapDto> userLdapDtoList = new ArrayList<>();
        try {
            ctxGC = new InitialLdapContext(env, new Control[] {
                    new PagedResultsControl(pageSize, Control.CRITICAL) });
            String searchFilter2 = "";
            if(criteria.equals("division")){
                searchFilter2 = String.format("(&(objectClass=organizationalPerson)(division=%s))",value);
            }else  if(criteria.equals("title")){
                searchFilter2 = String.format("(&(objectClass=organizationalPerson)(title=%s))",value);
            }

            String[] reqAtt = { "cn", "sn","division","title","department","memberOf","mail" };
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            controls.setReturningAttributes(reqAtt);

            NamingEnumeration users = ctxGC.search(AD_BASE, searchFilter2, controls);

            SearchResult result = null;

//            Integer i=0;

            while (users.hasMore()) {
                UserLdapDto userLdapDto = new UserLdapDto();

                result = (SearchResult) users.next();

                Attributes attr = result.getAttributes();
//                String name = attr.get("cn").get(0).toString();
                //deleteUserFromGroup(name,"Administrators");

                userLdapDto.setCn(attr.get("cn") != null ? attr.get("cn").get().toString() : "");
                userLdapDto.setSn(attr.get("sn") != null ? attr.get("sn").get().toString() : "");
                userLdapDto.setDivision(attr.get("division") != null ? attr.get("division").get().toString() : "");
                userLdapDto.setDepartament(attr.get("department") != null ? attr.get("department").get().toString() : "");
                userLdapDto.setTitle(attr.get("title") != null ? attr.get("title").get().toString() : "");
                userLdapDto.setMemberOf(attr.get("memberOf") != null ? attr.get("memberOf").get().toString() : "");
                userLdapDto.setEmail(attr.get("mail") != null ? attr.get("mail").get().toString():"");
//                i++;
//                System.out.println(i);
                userLdapDtoList.add(userLdapDto);

            }
        }catch(Exception ex){
            log.info("LDAP getUserByCriterias: " + ex.getMessage());
        }



        return userLdapDtoList;
    }
}
