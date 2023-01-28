package com.telmomanique.trabalhofinal.TaskManager.repositories;

import com.telmomanique.trabalhofinal.TaskManager.models.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends CrudRepository<Task, Integer> {

    public Task save(Task task);

    @Query(value = "SELECT * FROM task", nativeQuery = true)
    public List<Task> getAllTasks();

    @Query(value = "SELECT * FROM task  WHERE id = ?1" , nativeQuery = true)
    public Optional<Task> getTaskById(Integer id);

    @Query(value = "SELECT * FROM task WHERE hash = ?1" , nativeQuery = true)
    public Task getHashCount(String hash);


    @Query(value = "SELECT * FROM task  WHERE cliente_id= ?1", nativeQuery = true)
    public List<Task> getTaskByUserId(Integer id);
}
