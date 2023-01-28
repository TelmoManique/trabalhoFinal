package com.telmomanique.trabalhofinal.TheLanguageFinder.service;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Cliente;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.ClienteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ClienteDetailService implements UserDetailsService {
    @Autowired
    ClienteProxy clienteProxy;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if( !clienteProxy.getClienteByName(username).hasBody())
            throw new UsernameNotFoundException("This client doesn't exist");
        else{
            Cliente cliente = clienteProxy.getClienteByName(username).getBody();
            UserDetails userDetails = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
                    authList.add(new SimpleGrantedAuthority(cliente.getRole()));
                    return authList;
                }

                @Override
                public String getPassword() {
                    return cliente.getPassword();
                }

                @Override
                public String getUsername() {
                    return cliente.getUsername();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    if(cliente.isBanned() || cliente.isBlocked())
                        return false;
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
            return userDetails ;
        }
    }
}
