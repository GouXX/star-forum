package cn.gxx.starforum.common;

public enum ResultCodeEnum {
    /*** 通用部分 100 - 599***/
    // 成功请求
    SUCCESS("00000", "successful"),
    // 重定向
    REDIRECT("301", "redirect"),
    // 资源未找到
    NOT_FOUND("404", "not found"),
    // 服务器错误
    SERVER_ERROR("500","server error"),
    //非法请求
    ILLEGAL_REQUEST("40600", "illegal request"),

    /*** 这里可以根据不同模块用不同的区级分开错误码，例如:  ***/

    // 1000～1999 区间表示用户模块错误
    USER_NAME_WRONG("1001", "用户名错误"),
    USER_PWS_WRONG("1002", "密码不符合规则（8～16位包含字母和数字）"),
    USER_NAME_EXIST("1003", "账号已注册"),
    // 2000～2999 区间表示订单模块错误
    // 3000～3999 区间表示商品模块错误
    // 40100~40199 身份验证
    USER_LOGIN_FAIL("40101", "用户名或密码错误");

    /**
     * 响应状态码
     */
    private String code;
    /**
     * 响应信息
     */
    private String message;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
