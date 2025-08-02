package com.itheima.mp.service;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import com.itheima.mp.domain.po.UserInfo;
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
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
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
        user.setInfo(UserInfo.of(24, "英文老师", "female"));
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        return user;
    }

//    @Test
//    void testSaveOneByOne() {
//        // 单个 for 循环处理的方式
//        long b = System.currentTimeMillis();
//        for (int i = 0; i <= 100000; i++) {
//            userService.save(buildUser(i));
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("耗时: " + (e - b));
//    }
//
//    @Test
//    void testSaveBatch() {
//        // 使用批处理
//        // 每次批量插入 1000 条数据，插入 100 次即 10 万条数据
//
//        List<User> list = new ArrayList<>(1000);
//        long b = System.currentTimeMillis();
//        for (int i = 0; i <= 100000; i++) {
//            // 添加 user
//            list.add(buildUser(i));
//
//            // 每 1000 条插入一次
//            if (i % 1000 == 0) {
//                userService.saveBatch(list);
//
//                // 清空集合，准备下一批数据
//                list.clear();
//            }
//        }
//        long e = System.currentTimeMillis();
//        System.out.println("耗时: " + (e - b));
//        // 因为使用批处理，将网络请求次数减少，降低运行时间
//
//        // saveBatch 将数据编译为一条条 sql 语句，但是对于数据库，其仍是一条一条执行 sql 语句，效率不高
//        // 对于 mySQL 数据库，添加参数 rewriteBatchedStatements=ture 使得数据库开始批量插入（即一个语句插入1000条数据）
//    }

    @Test
    void testPageQuery() {
        int pageOn = 1, pageSize = 2;
        // 1. 准备分页条件
        // 1.1 分页条件
        Page<User> page = Page.of(pageOn, pageSize);
        // 1.2 排序条件
        // 首先按照 balance 排序，当 balance 相同时再按照 id 排序
        page.addOrder(OrderItem.asc("balance"));
        page.addOrder(OrderItem.asc("id"));

        // 2. 分页查询
        Page<User> p = userService.page(page);

        // 3. 解析
        long total = p.getTotal();
        System.out.println("total = " + total);
        long pages = p.getPages();
        System.out.println("pages = " + pages);
        List<User> users = p.getRecords();
        users.forEach(System.out::println);
    }
}