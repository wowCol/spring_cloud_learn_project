package com.itheima.mp.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.itheima.mp.domain.po.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

// 继承mybatis-plus，注意添加要使用的实体类
public interface UserMapper extends BaseMapper<User> {
    void updateBalanceByIds(@Param(Constants.WRAPPER) LambdaQueryWrapper<User> wrapper,
                            @Param("amount") Integer amount);

    void deductBalance(Long id, Integer money);
    // mybatis-plus使用“驼峰转下划线”方式将类型修改后直接作为数据库表名使用
    // 名为id的属性作为主键使用
    // 其余变量名转化后作为数据库字段名插入
}
