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

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
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

    //URI   POST: /taskForm
    @GetMapping("/taskForm")
    public ModelAndView taskForm(ModelAndView modelAndView){
        modelAndView.setViewName("clienteTaskForm");
        return modelAndView;
    }

    @RequestMapping(value = "/createTask/text", method = RequestMethod.GET)
    public void createTaskText (@RequestParam("text") String texto){
        Task task = new Task();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setCliente_id(clienteProxy.getClienteByName(username).getBody());
        task.setDocument(texto);
        task.setType("TEXT");
        taskProxy.createTaskText(task);
    }

    @RequestMapping(value="/createTask/URL" , method = RequestMethod.GET)
    public void createTaskURL (@RequestParam("url") String url){
        if(url.isEmpty()|| url.isBlank())
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        Task task = new Task();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setCliente_id(clienteProxy.getClienteByName(username).getBody());
        task.setDocument(url);
        task.setType("URL");
        taskProxy.createTaskURL(task);
    }

    @RequestMapping(value ="/createTask/file", method = RequestMethod.POST)
    public void  createTaskFile (@RequestParam("file") MultipartFile multiFile){
        if(multiFile == null)
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        Task task = new Task();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        task.setCliente_id(clienteProxy.getClienteByName(username).getBody());
        task.setDocument(multiFile.getName());
        task.setType(multiFile.getContentType());
        taskProxy.createTaskStream(task, multiFile);
    }
}
