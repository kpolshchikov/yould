package ru.tinkoff.yould.age;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeServiceTest {
    @Mock
    private AgeClient ageClient;
    @InjectMocks
    private AgeService sut;

    @Test
    void returnFalseWhenAgeClientReturnMore35() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(37);
        // act
        boolean actual = sut.isYoung("Василий");
        // assert
        assertThat(actual)
                .isFalse();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 13, 35})
    void returnTrueWhenAgeClientReturnMoreOrEquals0AndLessOrEquals35(int age) {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(age);
        // act
        boolean actual = sut.isYoung("Василий");
        // assert
        assertThat(actual)
                .isTrue();
    }

    @Test
    void throwErrorWhenAgeClientReturnLess0() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenReturn(-1);
        // act
        Assertions.assertThatThrownBy(() -> sut.isYoung("Василий"))
                // assert
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Возраст должен быть >= 0");
    }

    @Test
    void throwErrorWhenAgeClientReturnError() {
        // arrange
        when(ageClient.getAge("Василий"))
                .thenThrow(new NoSuchElementException("Не нашел Васю"));
        // act
        Assertions.assertThatThrownBy(() -> sut.isYoung("Василий"))
                // assert
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage("Не нашел Васю");
    }
}
