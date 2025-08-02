package com.itheima.mp.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        // 创建基础插件对象
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 1. 创建分页插件
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        paginationInnerInterceptor.setMaxLimit(1000L);
        // 2. 添加分页插件
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        // 想要添加其他插件直接向 interceptor 中继续 add 即可
        return interceptor;
    }
}
