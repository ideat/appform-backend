package com.mindware.appform.controller;

import com.mindware.appform.config.JwtRequest;
import com.mindware.appform.config.JwtResponse;
import com.mindware.appform.config.JwtTokenUtil;
import com.mindware.appform.entity.Users;
import com.mindware.appform.repository.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/ldap")
public class LdapAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UsersMapper usersMapper;

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

    private SearchResult result;

    @PostMapping("login")
    public ResponseEntity<?> createToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
//        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
//        authenticate(AD_USER,AD_PASS);

        if( !authenticateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword())){
            throw new Exception("Usuario o clave incorrecta");
        }
        //Get User by ad_user and replace user name
        Optional<Users> users = usersMapper.findByAdUser(authenticationRequest.getUsername());

        if(!users.isPresent()){
            throw new Exception("Usuario AD no tiene usuario AUTO-FORM asignado");
        }

        final UserDetails userDetails = userBuilder(users.get().getLogin(), authenticationRequest.getPassword(), "ROLE");
//        final UserDetails userDetails = userBuilder(authenticationRequest.getUsername(), authenticationRequest.getPassword(), "ROLE");

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    private User userBuilder(String login, String password, String rol){
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+rol));

        return new User(login,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);
    }

    public boolean authenticateUser(String user, String pass) {
        String returnedAtts[] = {"sn", "department", "cn"};
        String searchFilter = "(&(objectClass=user)(sAMAccountName=" + user + "))";

        // Create the search controls
        SearchControls searchCtls = new SearchControls();
        searchCtls.setReturningAttributes(returnedAtts);

        // Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, AD_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, user + "@" + AD_DOMAIN);
        env.put(Context.SECURITY_CREDENTIALS, pass);

        LdapContext ctxGC = null;

        try {
            ctxGC = new InitialLdapContext(env, null);
            // Search objects in GC using filters
            NamingEnumeration answer = ctxGC.search(AD_BASE, searchFilter, searchCtls);
            result = (SearchResult) answer.next();
        } catch (NamingException ex) {
            // ex.printStackTrace();
            return false;
        }
        return true;
    }
}
