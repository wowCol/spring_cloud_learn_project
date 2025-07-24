package com.itheima.mp.service;

import com.itheima.mp.domain.po.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void testSaveUser() {
        User user = new User();
//        user.setId(5L);
        user.setUsername("LiLeiLei");
        user.setPassword("123");
        user.setPhone("18685990011");
        user.setBalance(2000);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());

        userService.save(user);
    }

    @Test
    void testQueryUser() {
        List<User> users = userService.listByIds(List.of(1L, 2L, 4L));
        users.forEach(System.out::println);
    }

    private User buildUser(int i) {
        User user = new User();
        user.setUsername("user_" + i);
        user.setPassword("123");
        user.setPhone("18685990011L" + i);
        user.setBalance(2000);
        user.setInfo("{\"age\": 24, \"intro\": \"英文老师\", \"gender\": \"female\"}");
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

    @Test
    void testSaveOneByOne() {
        // 单个 for 循环处理的方式
        long b = System.currentTimeMillis();
        for (int i = 0; i <= 100000; i++) {
            userService.save(buildUser(i));
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时: " + (e - b));
    }

    @Test
    void testSaveBatch() {
        // 使用批处理
        // 每次批量插入 1000 条数据，插入 100 次即 10 万条数据

        List<User> list = new ArrayList<>(1000);
        long b = System.currentTimeMillis();
        for (int i = 0; i <= 100000; i++) {
            // 添加 user
            list.add(buildUser(i));

            // 每 1000 条插入依次
            if (i % 1000 == 0) {
                userService.saveBatch(list);

                // 清空集合，准备下一批数据
                list.clear();
            }
        }
        long e = System.currentTimeMillis();
        System.out.println("耗时: " + (e - b));
        // 因为使用批处理，将网络请求次数减少，降低运行时间

        // saveBatch 将数据编译为一条条 sql 语句，但是对于数据库，其仍是一条一条执行 sql 语句，效率不高
        // 对于 mySQL 数据库，添加参数 rewriteBatchedStatements=ture 使得数据库开始批量插入（即一个语句插入1000条数据）
    }
}