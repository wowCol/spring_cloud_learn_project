package com.itheima.mp.service;

import com.itheima.mp.domain.po.Address;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddressServiceTest {

    @Resource
    private AddressService addressService;

    @Test
    public void testLogicDeleteAddress() {
        // 1. 删除
        addressService.removeById(59L);

        // 2. 查询
        Address address = addressService.getById(59L);
        System.out.println("address = " + address);
    }
}