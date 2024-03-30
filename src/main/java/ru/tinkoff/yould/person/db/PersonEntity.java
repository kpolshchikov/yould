package ru.tinkoff.yould.person.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity(name = "person")
public class PersonEntity {
    @Id
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "is_young")
    private boolean isYoung;

    public Long getId() {
        return id;
    }

    public PersonEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PersonEntity setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isYoung() {
        return isYoung;
    }

    public PersonEntity setYoung(boolean young) {
        isYoung = young;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonEntity that = (PersonEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isYoung=" + isYoung +
                '}';
    }
}
