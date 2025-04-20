package com.example.demo.repositories;
import com.example.demo.DemodosApplication;
import com.example.demo.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import com.example.demo.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testSave(){
    var person = new Person(50, "Lily");
    var savedPerson= personRepository.save(person);

    assertEquals(50,savedPerson.getAge());
    assertEquals("Lily",savedPerson.getName());
    assertNotNull(personRepository.findById(savedPerson.getId()));

    System.out.println(savedPerson);

    personRepository.deleteAll();

    }

    @Test
    public void testFindAll(){
        var person1 = new Person(84, "Hubert");
        var person2 = new Person(18, "Tommy");
        var person3 = new Person(41, "Xiri");

        List<Person> personList = new ArrayList<>(List.of(person1, person2, person3));
        personRepository.saveAll(personList);

        var everybody = personRepository.findAll();
        assertNotNull(everybody);
        System.out.println(everybody);

        assertEquals(84, everybody.getFirst().getAge());


    }

    @Test
    public void testDelete(){
        var person1 = new Person(83, "Olvi");
        personRepository.save(person1);
        var personToDelete= personRepository.findById(person1.getId());
        assertNotNull(personToDelete);
        personRepository.deleteById(person1.getId());

        Optional<Person> result = personRepository.findById(person1.getId());
        assertTrue(result.isEmpty());

    }

    @Test
    public void testUpdate(){

        var person1 = new Person(67, "Laura");
        personRepository.save(person1);
        var everybody = personRepository.findAll();
        assertNotNull(everybody);
        System.out.println(everybody);
        Optional<Person> personFound = personRepository.findById(person1.getId());
        assertNotNull(personFound);
        Person person = personFound.get();
        person.setAge(68);
        person.setName("Lauri");
        personRepository.save(person);
        Optional<Person> personLauri = personRepository.findById(person1.getId());
        assertNotNull(personLauri);
        assertEquals("Lauri", personLauri.get().getName());
        assertEquals(68, personLauri.get().getAge());
    }


}
