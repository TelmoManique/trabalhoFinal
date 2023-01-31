package com.telmomanique.trabalhofinal.TheLanguageFinder.controller;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Cliente;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.ClienteProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Optional;

@RestController
public class WebController {
    @Autowired
    ClienteProxy clienteProxy;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ClienteController clienteController;
    @Autowired
    AdminController adminController;

    //URI:  GET     "/"
    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        if(SecurityContextHolder.getContext().getAuthentication().isAuthenticated()){
            Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
            for(GrantedAuthority g :authorities){
                if(g.toString().compareTo("ROLE_CLIENTE") == 0)
                    return clienteController.index(modelAndView);
                if(g.toString().compareTo("ROLE_ADMIN") == 0)
                    return adminController.index(modelAndView);
            }
        }
        modelAndView.setViewName("index");
        return modelAndView;
    }

    //URI   GET: /public/signupForm
    @RequestMapping(value="/public/signupForm", method = RequestMethod.GET)
    public ModelAndView signUpForm(ModelAndView modelAndView){
        modelAndView.setViewName("signup");
        return modelAndView;
    }
    //URI   POST: /public/signup
    //      200 OK
    //      407 CONFLICT
    @RequestMapping(value= "/public/signup", method = RequestMethod.GET)
    public ModelAndView signUp(ModelAndView modelAndView
            , @RequestParam("username") String username
            , @RequestParam("password") String password
            , @RequestParam("password_confirmation") String password_c){
        if(password.compareTo(password_c) != 0) //CHECK PASSWORD
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        if(clienteProxy.getClienteByName(username).getStatusCode() == HttpStatus.OK ) //CHECK EXIST
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        Cliente cliente = new Cliente();
        cliente.setUsername(username);
        cliente.setPassword(bCryptPasswordEncoder.encode(password));
        //cliente.setRole("ROLE_UNCERTIFIED");
        cliente.setRole("ROLE_CLIENTE");
        cliente.setBlocked(false);
        cliente.setBanned(false);

        ResponseEntity<Optional<Cliente>> response = clienteProxy.createCliente(cliente);
        if(response.getStatusCode() != HttpStatus.OK)
            throw new ResponseStatusException(response.getStatusCode());

        return index(modelAndView);
    }

    //URI   POST: /public/signup
    @GetMapping("/public/taskForm")
    public ModelAndView taskForm(ModelAndView modelAndView){
        modelAndView.setViewName("taskForm");
        return modelAndView;
    }

    @GetMapping("/error")
    public ModelAndView error(ModelAndView modelAndView){
        modelAndView.setViewName("error");
        return modelAndView;
    }


}
