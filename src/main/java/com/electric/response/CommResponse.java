package com.electric.response;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.electric.constant.Numbers;
import com.electric.exception.BaseException;

/**
 * 相应格式
 *
 * @author sunk
 * @date 2024/01/29
 */
public class CommResponse<T extends Object> implements Serializable {

    private static final long serialVersionUID = 5889506030158595576L;


    /**
     * 错误返回结果msg
     */
    public static final String RESULT_MSG_FAILURE = "系统开小差,稍后再试";

    /**
     * 正确返回结果msg
     */
    public static final String RESULT_MSG_SUCCESS = "操作成功";

    /**
     * 正确返回结果
     */
    public static final String RESULT_SUCCESS     = "SUCCESS";

    /**
     * 返回状态
     *
     * @see StatusCodeEnum
     */
    private Integer            statusCode;

    /**
     * 返回消息
     */
    private String             message;

    /**
     * 业务code
     */
    private String             bizCode;

    /**
     * 返回内容
     */
    private T                  data;

    /**
     * 消息展现方式，如果为空则默认toast，否则0表示toast，1表示新页面
     */
    private Integer            alertType;

    /**
     * 此属性没有使用，主要是为了解决@Cacheable 序列化问题(isSuccess)
     */
    private Boolean            success;

    public enum StatusCodeEnum {
        //成功
        OK(0),
        //失败
        FAIL(-1),
        //限流
        CURRENT_LIMITING(111),
        //H5自动开一卡通账户，一卡通账户不存在
        H5_OPEN_CARD_NO_EXIST(104),
        // 用户发出的请求有错误，服务器没有进行新建或修改数据的操作
        INVALID_REQUEST(203),
        //登录失效
        INVALID_LOGIN(204),
        //设备更换
        DEVICE_CHANGED(205),
        //服务不可用
        SERVICE_DISABLE(206),
        //一卡通未激活无法使用支付功能
        ONECARD_NO_ACTIVITY(207),
        //学校不能为空异常码
        SCHOOL_NOT_NULL(208),
        //未绑定一卡通
        ONE_CARD_NO_BIND(209),
        //一卡通被别人绑定
        ONECARD_OCCUPIED(210),
        //指纹识别fingerprint recognition
        FINGERPRINT_RECOGNITION(211),
        //手势识别gesture recognition
        GESTURE_RECOGNITION(212),
        //通过验证码注册的用户,补全密码
        NOT_LOGIN_PASSWORD(213),
        //Unionid校验失败
        UNIONID_CHECK_FAIL(214),
        //APP版本过低
        VERSION_TOO_LOW(215),
        //上传文件过大
        MAX_UPLOAD_SIZE(216),
        //错误的HttpMediaType
        HTTP_MEDIA_TYPE_ERROR(217),
        //不在服务时间内
        SERVICE_TIME_OUT(300),
        //APP自动开一卡通账户，一卡通账户不存在
        APP_OPEN_CARD_NO_EXIST(304),
        /**不支持的请求类型**/
        NOT_ACCEPTABLE(406),

        //身份证过期
        IDCARD_EXPIRED(200811),
        //身份证即将过期
        IDCARD_TO_EXPIRE(200812),
        //INTERNAL_SERVER_ERROR(500),   // 服务器发生错误
        ;

        Integer                    code;

        public static final Set<Integer> SET = new HashSet<Integer>(Numbers.INT_64);
        static {
            for (StatusCodeEnum statusCodeEnum : StatusCodeEnum.values()) {
                SET.add(statusCodeEnum.getCode());
            }
        }

        StatusCodeEnum(Integer code) {
            this.code = code;
        }

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }
    }

    public CommResponse() {
        this.statusCode = StatusCodeEnum.OK.getCode();
    }

    public CommResponse(T data) {
        this.statusCode = StatusCodeEnum.OK.getCode();
        this.data = data;
    }

    /**
     *
     * @param statusCode 状态码
     * @param message 消息内容
     */
    public CommResponse(StatusCodeEnum statusCode, String message) {
        if (statusCode != null) {
            this.statusCode = statusCode.getCode();
        }
        this.message = message;
    }

    /**
     *
     * @param data 数据
     * @param statusCode 状态码
     * @param message 消息内容
     */
    public CommResponse(T data, StatusCodeEnum statusCode, String message) {
        this.data = data;
        if (statusCode != null) {
            this.statusCode = statusCode.getCode();
        }
        this.message = message;
    }

    /**
     * 返回成功，无数据
     *
     * @param <T>
     * @return
     * @create  2020年4月28日 上午11:29:25 luochao
     * @history
     */
    public static <T> CommResponse<T> success() {
        CommResponse<T> response = new CommResponse<T>();
        response.setStatusCode(StatusCodeEnum.OK.getCode());
        response.setMessage(RESULT_MSG_SUCCESS);
        return response;
    }

    public static <T> CommResponse<T> success(T data) {
        CommResponse<T> response = new CommResponse<T>(data);
        response.setStatusCode(StatusCodeEnum.OK.getCode());
        response.setMessage(RESULT_MSG_SUCCESS);
        return response;
    }

    /**
     * 返回成功有数据
     *
     * @param <T>
     * @param data
     * @return
     * @create  2020年4月28日 上午11:29:45 luochao
     * @history
     */
    public static <T> CommResponse<T> success(T data, StatusCodeEnum statusCode, String message) {
        CommResponse<T> response = new CommResponse<T>(data);
        response.setStatusCode(statusCode.getCode());
        response.setMessage(RESULT_MSG_SUCCESS);
        return response;
    }

    /**
     * 返回失败,有消息内容
     *
     * @param message
     * @return
     * @create  2020年4月28日 上午11:30:01 luochao
     * @history
     */
    public static CommResponse<String> failure(String message) {
        CommResponse<String> response = new CommResponse<String>();
        response.setMessage(message);
        Integer code = StatusCodeEnum.FAIL.getCode();
        response.setStatusCode(code);
        response.setBizCode(code.toString());
        return response;
    }

    public static <T> CommResponse<T> failure(String msg, String bizCode) {
        CommResponse<T> response = new CommResponse<T>();
        response.setStatusCode(StatusCodeEnum.FAIL.getCode());
        response.setBizCode(bizCode);
        response.setMessage(msg);
        return response;
    }

    /**
     * 返回失败，有消息内容，有状态码
     *
     * @param message
     * @param statusCode
     * @return
     * @create  2020年4月28日 上午11:30:17 luochao
     * @history
     */
    public static CommResponse<String> failure(String message, StatusCodeEnum statusCode) {
        CommResponse<String> response = new CommResponse<String>();
        response.setMessage(message);
        Integer code = statusCode.getCode();
        response.setStatusCode(code);
        response.setBizCode(code.toString());
        return response;
    }

    public static CommResponse<String> failure(String message, StatusCodeEnum statusCode, String bizCode) {
        CommResponse<String> response = new CommResponse<String>();
        response.setMessage(message);
        Integer code = statusCode.getCode();
        response.setStatusCode(code);
        response.setBizCode(bizCode);
        return response;
    }

    /**
     * 返回失败,有异常信息，有状态码
     *
     * @param <T>
     * @param e
     * @return
     * @create  2020年4月28日 上午11:33:44 luochao
     * @history
     */
    public static <T> CommResponse<T> failure(BaseException e) {
        CommResponse<T> response = new CommResponse<T>();
        if (e != null) {
            response.setStatusCode(StatusCodeEnum.FAIL.getCode());
            response.setBizCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public static <T> CommResponse<T> failure(BaseException e, StatusCodeEnum statusCode) {
        CommResponse<T> response = new CommResponse<T>();
        if (e != null) {
            response.setStatusCode(statusCode.getCode());
            response.setBizCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    /*public static <T> CommResponse<T> failure(BaseException e, String bizCode) {
        CommResponse<T> response = new CommResponse<T>();
        if (e != null) {
            response.setStatusCode(StatusCodeEnum.FAIL.getCode());
            response.setBizCode(bizCode);
            response.setMessage(e.getMessage());
        }
        return response;
    }*/

    /**
     * 功能说明：返回一个请求失败的响应模板
     *
     * @param e 异常参数不能为空
     * @param statusCode 异常码
     * @param alertType 消息展现方式
     * @return CommResponse
     */
    public static <T> CommResponse<T> failure(BaseException e, StatusCodeEnum statusCode, Integer alertType) {
        CommResponse<T> response = new CommResponse<>();
        if (e != null) {
            response.setStatusCode(statusCode.getCode());
            response.setBizCode(e.getErrorCode());
            response.setMessage(e.getMessage());
        }
        response.setAlertType(alertType);
        return response;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return statusCode != null && StatusCodeEnum.OK.getCode().equals(statusCode);
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public Integer getAlertType() {
        return alertType;
    }

    public void setAlertType(Integer alertType) {
        this.alertType = alertType;
    }
}
