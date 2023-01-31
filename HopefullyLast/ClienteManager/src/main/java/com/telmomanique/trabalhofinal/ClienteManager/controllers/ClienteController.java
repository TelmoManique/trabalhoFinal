package com.telmomanique.trabalhofinal.ClienteManager.controllers;

import com.telmomanique.trabalhofinal.ClienteManager.models.Cliente;
import com.telmomanique.trabalhofinal.ClienteManager.services.ClienteService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    //URI:  POST:   "/createCliente"
    //          200 OK
    //          409 Conflict
    @RequestMapping(value = "/createCliente", method = RequestMethod.POST)
    public ResponseEntity<Optional<Cliente>> createCliente(@RequestBody Cliente cliente){
        if(clienteService.serviceGetCliente(cliente.getUsername()).isPresent())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        Optional<Cliente> cli = clienteService.serviceCreateCliente(cliente);
        return new ResponseEntity<>(cli, HttpStatus.OK);
    }

    //URI:  GET:   "/getCliente/{id}"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getCliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteService.serviceGetCliente(id);
        if(!cliente.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }

    //URI:  GET:   "/getCliente/name/{username}"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getCliente/name/{username}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> getClienteByName(@PathVariable("username") String username){
        Optional<Cliente> cliente = clienteService.serviceGetCliente(username);
        if(!cliente.isPresent())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }

    //URI:  GET:   "/getAllClientes"
    //          200 OK
    //          404 Doesn't Exist
    @RequestMapping( value = "/getAllClientes", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> getAllClientes(){
        List<Cliente> cliList = clienteService.serviceGetAllClientes();
        if(cliList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //TODO CORRIGIR APARECE 204

        return new ResponseEntity<>(cliList, HttpStatus.OK);
    }

    //URI:  PUT:   "/updateCliente"
    //          200 OK
    //          404 Doesn't Exist
    //          409 Conflict
    @RequestMapping( value = "/updateCliente", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> updateCliente( @RequestBody Cliente tempCli ){
        Optional<Cliente> opCli = clienteService.serviceGetCliente(tempCli.getId());
        if(opCli.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        if(opCli.get().getUsername().contentEquals(tempCli.getUsername()) && opCli.get().getId() != tempCli.getId())
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        //TODO Mudar para Service
        Cliente cliente = new Cliente();
        cliente = opCli.get();
        if(!tempCli.getUsername().isEmpty() || !tempCli.getUsername().isBlank())
            cliente.setUsername(tempCli.getUsername());
        if(!tempCli.getPassword().isEmpty() || !tempCli.getPassword().isBlank())
            cliente.setPassword(tempCli.getPassword());
        if(!tempCli.getRole().isEmpty() || !tempCli.getRole().isBlank())
            cliente.setRole(tempCli.getRole());

        opCli = clienteService.serviceUpdateCliente(cliente);
        if(!opCli.isPresent())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(opCli.get(), HttpStatus.OK);
    }

    //URI:  PUT:   "/banCliente/{id}"
    //          200 OK
    //          404 Doesn't Exist
    //          409 Conflict
    @RequestMapping( value = "/banCliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> banCliente(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteService.serviceGetCliente(id);
        if(cliente.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        cliente = clienteService.serviceBanCliente(id);
        if(cliente.isEmpty())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }

    //URI:  PUT:   "/banCliente/{id}"
    //          200 OK
    //          404 Doesn't Exist
    //          409 Conflict
    @RequestMapping( value = "/blockCliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> blockCliente(@PathVariable("id") Integer id){
        Optional<Cliente> cliente = clienteService.serviceGetCliente(id);
        if(cliente.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        cliente = clienteService.serviceBlockCliente(id);
        if(cliente.isEmpty())
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
    }

}
