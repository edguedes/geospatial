package com.example.geospatial;

import com.example.geospatial.entites.Person;
import com.example.geospatial.repository.PersonStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonStorageTest {

    private PersonStorage personStorage;

    @BeforeEach
    void setUp() {
        personStorage = new PersonStorage();
    }

    @Test
    void shouldSavePersonWithAutoIncrementedId() {
        Person person = new Person();
        person.setName("Ana");
        person.setDtBirth(LocalDate.of(1990, 1, 1));

        Person saved = personStorage.save(person);

        assertEquals(1, saved.getId());
        assertEquals("Ana", saved.getName());
    }

    @Test
    void shouldThrowWhenSavingPersonWithDuplicateId() {
        Person p1 = new Person();
        p1.setId(10);
        p1.setName("João");
        p1.setDtBirth(LocalDate.of(1985, 5, 5));
        personStorage.save(p1);

        Person p2 = new Person();
        p2.setId(10);
        p2.setName("Maria");
        p2.setDtBirth(LocalDate.of(1995, 6, 6));

        assertThrows(IllegalArgumentException.class, () -> personStorage.save(p2));
    }

    @Test
    void shouldUpdateExistingPerson() {
        Person p = new Person();
        p.setName("Carlos");
        p.setDtBirth(LocalDate.of(1992, 2, 2));
        Person saved = personStorage.save(p);

        Person update = new Person();
        update.setName("Carlos Silva");
        update.setDtBirth(LocalDate.of(1992, 2, 2));

        Person updated = personStorage.update(saved.getId(), update);

        assertEquals("Carlos Silva", updated.getName());
        assertEquals(saved.getId(), updated.getId());
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentPerson() {
        Person update = new Person();
        update.setName("Fantasma");

        assertThrows(NoSuchElementException.class, () -> personStorage.update(999, update));
    }

    @Test
    void shouldUpdateName() {
        Person person = new Person();
        person.setName("Laura");
        person.setDtBirth(LocalDate.of(2000, 3, 3));
        Person saved = personStorage.save(person);

        Person updated = personStorage.updateName(saved.getId(), "Laura Lima");

        assertEquals("Laura Lima", updated.getName());
    }

    @Test
    void shouldDeletePerson() {
        Person person = new Person();
        person.setName("Marcelo");
        person.setDtBirth(LocalDate.of(1991, 4, 4));
        Person saved = personStorage.save(person);

        assertDoesNotThrow(() -> personStorage.deleteById(saved.getId()));

        assertThrows(NoSuchElementException.class, () -> personStorage.findById(saved.getId()));
    }

    @Test
    void shouldFindAllSortedByName() {
        Person p1 = new Person(); p1.setName("Zoe"); p1.setDtBirth(LocalDate.now());
        Person p2 = new Person(); p2.setName("Ana"); p2.setDtBirth(LocalDate.now());

        personStorage.save(p1);
        personStorage.save(p2);

        List<Person> result = personStorage.findAllSortedByName();
        assertEquals("Ana", result.get(0).getName());
        assertEquals("Zoe", result.get(1).getName());
    }
}
