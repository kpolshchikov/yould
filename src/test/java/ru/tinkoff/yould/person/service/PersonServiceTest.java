package ru.tinkoff.yould.person.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.yould.age.AgeClient;
import ru.tinkoff.yould.age.AgeService;
import ru.tinkoff.yould.person.db.PersonEntity;
import ru.tinkoff.yould.person.db.PersonRepository;
import ru.tinkoff.yould.person.model.Person;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    private AgeClient ageClient;
    private AgeService ageService;
    private PersonRepository personRepository;
    private PersonService sut;
    @Captor
    private ArgumentCaptor<PersonEntity> personEntityArgumentCaptor;

    public PersonServiceTest() {
        ageClient = Mockito.mock(AgeClient.class);
        ageService = new AgeService(ageClient);
        personRepository = Mockito.mock(PersonRepository.class);
        sut = new PersonService(personRepository, ageService);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 13, 35})
    void saveYoungPersonWhenAgeClientReturnAgeMoreOrEquals0AndLessOrEquals35(int age) {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(age);
        // act
        sut.save(new Person("Василий"));
        // assert
        verify(personRepository).save(personEntityArgumentCaptor.capture());
        Assertions.assertThat(personEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new PersonEntity()
                        .setName("Василий")
                        .setYoung(true));
    }

    @Test
    void saveOldPersonWhenAgeClientReturnAgeMore35() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(36);
        // act
        sut.save(new Person("Василий"));
        // assert
        verify(personRepository).save(personEntityArgumentCaptor.capture());
        Assertions.assertThat(personEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new PersonEntity()
                        .setName("Василий")
                        .setYoung(false));
    }

    @Test
    void throwErrorWhenAgeClientReturnAgeLess0() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(-1);
        // act
        Assertions.assertThatThrownBy(() -> sut.save(new Person("Василий")))
                // assert
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Возраст должен быть >= 0");
        verifyNoInteractions(personRepository);
    }

    @Test
    void throwErrorWhenAgeClientReturnError() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenThrow(new NoSuchElementException("Не нашел Васю"));
        // act
        Assertions.assertThatThrownBy(() -> sut.save(new Person("Василий")))
                // assert
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Не нашел Васю");
        verifyNoInteractions(personRepository);
    }
}
