package com.assignment.gateway.service;

import com.assignment.gateway.model.*;
import com.assignment.gateway.protobuf.PersonProtos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@Service
public class PersonService {

    @Autowired
    AmqpService amqpService;

    @Autowired
    RestTemplate restTemplate;

    @Value("${gw.service.encryption.key}")
    String key;

    @Value("${storage.service.get.person.uri}")
    String storageServiceUri;

    public Map<String, String> addPerson(FileType fileType, PersonAddReq person) {
        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        String uuid = UUID.randomUUID().toString();
        byte[] protoBytes = personBuilder
                .setId(uuid)
                .setName(person.getName())
                .setDob(person.getDob())
                .setSalary(person.getSalary())
                .setAge(person.getAge())
                .build().toByteArray();
        try {
            amqpService.sendToQueue(fileType, encrypt(protoBytes));
        } catch (Exception e) {
            System.out.println("error while encrypting proto bytes: " + e.getMessage());
            throw new GatewayException();
        }
        return Collections.singletonMap("id", uuid);
    }

    public void updatePerson(FileType fileType, PersonUpdateReq person, String id){
        PersonProtos.Person.Builder personBuilder = PersonProtos.Person.newBuilder();
        byte[] protoBytes = personBuilder
                .setId(id)
                .setName(person.getName() == null ? personBuilder.getName() : person.getName())
                .setDob(person.getDob() == null ? personBuilder.getDob() : person.getDob())
                .setSalary(person.getSalary() == null ? personBuilder.getSalary() : person.getSalary())
                .setAge(person.getAge() == null ? personBuilder.getAge() : person.getAge())
                .build().toByteArray();
        try {
            amqpService.sendToQueue(fileType, encrypt(protoBytes));
        } catch (Exception e) {
            System.out.println("error while encrypting proto bytes: " + e.getMessage());
            throw new GatewayException();
        }
    }

    public Person getPerson(String id){
        try{
            return restTemplate.getForObject(storageServiceUri + id, Person.class);
        }catch (RestClientException ex){
            throw new GatewayException();
        }
    }

    public byte[] encrypt(byte[] raw) throws Exception {
        byte[] keyBytes = DatatypeConverter.parseBase64Binary(key);
        Key finalKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, finalKey);
        return cipher.doFinal(raw);
    }
}
