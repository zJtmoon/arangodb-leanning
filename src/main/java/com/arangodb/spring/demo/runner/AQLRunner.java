package com.arangodb.spring.demo.runner;

import com.arangodb.ArangoCursor;
import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.entity.ChildOf;
import com.arangodb.spring.demo.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ComponentScan("com.arangodb.spring.demo")
public class AQLRunner implements CommandLineRunner {

    @Autowired
    private CharacterRepository repository;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# AQL查询");

        System.out.println("## 查找所有age超过21的Character（排序降序）");
        final Iterable<Character> older = repository.getOlderThan(21);
        older.forEach(System.out::println);

        System.out.println("## 查找surname 为“Lannister”的所有characters （按age升序排序）");
        final Iterable<Character> lannisters = repository.getWithSurname("Lannister");
        lannisters.forEach(System.out::println);

        System.out.println("## 查找surname为“Lannister”且年龄超过35岁的所有characters ");
        final Map<String, Object> bindvars = new HashMap<>();
        bindvars.put("surname", "Lannister");
        bindvars.put("@col", Character.class);
        final ArangoCursor<Character> oldLannisters = repository.getWithSurnameOlderThan(35, bindvars);
        System.out.println(String.format("Found %s documents", oldLannisters.getCount()));
        oldLannisters.forEach(System.out::println);

        System.out.println("## 查找“Tywin Lannister”的所有childs（按age递减排序）");
        repository.findByNameAndSurname("Tywin", "Lannister").ifPresent(tywin -> {
            final Set<Character> childs = repository.getAllChildsAndGrandchilds("characters/" + tywin.getId(), ChildOf.class);
            childs.forEach(System.out::println);
        });
    }

}
