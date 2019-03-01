package com.arangodb.spring.demo.runner;

import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CharacterRepository;
import com.arangodb.springframework.core.ArangoOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collection;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

@ComponentScan("com.arangodb.spring.demo")
public class CrudRunner implements CommandLineRunner {

    @Autowired
    private ArangoOperations operations;
    @Autowired
    private CharacterRepository repository;

    @Override
    public void run(final String... args) throws Exception {
        // 首先删除数据库，以便我们可以使用相同的数据集多次运行它
        operations.dropDatabase();

        System.out.println("# CRUD 操作");

        // 在数据库中保存单个实体
        // 不需要先创建集合。 这会自动发生
        final Character nedStark = new Character("Ned", "Stark", true, 41);
        repository.save(nedStark);
        // 数据库中生成的id在原始实体中设置
        System.out.println(String.format("Ned Stark 在数据库中的ID为: '%s'", nedStark.getId()));

        // 让我们来看看我们是否可以在数据库中找到Ned Stark
        final Character foundNed = repository.findById(nedStark.getId()).get();
        System.out.println(String.format("找到记录  %s", foundNed));

        // 因为每个人都知道Ned Stark在第一季就死了。
        // 所以我们必须更新他的“活着”旗帜
        nedStark.setAlive(false);
        repository.save(nedStark);
        final Character deadNed = repository.findById(nedStark.getId()).get();
        System.out.println(String.format("Ned Stark在'alive'标志更新后 : %s", deadNed));

        // 我们保存一些额外的字符
        final Collection<Character> createCharacters = createCharacters();
        System.out.println(String.format("保存 %s 个附加字符", createCharacters.size()));
        repository.saveAll(createCharacters);

        final Iterable<Character> all = repository.findAll();
        final long count = StreamSupport.stream(Spliterators.spliteratorUnknownSize(all.iterator(), 0), false).count();
        System.out.println(String.format("数据库中共有 %s 个字符", count));

        System.out.println("## 返回按名称排序的所有字符");
        final Iterable<Character> allSorted = repository.findAll(new Sort(Sort.Direction.ASC, "name"));
        allSorted.forEach(System.out::println);

        System.out.println("## 返回按名称排序的前5个字符");
        final Page<Character> first5Sorted = repository
                .findAll(PageRequest.of(0, 5, new Sort(Sort.Direction.ASC, "name")));
        first5Sorted.forEach(System.out::println);
    }


    /**
     * 测试数据集
     */
    public static Collection<Character> createCharacters() {
        return Arrays.asList(new Character("Ned", "Stark", false, 41), new Character("Robert", "Baratheon", false),
                new Character("Jaime", "Lannister", true, 36), new Character("Catelyn", "Stark", false, 40),
                new Character("Cersei", "Lannister", true, 36), new Character("Daenerys", "Targaryen", true, 16),
                new Character("Jorah", "Mormont", false), new Character("Petyr", "Baelish", false),
                new Character("Viserys", "Targaryen", false), new Character("Jon", "Snow", true, 16),
                new Character("Sansa", "Stark", true, 13), new Character("Arya", "Stark", true, 11),
                new Character("Robb", "Stark", false), new Character("Theon", "Greyjoy", true, 16),
                new Character("Bran", "Stark", true, 10), new Character("Joffrey", "Baratheon", false, 19),
                new Character("Sandor", "Clegane", true), new Character("Tyrion", "Lannister", true, 32),
                new Character("Khal", "Drogo", false), new Character("Tywin", "Lannister", false),
                new Character("Davos", "Seaworth", true, 49), new Character("Samwell", "Tarly", true, 17),
                new Character("Stannis", "Baratheon", false), new Character("Melisandre", null, true),
                new Character("Margaery", "Tyrell", false), new Character("Jeor", "Mormont", false),
                new Character("Bronn", null, true), new Character("Varys", null, true), new Character("Shae", null, false),
                new Character("Talisa", "Maegyr", false), new Character("Gendry", null, false),
                new Character("Ygritte", null, false), new Character("Tormund", "Giantsbane", true),
                new Character("Gilly", null, true), new Character("Brienne", "Tarth", true, 32),
                new Character("Ramsay", "Bolton", true), new Character("Ellaria", "Sand", true),
                new Character("Daario", "Naharis", true), new Character("Missandei", null, true),
                new Character("Tommen", "Baratheon", true), new Character("Jaqen", "H'ghar", true),
                new Character("Roose", "Bolton", true), new Character("The High Sparrow", null, true));
    }

}
