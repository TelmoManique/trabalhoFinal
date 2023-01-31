package com.telmomanique.trabalhofinal.TheLanguageFinder.proxy;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Task;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@FeignClient(value= "task-manager"/*, url= "http://task-manager/"*/)
public interface TaskProxy {

    @RequestMapping(value = "/createTask", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTask(@RequestBody Task task);

    @RequestMapping(value = "/createTaskText", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTaskText(@RequestBody Task task);

    @RequestMapping(value = "/createTaskURL", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTaskURL(@RequestBody Task task);

    @RequestMapping(value = "/createTaskStream", method = RequestMethod.POST)
    public ResponseEntity<Optional<Task>> createTaskStream(@RequestBody Task task, @RequestParam("file") MultipartFile multiFile);

    @RequestMapping( value = "/getTask/{id}", method = RequestMethod.GET)
    public ResponseEntity<Task> getTaskById(@PathVariable("id") Integer id);

    @RequestMapping( value = "/getTaskByCliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable("id") Integer id);

    @RequestMapping( value = "/getAllTasks", method = RequestMethod.GET)
    public ResponseEntity<List<Task>> getAllTask();

    @RequestMapping( value = "/updateTask", method = RequestMethod.PUT)
    public ResponseEntity<Task> updateTask( @RequestBody Task tempTask );
}
