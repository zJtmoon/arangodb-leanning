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
import com.arangodb.spring.demo.entity.CompanyInvestmentRelation;
import com.arangodb.spring.demo.entity.InvestmentRelation;
import com.arangodb.spring.demo.entity.Member;
import com.arangodb.spring.demo.repository.CompanyInvestRmentelationReposition;
import com.arangodb.spring.demo.repository.CompanyRepository;
import com.arangodb.spring.demo.repository.InvestRmentelationReposition;
import com.arangodb.spring.demo.repository.MemberRepository;
import com.arangodb.springframework.annotation.From;
import com.arangodb.springframework.annotation.To;
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
public class CompanyInvestRunner implements CommandLineRunner {
    @Autowired
    private CompanyInvestRmentelationReposition companyInvestRmentelationReposition;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void run(String... args) throws Exception {
        CsvReader reader = CsvUtil.getReader();
//        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/2020-8-10-11-50-30-29831949762767-北京百度网讯科技有限公司-梁志祥【工商信息-电话地址-注册信-采集的数据-后羿采集器.csv"));
        CsvData data = reader.read(FileUtil.file("//home/zjt/下载/一起拼拼乐/企业3/2020-8-10-17-46-59-886797180464400-对外投资的数据-后羿采集器.csv"));

        String companyNameto = "";
        String companyKeyfrom = "";

        String companyKeyto = "";

        String type_change = "";

        String change_before_proportion = "";

        String change_after_proportion = "";

        String change_before_money = "";

        String change_after_money = "";

        String date;

        List<CsvRow> rows = data.getRows();
        for (CsvRow csvRow : rows) {
            companyNameto=csvRow.get(0);
            companyKeyto=csvRow.get(1).split("/")[4];
            type_change=csvRow.get(2);
            change_before_proportion=csvRow.get(3);
            change_after_proportion=csvRow.get(4);
            change_before_money=csvRow.get(5);
            change_after_money=csvRow.get(6);
            date=csvRow.get(7);

            companyKeyfrom= csvRow.get(9).split("/")[4];
            System.out.println("companyKeyfrom   "+companyKeyfrom);
//            memberKey = csvRow.get(0).split("/")[4];
//            companyKey = csvRow.get(1).split("/")[4];
//            Member m = Member.builder().key(memberKey).build();


            Optional<Company2> lannisters = companyRepository.findById("company2/" + companyKeyfrom);
            Company2 company_from = lannisters.get();
            Company2 company_to=Company2.builder().company_name(csvRow.get(0)).key(companyKeyto).build();


            System.out.println("company_from------------------" + company_from);
            System.out.println("companyto------------------" + company_to);
            CompanyInvestmentRelation companyInvestmentRelation=CompanyInvestmentRelation.builder().companyfrom(company_from)
                    .companyto(company_to)
                    .change_before_proportion(change_before_proportion)
                    .change_after_proportion(change_after_proportion)
                    .change_before_money(change_before_money)
                    .change_after_money(change_after_money)
                    .type_change(type_change)
                    .date(date).build();

            companyRepository.save(company_to);
            companyInvestRmentelationReposition.save(companyInvestmentRelation);
            System.out.println("companyInvestmentRelation------------------" + companyInvestmentRelation);



        }
    }
}
