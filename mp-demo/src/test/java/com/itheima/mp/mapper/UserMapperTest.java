package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
//        user.setId(5L);
        user.setUsername("WangWuchong");
        user.setPassword("123");
        user.setPhone("18685990011");
        user.setBalance(200);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Test
    void testSelectById() {
        User user = userMapper.selectById(5L);
        System.out.println("user = " + user);
    }


    @Test
    void testQueryByIds() {
        List<User> users = userMapper.selectBatchIds(List.of(1L, 2L, 3L, 4L));
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(5L);
        user.setBalance(20000);
        userMapper.updateById(user);
    }

    @Test
    void testDeleteUser() {
        userMapper.deleteById(5L);
    }

    @Test
    void testQueryWrapper() {
        // 1.构建查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "info", "balance")
                .like("username", "o")
                .ge("balance", 1000);
        // 2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        // 1.设置要更新的数据
        User user = new User();
        user.setBalance(2000);
        // 2.构造查询条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "jack");
        // 3.查询
        userMapper.update(user, wrapper);
    }

    // 当更新内容比较复杂时才实用 UpdateWrapper 来设置比较复杂的 set 语句
    @Test
    void testUpdateByWrapper() {
        // 1.构建 UpdateWrapper
        List<Long> ids = List.of(1L, 2L, 4L);
        UpdateWrapper<User> wrapper = new UpdateWrapper<User>();
        wrapper.setSql("balance = balance - 200")
                .in("id", ids);
        // 2.查询
        userMapper.update(null, wrapper);
    }

    // 一般使用 query 来构建 select，update 和 delete 的 where 子句部分
    @Test
    void testLambdaQueryWrapper() {
        // 1.构建 Wrapper
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(User::getId, User::getUsername, User::getInfo, User::getBalance)
                .like(User::getUsername, "o")
                .lt(User::getBalance, 1000);
        // 2.查询
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testCustomSqlUpdate() {
        // 1.更新条件
        List<Long> ids = List.of(1L,  2L, 4L);
        Integer amount = 300;
        // 2.构建 wrapper
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(User::getId, ids);
        // 3.查询
        userMapper.updateBalanceByIds(wrapper, amount);
    }
}