package com.assignment.gateway.controller;

import com.assignment.gateway.model.FileType;
import com.assignment.gateway.model.Person;
import com.assignment.gateway.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@RestController
@RequestMapping("/gw")
public class GatewayController {

    @Autowired
    PersonService personService;

    @PostMapping("/person")
    public void addPerson(@RequestHeader("X-File-Type") FileType fileType,
                          @Valid @RequestBody Person person){
        personService.addPerson(fileType, person);
    }
}
