package ru.tinkoff.yould.age;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AgeClient {

    private final SecureRandom random = new SecureRandom();

    public int getAge(String name) {
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка");
        }
        return random.nextInt(150) - 30;
    }
}
