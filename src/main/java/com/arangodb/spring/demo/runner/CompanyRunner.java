/*
 * Copyright: 2020 dingxiang-inc.com Inc. All rights reserved.
 */

package com.arangodb.spring.demo.runner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.arangodb.spring.demo.entity.*;
import com.arangodb.spring.demo.entity.Character;
import com.arangodb.spring.demo.repository.CompanyRelationReposition;
import com.arangodb.spring.demo.repository.CompanyRepository;
import com.arangodb.spring.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * @FileName: CompanyRunner.java
 * @Description: CompanyRunner.java类说明
 * @Author: zjt
 * @Date: 20-8-7 上午12:41
 */
@ComponentScan("com.arangodb.spring.demo")
public class CompanyRunner implements CommandLineRunner {
    @Autowired
    private CompanyRelationReposition companyRelationReposition;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        CsvReader reader = CsvUtil.getReader();
//        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/职位与公司.csv"));
        CsvData data = reader.read(FileUtil.file("/home/zjt/下载/一起拼拼乐/企业3/2020-8-10-17-4-57-48699146696488-企业3的数据-后羿采集器.csv"));

        String memberKey="";
        String companyKey="";
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            memberKey=csvRow.get(1).split("/")[4];
            companyKey=csvRow.get(4).split("/")[4];
            Member m = Member.builder().name(csvRow.get(0)).key(memberKey).build();
            Company2 company = Company2.builder().company_name(csvRow.get(3)).key(companyKey).build();

//            Iterable<Member> lannisters = memberRepository.findByName(m.getName());
//            if (!lannisters.iterator().hasNext()){
//                memberRepository.save(m);
//            }else {
//                m= (Member) lannisters.iterator().next();
//            }


            memberRepository.save(m);
            CompanyRelation companyRelation = new CompanyRelation(m, company, csvRow.get(2));

            System.out.println("m------------------------   "+m);

            System.out.println("company------------------------   "+company);

            companyRepository.save(company);
            companyRelationReposition.save(companyRelation);


        }
    }
}
