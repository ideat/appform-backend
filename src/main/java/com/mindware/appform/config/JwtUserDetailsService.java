package com.mindware.appform.config;

import com.mindware.appform.entity.Users;
import com.mindware.appform.service.RolService;
import com.mindware.appform.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class JwtUserDetailsService  implements UserDetailsService {
    @Autowired
    UsersService usersService;

    @Autowired
    RolService rolService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Users> users = usersService.findByLogin(login);
        List<GrantedAuthority> roles = new ArrayList<>();
        if(users.isPresent()) {
            return this.usersBuilder(login, users.get().getPassword(), users.get().getRolName());
        }else{
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    private User usersBuilder(String login, String password, String rol){
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+rol));


        return new User(login,password,enabled,accountNonExpired,credentialsNonExpired,accountNonLocked,authorities);

    }
}
