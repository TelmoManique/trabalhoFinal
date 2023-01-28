package com.telmomanique.trabalhofinal.TaskManager.controllers;

import com.telmomanique.trabalhofinal.TaskManager.models.Task;
import com.telmomanique.trabalhofinal.TaskManager.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    //URI:  POST:   "/createTask"
    //          200 OK
    //          409 Conflict
    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTask(@RequestBody Task task){
        if(taskService.serviceHashExist(task.getHash()))
            return new ResponseEntity<>(Optional.of(task), HttpStatus.CONFLICT);
        //TODO ADD USER ID CHECK

        Optional<Task> t = taskService.serviceCreateTask(task);
        //TODO LINK TO APACHE TIKA MICRO
        return new ResponseEntity<>(t, HttpStatus.OK);
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
