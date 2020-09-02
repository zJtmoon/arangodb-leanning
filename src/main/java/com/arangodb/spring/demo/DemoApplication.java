package com.arangodb.spring.demo;

import com.arangodb.spring.demo.runner.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(final String... args) {

        //每个Runner都是独立的样例demo，可注释其它Runner进行运行测试
        final Class<?>[] runner = new Class<?>[]{
                CrudRunner.class,
//                ByExampleRunner.class,
//                DerivedQueryRunner.class,
//                RelationsRunner.class,
//                AQLRunner.class,
//                GeospatialRunner.class
//                CompanyRunner.class
//                  InvestRunner.class
//                CompanyInvestRunner.class
        };
        System.exit(SpringApplication.exit(SpringApplication.run(runner, args)));
    }
}