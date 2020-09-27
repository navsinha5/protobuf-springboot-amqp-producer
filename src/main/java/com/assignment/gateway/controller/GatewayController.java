package com.assignment.gateway.controller;

import com.assignment.gateway.model.FileType;
import com.assignment.gateway.model.Person;
import com.assignment.gateway.model.PersonAddReq;
import com.assignment.gateway.model.PersonUpdateReq;
import com.assignment.gateway.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/gw")
public class GatewayController {

    @Autowired
    PersonService personService;

    @GetMapping("/person")
    public Person getPerson(@RequestParam("id") @NotBlank(message = "id is required") String id){
        return personService.getPerson(id);
    }

    @PostMapping("/person")
    public Map<String, String> addPerson(@RequestHeader("X-File-Type") FileType fileType,
                                         @Valid @RequestBody PersonAddReq person){
        return personService.addPerson(fileType, person);
    }

    @PatchMapping("/person")
    public void updatePerson(@RequestHeader("X-File-Type") FileType fileType,
                             @RequestParam("id") @NotBlank(message = "id can't be blank") String id,
                             @Valid @RequestBody(required = false) PersonUpdateReq person){
        personService.updatePerson(fileType, person, id);
    }
}
