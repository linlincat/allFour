package com.four.m.model.request;

import jdk.nashorn.internal.runtime.arrays.IteratorAction;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/*
* 描述:       添加分类
* */
public class AddCategoryReq {
    @Size(min=2, max=5, message = "name长度应该在2-5之间")
    @NotNull(message = "name不能为null")
    private String name;
    @NotNull(message = "type不能为null")
    @Max (value = 3, message = "type不能超过3")
    private Integer type;
    @NotNull(message = "parendId不能为null")
    private Integer parentId;
    @NotNull(message = "orderNum不能为null")
    private Integer orderNum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}
