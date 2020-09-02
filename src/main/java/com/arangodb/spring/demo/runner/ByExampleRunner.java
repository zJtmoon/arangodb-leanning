package com.arangodb.spring.demo.runner;

import com.arangodb.internal.ArangoDatabaseImpl;
import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import java.util.Optional;

@ComponentScan("com.arangodb.spring.demo")
public class ByExampleRunner implements CommandLineRunner {

    @Autowired
    private CharacterRepository repository;
    @Autowired
    private ArangoDatabaseImpl arangoDatabase;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# 通过示例查询");
        final Character nedStark = new Character("Ned", "Stark", false, 41);

        System.out.println(String.format("## 找到匹配的字符 %s", nedStark));
        final Character foundNedStark = repository.findOne(Example.of(nedStark)).get();
        System.out.println(String.format("找到记录 %s", foundNedStark));

        System.out.println("## 找到所有死去的 Starks");
        // 因为我们只关心我们实体中的surname和alive，所以我们必须忽略ExampleMatcher中的其他字段
        final Iterable<Character> allDeadStarks = repository
                .findAll(Example.of(new Character(null, "Stark", false), ExampleMatcher.matchingAll()
                        .withMatcher("surname", match -> match.exact()).withIgnorePaths("name", "age")));
        allDeadStarks.forEach(System.out::println);

        System.out.println("## 找到所有比Ned Stark年轻30岁的Starks");
        // 而不是改变Ned Stark实体的年龄，而是在ExampleMatcher中使用变换器。 因为我们使用从db获取的实体，所以我们必须忽略非空的字段'id'
        final Iterable<Character> allYoungerStarks = repository.findAll(Example.of(foundNedStark,
                ExampleMatcher.matchingAll().withMatcher("surname", match -> match.exact())
                        .withIgnorePaths("id", "name", "alive")
                        .withTransformer("age", age -> Optional.of(((int) age.get()) - 30))));
        allYoungerStarks.forEach(System.out::println);

        System.out.println("## 找到surname以'ark'结尾的所有字符（忽略大小写）");
        final Iterable<Character> ark = repository.findAll(Example.of(new Character(null, "ark", false),
                ExampleMatcher.matchingAll().withMatcher("surname", match -> match.endsWith()).withIgnoreCase()
                        .withIgnorePaths("name", "alive", "age")));
        ark.forEach(System.out::println);
    }

}
