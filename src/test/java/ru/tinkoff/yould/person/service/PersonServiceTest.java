package ru.tinkoff.yould.person.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.tinkoff.yould.age.AgeService;
import ru.tinkoff.yould.person.db.PersonEntity;
import ru.tinkoff.yould.person.db.PersonRepository;
import ru.tinkoff.yould.person.model.Person;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @Mock
    private AgeService ageService;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private PersonService sut;
    @Captor
    ArgumentCaptor<PersonEntity> personEntityArgumentCaptor;

    @Test
    void saveYoungPersonWhenAgeServiceReturnTrue() {
        // arrange
        when(ageService.isYoung("Василий"))
                .thenReturn(true);
        // act
        sut.save(new Person("Василий"));
        // assert
        verify(ageService, times(1))
                .isYoung("Василий");
        verify(personRepository, times(1))
                .save(personEntityArgumentCaptor.capture());
        assertThat(personEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new PersonEntity()
                        .setName("Василий")
                        .setYoung(true));
    }

    @Test
    void saveOldPersonWhenAgeServiceReturnFalse() {
        // arrange
        when(ageService.isYoung("Василий"))
                .thenReturn(false);
        // act
        sut.save(new Person("Василий"));
        // assert
        verify(ageService, times(1))
                .isYoung("Василий");
        verify(personRepository, times(1))
                .save(personEntityArgumentCaptor.capture());
        assertThat(personEntityArgumentCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new PersonEntity()
                        .setName("Василий")
                        .setYoung(false));
    }

    @Test
    void returnErrorWhenAgeServiceReturnError() {
        // arrange
        when(ageService.isYoung("Василий"))
                .thenThrow(new NoSuchElementException("Не нашел Васю"));
        // act
        assertThatThrownBy(() -> sut.save(new Person("Василий")))
                // assert
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Не нашел Васю");
        verify(ageService, times(1))
                .isYoung("Василий");
        verifyNoInteractions(personRepository);
    }
}
