package com.telmomanique.trabalhofinal.TheLanguageFinder.controller;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Cliente;
import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Task;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.ClienteProxy;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.TaskProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;


import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

     @Autowired
     ClienteProxy clienteProxy;
     @Autowired
     TaskProxy taskProxy;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("adminIndex");
        return modelAndView;
    }
    @RequestMapping( value= "/allClientes", method = RequestMethod.GET)
    public ModelAndView getAllProfiles(ModelAndView modelAndView){

        ResponseEntity<List<Cliente>> response = clienteProxy.getAllClientes();
        List<Cliente> clienteList = response.getBody();
        modelAndView.setViewName("allProfiles");
        modelAndView.addObject("cliente_list" , clienteList);
        return modelAndView;
    }

    @RequestMapping( value= "/allTasks", method = RequestMethod.GET)
    public ModelAndView getAllTasks(ModelAndView modelAndView){
        ResponseEntity<List<Task>> response = taskProxy.getAllTask();
        List<Task> taskList = response.getBody();

        modelAndView.setViewName("allTasks");
        modelAndView.addObject("task_list" , taskList);
        return modelAndView;
    }

    @RequestMapping(value = "/banCliente/{id}" , method = RequestMethod.POST)
    public void banClient(ModelAndView modelAndView, @PathVariable("id") Integer id){
        ResponseEntity<Cliente> response= clienteProxy.getClienteById(id);
        if(response.getStatusCode() != HttpStatus.OK)
            throw new ResponseStatusException(response.getStatusCode());

        getAllProfiles(modelAndView);
    }

    @RequestMapping(value = "/blockCliente/{id}" , method = RequestMethod.POST)
    public void blockClient(ModelAndView modelAndView, @PathVariable("id") Integer id){
        ResponseEntity<Cliente> response= clienteProxy.blockCliente(id);
        if(response.getStatusCode() != HttpStatus.OK)
            throw new ResponseStatusException(response.getStatusCode());

        getAllProfiles(modelAndView);
    }
}
