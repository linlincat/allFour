package com.four.m.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 描述:        后端返回给前端的数据格式
 * */

public class CategoryVo {
    private Integer id;
    private String name;
    private Integer type;
    private Integer parentId;
    private Integer orderNum;
    private Date createTime;
    private Date updateTime;

    private List<CategoryVo> childCategroy = new ArrayList<> ();

    public List<CategoryVo> getChildCategroy() {
        return childCategroy;
    }
    public void setChildCategroy(List<CategoryVo> childCategroy) {
        this.childCategroy = childCategroy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
