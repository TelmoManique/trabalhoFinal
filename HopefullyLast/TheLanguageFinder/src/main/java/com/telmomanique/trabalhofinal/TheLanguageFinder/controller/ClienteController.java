package com.telmomanique.trabalhofinal.TheLanguageFinder.controller;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Cliente;
import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Task;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.ClienteProxy;
import com.telmomanique.trabalhofinal.TheLanguageFinder.proxy.TaskProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteProxy clienteProxy;
    @Autowired
    TaskProxy taskProxy;

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView){
        modelAndView.setViewName("clienteIndex");
        return modelAndView;
    }
    @GetMapping("/profile")
    public ModelAndView profile(ModelAndView modelAndView){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Cliente cliente = clienteProxy.getClienteByName(username).getBody();
        ResponseEntity<List<Task>> response = taskProxy.getTaskByUserId(cliente.getId());
        if(response.getBody() != null) {
            List<Task> taskList = response.getBody();
            modelAndView.addObject("task_list", taskList);
        }
        modelAndView.setViewName("profile");
        modelAndView.addObject("cliente" , cliente);
        return modelAndView;
    }

    @RequestMapping(value="/createTask/URL" , method = RequestMethod.POST)
    public void createTaskURL (@RequestParam String url){
        if(url.isEmpty()|| url.isBlank())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/createTask/file")
    public void  createTaskFile (@RequestParam("file") MultipartFile multiFile){
        if(multiFile == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

    }
}
