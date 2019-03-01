package com.arangodb.spring.demo.runner;

import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.entity.ChildOf;
import com.arangodb.spring.demo.repository.CharacterRepository;
import com.arangodb.spring.demo.repository.ChildOfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@ComponentScan("com.arangodb.spring.demo")
public class RelationsRunner implements CommandLineRunner {

    @Autowired
    private CharacterRepository characterRepo;
    @Autowired
    private ChildOfRepository childOfRepo;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# 关系");
        //重新初始化数据
        characterRepo.saveAll(CrudRunner.createCharacters());

        // 首先为Starks和Lannisters创造一些关系
        characterRepo.findByNameAndSurname("Ned", "Stark").ifPresent(ned -> {
            characterRepo.findByNameAndSurname("Catelyn", "Stark").ifPresent(catelyn -> {
                characterRepo.findByNameAndSurname("Robb", "Stark").ifPresent(robb -> {
                    childOfRepo.saveAll(Arrays.asList(new ChildOf(robb, ned), new ChildOf(robb, catelyn)));
                });
                characterRepo.findByNameAndSurname("Sansa", "Stark").ifPresent(sansa -> {
                    childOfRepo.saveAll(Arrays.asList(new ChildOf(sansa, ned), new ChildOf(sansa, catelyn)));
                });
                characterRepo.findByNameAndSurname("Arya", "Stark").ifPresent(arya -> {
                    childOfRepo.saveAll(Arrays.asList(new ChildOf(arya, ned), new ChildOf(arya, catelyn)));
                });
                characterRepo.findByNameAndSurname("Bran", "Stark").ifPresent(bran -> {
                    childOfRepo.saveAll(Arrays.asList(new ChildOf(bran, ned), new ChildOf(bran, catelyn)));
                });
            });
            characterRepo.findByNameAndSurname("Jon", "Snow")
                    .ifPresent(bran -> childOfRepo.save(new ChildOf(bran, ned)));
        });

        characterRepo.findByNameAndSurname("Tywin", "Lannister").ifPresent(tywin -> {
            characterRepo.findByNameAndSurname("Jaime", "Lannister").ifPresent(jaime -> {
                childOfRepo.save(new ChildOf(jaime, tywin));
                characterRepo.findByNameAndSurname("Cersei", "Lannister").ifPresent(cersei -> {
                    childOfRepo.save(new ChildOf(cersei, tywin));
                    characterRepo.findByNameAndSurname("Joffrey", "Baratheon").ifPresent(joffrey -> {
                        childOfRepo.saveAll(Arrays.asList(new ChildOf(joffrey, jaime), new ChildOf(joffrey, cersei)));
                    });
                });
            });
            characterRepo.findByNameAndSurname("Tyrion", "Lannister")
                    .ifPresent(tyrion -> childOfRepo.save(new ChildOf(tyrion, tywin)));
        });

        //然后，我们在Character类中的 private Collection<Character> childs; 属性上面加注解  @Relations(edges = ChildOf.class, lazy = true)
        // 我们现在可以在获取Character时加载Character的所有children
        characterRepo.findByNameAndSurname("Ned", "Stark").ifPresent(nedStark -> {
            System.out.println(String.format("## 下面是 %s 的child:", nedStark));
            nedStark.getChilds().forEach(System.out::println);
        });

        // 字段'childs'不会保留在character文档本身中，而是通过edges表示。 然而，我们可以编写一个包含连接character属性的派生方法
        System.out.println("## 这些是'Sansa'的父母 =====");
        final Iterable<Character> parentsOfSansa = characterRepo.findByChildsName("Sansa");
        parentsOfSansa.forEach(System.out::println);

        System.out.println("## 这些parents的child年龄在16到20岁之间");
        final Iterable<Character> childsBetween16a20 = characterRepo.findByChildsAgeBetween(16, 20);
        childsBetween16a20.forEach(System.out::println);
    }

}
