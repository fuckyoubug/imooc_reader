package com.imooc.reader.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class) //运行时初始化spring容器
@ContextConfiguration(locations = {"classpath:applicationContext1.xml"})
public class TestServiceTest  {

    @Resource
    private  TestService testService;
    @Test
    public void testBatchImport() {
        testService.batchImport();
        System.out.println("批量导入成功");
    }
}