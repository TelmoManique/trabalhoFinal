package com.telmomanique.trabalhofinal.TaskManager.services;

import com.telmomanique.trabalhofinal.TaskManager.models.Task;
import com.telmomanique.trabalhofinal.TaskManager.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public Optional<Task> serviceCreateTask(Task task) {
        task.setInt_date(new Timestamp(System.currentTimeMillis()));
        return Optional.of(taskRepository.save(task));
    }
    public List<Task> serviceGetAllTasks() {
        return taskRepository.getAllTasks();
    }
    public Optional<Task> serviceUpdateTask(Task task) {
        return Optional.of(taskRepository.save(task));
    }
    public Optional<Task> serviceGetTask(Integer id) {
        return taskRepository.getTaskById(id);
    }
    public boolean serviceHashExist(String hash) {
        if( taskRepository.getHashCount(hash).getId() >0 )
            return true;
        return false ;
    }
    public List<Task> serviceGetTaskByUser(Integer id) {
        return  taskRepository.getTaskByUserId(id);
    }
}
