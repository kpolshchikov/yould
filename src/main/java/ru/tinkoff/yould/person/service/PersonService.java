package ru.tinkoff.yould.person.service;

import org.springframework.stereotype.Service;
import ru.tinkoff.yould.age.AgeService;
import ru.tinkoff.yould.person.db.PersonEntity;
import ru.tinkoff.yould.person.db.PersonRepository;
import ru.tinkoff.yould.person.model.Person;

import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final AgeService ageService;

    public PersonService(PersonRepository personRepository, AgeService ageService) {
        this.personRepository = personRepository;
        this.ageService = ageService;
    }

    public List<PersonEntity> getYoungPersons() {
        List<PersonEntity> persons = personRepository.findAll();
        return persons.stream().filter(PersonEntity::isYoung).toList();
    }

    public PersonEntity save(Person person) {
        boolean isYoung = ageService.isYoung(person.name());
        return personRepository.save(new PersonEntity()
                .setName(person.name())
                .setYoung(isYoung)
        );
    }
}
