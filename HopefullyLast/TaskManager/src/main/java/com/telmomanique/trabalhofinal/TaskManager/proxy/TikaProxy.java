package com.telmomanique.trabalhofinal.TaskManager.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value= "apache-tika"/*, url= "http://task-manager/"*/)
public interface TikaProxy {

    @RequestMapping(value = "/getLanguageText/{text}", method = RequestMethod.POST)
    public String getLanguage(@PathVariable("text") String text);
}
