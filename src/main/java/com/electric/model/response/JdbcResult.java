package com.electric.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * jdbc执行返回结果类
 *
 * @author sunk
 * @date 2026/3/31
 */
@Getter
@Setter
public class JdbcResult {

    /**执行结果*/
    private Boolean status;

    /**执行失败后的错误消息*/
    private String  errorMsg;

    /** 影响行数 */
    private Integer rows;

    public JdbcResult() {

    }

    public JdbcResult(Boolean status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }

    public JdbcResult(Boolean status, Integer rows, String errorMsg) {
        this.status = status;
        this.rows = rows;
        this.errorMsg = errorMsg;
    }

    /**
     * 成功
     *
     * @return
     * @create  2021年10月12日 上午11:04:23 luochao
     * @history
     */
    public static JdbcResult success() {
        return new JdbcResult(true, "");
    }

    /**
     * 成功
     *
     * @param rows
     * @return
     * @create  2022年6月27日 下午2:47:36 luochao
     * @history
     */
    public static JdbcResult success(Integer rows) {
        return new JdbcResult(true, rows, "");
    }

    /**
     * 失败
     *
     * @param errorMsg
     * @return
     * @create  2021年10月12日 上午11:04:56 luochao
     * @history
     */
    public static JdbcResult fail(String errorMsg) {
        return new JdbcResult(false, errorMsg);
    }
}
