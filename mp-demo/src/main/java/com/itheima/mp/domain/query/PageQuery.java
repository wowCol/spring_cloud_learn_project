package com.itheima.mp.domain.query;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.mp.domain.po.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "分页查询实体")
public class PageQuery {
    @ApiModelProperty("页码")
    private Long pageNo = 1L;
    @ApiModelProperty("页面大小")
    private Long pageSize = 10L;
    @ApiModelProperty("排序字段")
    private String sortBy;
    @ApiModelProperty("是否升序")
    private Boolean isAsc = true;

    public <T> Page<T> toMpPage(OrderItem ... orderItems) {
        // 1. 分页条件
        Page<T> page = Page.of(pageNo, pageSize);
        // 2. 排序条件
        if (BeanUtil.isNotEmpty(sortBy)) {
            page.addOrder(new OrderItem().setColumn(sortBy).setAsc(isAsc));
        } else if (BeanUtil.isNotEmpty(orderItems)) {
            page.addOrder(orderItems);
        }

        return page;
    }

    public <T> Page<T> toMpPage(String defaultSortBy, Boolean defaultAsc) {
        return toMpPage(new OrderItem().setColumn(defaultSortBy).setAsc(defaultAsc));
    }
    public <T> Page<T> toMpPageDefaultSortByCreateTime() {
        return toMpPage(OrderItem.desc("create_time"));
    }

    public <T> Page<T> toMpPageDefaultSortByUpdateTime() {
        return toMpPage(OrderItem.desc("update_time"));
    }
}
