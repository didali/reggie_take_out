package com.dida.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    //当前页数据
    private List<T> records;
    //总数据条数
    private Integer total;
}
