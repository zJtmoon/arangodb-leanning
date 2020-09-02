/*
 * Copyright: 2020 dingxiang-inc.com Inc. All rights reserved.
 */

package com.arangodb.spring.demo.runner;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import com.arangodb.spring.demo.entity.Company2;
import com.arangodb.spring.demo.entity.CompanyRelation;
import com.arangodb.spring.demo.entity.InvestmentRelation;
import com.arangodb.spring.demo.entity.Member;
import com.arangodb.spring.demo.repository.CompanyRelationReposition;
import com.arangodb.spring.demo.repository.CompanyRepository;
import com.arangodb.spring.demo.repository.InvestRmentelationReposition;
import com.arangodb.spring.demo.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.Optional;

/**
 * @FileName: CompanyRunner.java
 * @Description: CompanyRunner.java类说明
 * @Author: zjt
 * @Date: 20-8-7 上午12:41
 */
@ComponentScan("com.arangodb.spring.demo")
public class InvestRunner implements CommandLineRunner {
    @Autowired
    private InvestRmentelationReposition investRmentelationReposition;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        CsvReader reader = CsvUtil.getReader();
//        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/2020-8-10-10-54-44-2648655141815-职位的链接的数据-后羿采集器.csv"));
//        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/企业3/2020-8-10-18-45-16-54717457881879-职位的链接2的数据-后羿采集器.csv"));
        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/企业3/2020-8-10-19-54-47-58888868446157-职位的链接3的数据-后羿采集器.csv"));
        String memberKey="";
        String companyKey="";
        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            //getRawList返回一个List列表，列表的每一项为CSV中的一个单元格（既逗号分隔部分）
            memberKey=csvRow.get(4).split("/")[4];
            companyKey=csvRow.get(1).split("/")[4];
//            Member m = Member.builder().key(memberKey).build();


            Optional<Member> lannisters = memberRepository.findById("member/"+memberKey);
            Member m=lannisters.get();
//            if (!lannisters.iterator().hasNext()) {
//                memberRepository.save(m);
//            } else {
//                m = (Member) lannisters.iterator().next();
//            }

            Company2 company = Company2.builder().company_name(csvRow.get(0)).key(companyKey).build();
            System.out.println("member------------------" +m);
            System.out.println("company------------------" +company);
            InvestmentRelation investmentRelation=InvestmentRelation.builder().company(company).proportion(csvRow.get(2)).member(m).build();
            companyRepository.save(company);
            System.out.println("company------------------" +company);
            System.out.println("investmentRelation------------------" +investmentRelation);

            investRmentelationReposition.save(investmentRelation);


        }
    }
}
