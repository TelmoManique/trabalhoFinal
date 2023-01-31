package com.telmomanique.trabalhofinal.TaskManager.controllers;

import com.telmomanique.trabalhofinal.TaskManager.models.Task;
import com.telmomanique.trabalhofinal.TaskManager.proxy.TikaProxy;
import com.telmomanique.trabalhofinal.TaskManager.services.TaskService;
import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.xml.sax.SAXException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


@RestController
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    TikaProxy tikaProxy;

    //URI:  POST:   "/createTask"
    //          200 OK
    //          409 Conflict
    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTask( @RequestBody Task task ){
        if(taskService.serviceHashExist( task.getHash()) )
            throw new ResponseStatusException( HttpStatus.CONFLICT );
        
        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        task.setInt_date( time );
        task.setStatus("Created");
        Optional<Task> t = taskService.serviceCreateTask(task);
        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @RequestMapping(value = "/createTaskText", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTaskText(@RequestBody Task task){
        MessageDigest digest;
        ResponseEntity<Optional<Task>> taskRes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] result = digest.digest(task.getDocument().getBytes());
        StringBuilder hexString = new StringBuilder();
        for(int i = 0; i< result.length; i++){
            String hex = Integer.toHexString(0xff & result[i]);
            if(hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }
        task.setHash(hexString.toString());
        task.setDuration( Duration.ZERO);
        task = createTask(task).getBody().get();

        task.setLanguage(tikaProxy.getLanguage(task.getDocument()));
        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        task.setEnd_date( time );
        Duration duration = Duration.between(task.getEnd_date().toInstant(), task.getInt_date().toInstant());
        task.setDuration( duration );
        task.setStatus("Finished");

        task = updateTask(task).getBody();
        return taskRes;
    }

    @RequestMapping(value = "/createTaskStream", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTaskStream(@RequestBody Task task, @RequestParam("file") MultipartFile multiFile){
        MessageDigest digest;
        ResponseEntity<Optional<Task>> taskRes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        String text = null;
        try {
            BodyContentHandler handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();

            Parser parser = new AutoDetectParser();
            parser.parse(new ByteArrayInputStream(multiFile.getBytes()), handler, metadata, context);
            text = handler.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException(e);
        }

        task = createTask(task).getBody().get();

        task.setLanguage( tikaProxy.getLanguage(text) );
        Calendar cal = Calendar.getInstance();
        Timestamp time = new Timestamp(cal.getTimeInMillis());
        task.setEnd_date( time );
        Duration duration = Duration.between(task.getEnd_date().toInstant(), task.getInt_date().toInstant());
        task.setDuration( duration );
        task.setStatus("Finished");

        task = updateTask(task).getBody();
        return new ResponseEntity<Optional<Task>> (Optional.of(task), HttpStatus.OK);
    }

    //URI:  GET:   "/getTask/{id}"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getTask/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Integer id){
        Optional<Task> task = taskService.serviceGetTask(id);
        if(!task.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(task.get(), HttpStatus.OK);
    }
    //URI:  GET:   "/getTask/cliente/{id}"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getTaskByCliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable("id") Integer id){
        List<Task> taskList = taskService.serviceGetTaskByUser(id);
        if(taskList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }
    //URI:  GET:   "/getAllTasks"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getAllTasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTask(){
        List<Task> taskList = taskService.serviceGetAllTasks();
        if(taskList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }
    //URI:  PUT:   "/updateTask"
    //          200 OK
    //          404 Doesn't Exist
    //          409 Conflict
    @RequestMapping( value = "/updateTask", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask( @RequestBody Task tempTask ){
        Optional<Task> opTask = taskService.serviceGetTask(tempTask.getId());
        if(opTask.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if(!opTask.get().getHash().contentEquals(opTask.get().getHash()) && opTask.get().getId() != tempTask.getId())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        Task task = new Task();
        task = opTask.get();
        if(!tempTask.getLanguage().isEmpty() || !tempTask.getLanguage().isBlank())
            task.setLanguage(tempTask.getLanguage());
        if(!tempTask.getStatus().isEmpty() || !tempTask.getStatus().isBlank())
            task.setStatus(tempTask.getStatus());

        task.setEnd_date(tempTask.getEnd_date());

        opTask = taskService.serviceUpdateTask(task);
        if(!opTask.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(opTask.get(), HttpStatus.OK);
    }
    
}
