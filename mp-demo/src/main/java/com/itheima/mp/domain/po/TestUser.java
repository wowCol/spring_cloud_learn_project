package com.itheima.mp.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

// mybatis-plus中使用 @TableName 关键字实现指定表名
@TableName("User")
public class TestUser {
    // 使用 @TableId 指定主键，其中 value 指定主键对应的数据库表项名称，type 对应主键的策略
    // 主键策略包括：AUTO 自增，INPUT 手动设置，ASSIGN_ID （默认方式）使用雪花算法生成
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    // 使用 @TableField 指定当前变量对应的数据库表项名称
    // 1.当变量名称与数据库表名称不同的时候使用
    @TableField("username")
    private String name;
    // 2.当为 is 开头，且为 Boolean 类型的数据时
    // 默认约定中该类数据会自动去掉 is_ 前缀
    @TableField("is_married")
    private Boolean isMarried;
    // 3.当变量名称与数据库字段名冲突时
    // 如：order 在数据库中是 order by 的前置，若直接使用会导致数据库报错，因此需要转义，使用` `包裹
    @TableField("`order`")
    private Integer order;
    // 4.当前变量不存在于数据库表中时，防止查询报错
    @TableField(exist = false)
    private String address;
}
