package com.electric.model.response;

import java.util.List;

/**
 * 分页返回对象
 *
 * @author sunk
 * @date 2024/01/29
 */
public class PageResult<T extends Object> extends CommResponse<Object> {

    private static final long serialVersionUID = 1L;

    /** 返回数据 */
    private List<T> rows;

    /** 总条数 */
    private Integer           total;

    public PageResult() {
    }

    public PageResult(List<T> rows, Integer total) {
        this.rows = rows;
        this.total = total;
    }

    public PageResult(List<T> rows, Long total) {
        this.rows = rows;
        this.total = total.intValue();
    }

    public static <T> PageResult<T> handle(List<T> rows, Integer total) {
        return new PageResult<T>(rows, total);
    }

    public static <T> PageResult<T> handle(List<T> rows, Long total) {
        return new PageResult<T>(rows, total);
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
