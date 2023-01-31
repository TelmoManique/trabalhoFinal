package com.telmomanique.trabalhofinal.Tika.controller;

import org.apache.tika.io.TikaInputStream;
import org.apache.tika.language.LanguageIdentifier;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

@RestController
public class Controller {

    @RequestMapping(value = "/getLanguageText/{text}" , method = RequestMethod.POST)
    public String getLanguage(@PathVariable("text") String text) {
        LanguageIdentifier identifier = new LanguageIdentifier(text);
        return identifier.getLanguage();
    }
}
