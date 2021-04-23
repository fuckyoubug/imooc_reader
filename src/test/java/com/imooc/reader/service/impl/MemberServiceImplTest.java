package com.imooc.reader.service.impl;

import com.imooc.reader.entity.Member;
import com.imooc.reader.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext1.xml"})
public class MemberServiceImplTest {
        @Resource
    MemberService memberService;
    @Test
    public void createMember() {

        Member member = memberService.createMember("hx", "123456", "hx 臭b");
        System.out.println("member:"+member);

    }
}