package com.telmomanique.trabalhofinal.TheLanguageFinder.proxy;

import com.telmomanique.trabalhofinal.TheLanguageFinder.models.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Optional;

@FeignClient(value= "cliente-manager"/*, url= "http://cliente-manager/"*/)
public interface ClienteProxy {

    @RequestMapping(value = "/createCliente", method = RequestMethod.POST)
    public ResponseEntity<Optional<Cliente>> createCliente(@RequestBody Cliente cliente);

    @RequestMapping( value = "/getCliente/{id}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> getClienteById(@PathVariable("id") Integer id);

    @RequestMapping( value = "/getCliente/name/{username}", method = RequestMethod.GET)
    public ResponseEntity<Cliente> getClienteByName(@PathVariable("username") String username);

    @RequestMapping( value = "/getAllClientes", method = RequestMethod.GET)
    public ResponseEntity<List<Cliente>> getAllClientes();

    @RequestMapping( value = "/updateCliente", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> updateCliente( @RequestBody Cliente tempCli );

    @RequestMapping( value = "/banCliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> banCliente(@PathVariable("id") Integer id);

    @RequestMapping( value = "/blockCliente/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Cliente> blockCliente(@PathVariable("id") Integer id);

}
