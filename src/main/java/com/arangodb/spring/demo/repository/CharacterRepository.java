package com.arangodb.spring.demo.repository;

import com.arangodb.ArangoCursor;
import com.arangodb.spring.demo.entity.Character;
import com.arangodb.springframework.annotation.BindVars;
import com.arangodb.springframework.annotation.Query;
import com.arangodb.springframework.annotation.QueryOptions;
import com.arangodb.springframework.repository.ArangoRepository;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface CharacterRepository extends ArangoRepository<Character, String> {

    Iterable<Character> findBySurname(String surname);

    Collection<Character> findTop2DistinctBySurnameIgnoreCaseOrderByAgeDesc(String surname);

    List<Character> findBySurnameEndsWithAndAgeBetweenAndNameInAllIgnoreCase(
            String suffix,
            int lowerBound,
            int upperBound,
            String[] nameList);

    Optional<Character> findByNameAndSurname(String name, String surname);

    Integer countByAliveTrue();

    void removeBySurnameNotLikeOrAliveFalse(String surname);

    Iterable<Character> findByChildsName(String name);

    Iterable<Character> findByChildsAgeBetween(int lowerBound, int upperBound);

    /**
     * 查找所有age超过21的Character（排序降序）
     *
     * @param value
     * @return
     */
    @Query("FOR c IN characters FILTER c.age > @value SORT c.age DESC RETURN c")
    Iterable<Character> getOlderThan(@Param("value") int value);

    @Query("FOR c IN characters FILTER c.surname == @surname SORT c.age ASC RETURN c")
    Iterable<Character> getWithSurname(@Param("surname") String value);

    @Query("FOR c IN @@col FILTER c.surname == @surname AND c.age > @age RETURN c")
    @QueryOptions(count = true)
    ArangoCursor<Character> getWithSurnameOlderThan(@Param("age") int value, @BindVars Map<String, Object> bindvars);

    /**
     * 关于 INBOUND、OUTBOUND、ANY 飞区别，可以查看文章 https://blog.csdn.net/yuzongtao/article/details/76061897
     * @param id
     * @param edgeCollection
     * @return
     */
    @Query("FOR v IN 1..2 INBOUND @id @@edgeCol SORT v.age DESC RETURN DISTINCT v")
    Set<Character> getAllChildsAndGrandchilds(@Param("id") String id, @Param("@edgeCol") Class<?> edgeCollection);

}
