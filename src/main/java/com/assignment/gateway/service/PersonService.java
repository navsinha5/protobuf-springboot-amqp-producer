package com.assignment.gateway.service;

import com.assignment.gateway.model.FileType;
import com.assignment.gateway.model.Person;
import com.assignment.gateway.protobuf.PersonProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    AmqpService amqpService;

    public void addPerson(FileType fileType, Person person){
        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        byte[] protoBytes = personBuilder
                .setName(person.getName())
                .setDob(person.getDob())
                .setSalary(person.getSalary())
                .setAge(person.getAge())
                .build().toByteArray();
        amqpService.sendToQueue(fileType, protoBytes);
    }
}
