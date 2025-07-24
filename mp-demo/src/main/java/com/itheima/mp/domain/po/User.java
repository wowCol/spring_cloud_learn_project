package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/*
 表名注解常用于：
 1. 成员变量与数据库字段名称不一致
 2. 成员变量名以 is 开头，且是布尔值
 3. 成员变量名与数据库关键字冲突
 4. 成员变量不是数据库字段
*/
@Data
@TableName("user")
public class User {

    // 用户id
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    // 用户名
    @TableField("username")
    private String username;

    // 密码
    @TableField("password")
    private String password;

    // 注册手机号
    @TableField("phone")
    private String phone;

    // 详细信息
    @TableField("info")
    private String info;

    // 使用状态（1正常 2冻结）
    @TableField("status")
    private Integer status;

    // 账户余额
    @TableField("balance")
    private Integer balance;

    // 创建时间
    @TableField("create_time")
    private LocalDateTime createTime;

    // 更新时间
    @TableField("update_time")
    private LocalDateTime updateTime;

    // 地址
    @TableField(exist = false)
    private String address;
}
