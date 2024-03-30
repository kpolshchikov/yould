package ru.tinkoff.yould.person.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tinkoff.yould.person.db.PersonEntity;
import ru.tinkoff.yould.person.model.Person;
import ru.tinkoff.yould.person.service.PersonService;

import java.util.List;

@Controller
@RequestMapping("/api/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/young")
    public List<PersonEntity> getYoungs() {
        return personService.getYoungPersons();
    }

    @PostMapping
    public PersonEntity save(@RequestBody Person person) {
        return personService.save(person);
    }
}
