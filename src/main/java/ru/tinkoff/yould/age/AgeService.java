package ru.tinkoff.yould.age;

import org.springframework.stereotype.Service;

@Service
public class AgeService {

    private final AgeClient ageClient;

    public AgeService(AgeClient ageClient) {
        this.ageClient = ageClient;
    }

    public boolean isYoung(String name) {
        int age = ageClient.getAge(name);
        System.out.printf("Age service return %d%n", age);
        if (age < 0) {
            throw new IllegalArgumentException("Возраст должен быть >= 0");
        }
        return age <= 35;
    }
}
