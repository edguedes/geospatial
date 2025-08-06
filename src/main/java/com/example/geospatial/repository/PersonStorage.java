package com.example.geospatial.repository;

import com.example.geospatial.entites.Person;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PersonStorage {

    private final Map<Integer, Person> personMap = new HashMap<>();

    public synchronized Person save(Person person) {
        Integer personId = person.getId();

        if (personId == null) {
            personId = personMap.keySet().stream()
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
            person.setId(personId);
        } else if (personMap.containsKey(personId)) {
            throw new IllegalArgumentException("There is already a person with that ID.");
        }

        if (person.getDtAdmission() == null) {
            person.prePersist();
        }

        personMap.put(personId, person);
        return person;
    }

    public List<Person> findAllSortedByName() {
        return personMap.values().stream()
                .sorted(Comparator.comparing(Person::getName))
                .toList();
    }

    public void deleteById(Integer id) {
        findById(id);
        personMap.remove(id);
    }

    public synchronized Person update(Integer id, Person newPerson) {
        Person oldPerson = findById(id);
        newPerson.setId(id);
        newPerson.setDtAdmission(oldPerson.getDtAdmission());
        personMap.put(id, newPerson);

        return newPerson;
    }

    public synchronized Person updateName(Integer id, String newName) {
        Person person = findById(id);
        person.setName(newName);

        return person;
    }

    public Person findById(Integer id) {
        Person person = personMap.get(id);
        if (person == null) {
            throw new NoSuchElementException("Person not found. ID: " + id);
        }
        return person;
    }
}
