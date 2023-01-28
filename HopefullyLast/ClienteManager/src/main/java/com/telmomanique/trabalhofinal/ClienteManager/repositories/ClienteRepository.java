package com.telmomanique.trabalhofinal.ClienteManager.repositories;

import com.telmomanique.trabalhofinal.ClienteManager.models.Cliente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer> {


    public Cliente save(Cliente cliente);

    @Query("SELECT c FROM Cliente c ")
    public List<Cliente> getAllClientes();

    @Query("SELECT c FROM Cliente c WHERE c.id = ?1")
    public Optional<Cliente> getClienteByID(Integer id);

    @Query("SELECT c FROM Cliente c WHERE c.username = ?1")
    public Optional<Cliente> getClienteByUsername(String username);




}
