package com.imooc.reader;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.imooc.reader.entity.Test;
import com.imooc.reader.mapper.TestMapper;
//import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext1.xml"})
public class MybatisPlusTest {

    @Resource
    private TestMapper testMapper;

    @org.junit.Test
    public  void testInsert(){
        Test test = new Test();
        test.setContent("MybatisPlus 测试");
        testMapper.insert(test);
    }

    @org.junit.Test
    public  void testUpdate(){
      Test test =   testMapper.selectById(34);
        test.setContent("this is Mybatis plus test !");
        testMapper.updateById(test);
    }
    @org.junit.Test
    public  void testDelete()
    {
        testMapper.deleteById(34);
    }
    @org.junit.Test
    public  void testSelect()
    {
        QueryWrapper<Test > queryWrapper = new QueryWrapper<Test>();
        queryWrapper.eq("id",30); // 等于判断查询
          queryWrapper.gt("id",25);          //范围查询 大于25的数据
        //eq 和 gt 都写 为id等于且大于    默认用and连接
        List<Test> list = testMapper.selectList(queryWrapper);
        System.out.println(list.get(0));
    }
}
