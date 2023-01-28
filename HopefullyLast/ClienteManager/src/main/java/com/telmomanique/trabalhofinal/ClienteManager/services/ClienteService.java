package com.telmomanique.trabalhofinal.ClienteManager.services;

import com.telmomanique.trabalhofinal.ClienteManager.models.Cliente;
import com.telmomanique.trabalhofinal.ClienteManager.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService implements ApplicationRunner {
    @Autowired
    ClienteRepository clienteRepository; //TODO CHANGE FOR THE REAL ONE

    public Optional<Cliente> serviceGetCliente(Integer id){
        return clienteRepository.getClienteByID(id);
    }
    public Optional<Cliente> serviceGetCliente(String username){
        return clienteRepository.getClienteByUsername(username);
    }
    public List<Cliente> serviceGetAllClientes(){
        return clienteRepository.getAllClientes();
    }
    public Optional<Cliente> serviceCreateCliente (Cliente cliente){
        return Optional.of(clienteRepository.save(cliente));
    }
    public Optional<Cliente> serviceUpdateCliente(Cliente cliente){
        return  Optional.of(clienteRepository.save(cliente));
    }
    public Optional<Cliente> serviceBanCliente(Integer id) {
        Optional<Cliente> c = clienteRepository.getClienteByID(id);
        if(c.isEmpty())
            return Optional.empty();

        c.get().setBanned(!c.get().isBanned());
        return  Optional.of(clienteRepository.save(c.get()));
    }
    public Optional<Cliente> serviceBlockCliente(Integer id) {
        Optional<Cliente> c = clienteRepository.getClienteByID(id);
        if(c.isEmpty())
            return Optional.empty();

        c.get().setBlocked(!c.get().isBlocked());
        return  Optional.of(clienteRepository.save(c.get()));
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //TODO REMOVE
    }
}
