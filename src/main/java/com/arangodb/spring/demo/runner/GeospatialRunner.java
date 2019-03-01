package com.arangodb.spring.demo.runner;

import com.arangodb.spring.demo.entity.Location;
import com.arangodb.spring.demo.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Range;
import org.springframework.data.domain.Range.Bound;
import org.springframework.data.geo.*;

import java.util.Arrays;

@ComponentScan("com.arangodb.spring.demo")
public class GeospatialRunner implements CommandLineRunner {

    @Autowired
    private LocationRepository repository;

    @Override
    public void run(final String... args) throws Exception {
        System.out.println("# 地理空间");

        repository.saveAll(Arrays.asList(new Location("Dragonstone", new double[]{55.167801, -6.815096}),
                new Location("King's Landing", new double[]{42.639752, 18.110189}),
                new Location("The Red Keep", new double[]{35.896447, 14.446442}),
                new Location("Yunkai", new double[]{31.046642, -7.129532}),
                new Location("Astapor", new double[]{31.50974, -9.774249}),
                new Location("Winterfell", new double[]{54.368321, -5.581312}),
                new Location("Vaes Dothrak", new double[]{54.16776, -6.096125}),
                new Location("Beyond the wall", new double[]{64.265473, -21.094093})));

        System.out.println("## 查找'Winterfell'附近的前5个Location - 当前为第一页数据");
        final GeoPage<Location> first5 = repository.findByLocationNear(new Point(-5.581312, 54.368321),
                PageRequest.of(0, 5));
        first5.forEach(System.out::println);

        System.out.println("## 查找'Winterfell'附近的5个Location（仅剩3个Location） - 当前为第二页数据");
        final GeoPage<Location> next5 = repository.findByLocationNear(new Point(-5.581312, 54.368321),
                PageRequest.of(1, 5));
        next5.forEach(System.out::println);

        System.out.println("## 查找距离'Winterfell'50公里范围内的所有Location");
        final GeoResults<Location> findWithing50kilometers = repository
                .findByLocationWithin(new Point(-5.581312, 54.368321), new Distance(50, Metrics.KILOMETERS));
        findWithing50kilometers.forEach(System.out::println);

        System.out.println("## 找到距离'Winterfell'40到50公里的所有Location");
        final Iterable<Location> findByLocationWithin = repository.findByLocationWithin(new Point(-5.581312, 54.368321),
                Range.of(Bound.inclusive(40000.), Bound.inclusive(50000.)));
        findByLocationWithin.forEach(System.out::println);

        System.out.println("## 查找给定多边形内的所有Location");
        final Iterable<Location> withinPolygon = repository.findByLocationWithin(
                new Polygon(Arrays.asList(new Point(-25, 40), new Point(-25, 70), new Point(25, 70))));
        withinPolygon.forEach(System.out::println);
    }

}
