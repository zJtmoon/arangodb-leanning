package com.arangodb.spring.demo.runner;

import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@ComponentScan("com.arangodb.spring.demo")
public class DerivedQueryRunner implements CommandLineRunner {

    @Autowired
    private CharacterRepository repository;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# 扩展  查询");

        System.out.println("## 找到所有surname为'Lannister'的人物");
        final Iterable<Character> lannisters = repository.findBySurname("Lannister");
        lannisters.forEach(System.out::println);

        System.out.println("##查找按年龄排序的前2名Lannnisters");
        final Collection<Character> top2 = repository.findTop2DistinctBySurnameIgnoreCaseOrderByAgeDesc("lannister");
        top2.forEach(System.out::println);

        System.out.println(
                "##找到名为'Bran'或'Sansa'的所有characters，它的姓氏以'ark'结尾，年龄在10到16岁之间");
        final List<Character> youngStarks = repository.findBySurnameEndsWithAndAgeBetweenAndNameInAllIgnoreCase("ark",
                10, 16, new String[]{"Bran", "Sansa"});
        youngStarks.forEach(System.out::println);

        System.out.println("## 按name和surname查找单个character ");
        final Optional<Character> tyrion = repository.findByNameAndSurname("Tyrion", "Lannister");
        tyrion.ifPresent(c -> {
            System.out.println(String.format("查询出数据 -  %s", c));
        });

        System.out.println("## 计算仍有多少个characters alive");
        final Integer alive = repository.countByAliveTrue();
        System.out.println(String.format("alive为true的数据数量为", alive));

        System.out.println("## 删除姓氏为非“Stark”或者 alive为false的所有characters ");
        repository.removeBySurnameNotLikeOrAliveFalse("Stark");
        // 数据库中剩余的数据，预计只剩下Arya，Bran和Sansa
        repository.findAll().forEach(System.out::println);
    }

}
