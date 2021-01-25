package cn.gxx.starforum.common;

public class ResultModel {
    /**
     * 返回码
     */
    private int code;

    /**
     * 返回结果描述
     */
    private String message;

    /**
     * 返回内容
     */
    private Object content;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultModel(ResultCodeEnum status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = "";
    }

    public ResultModel(ResultCodeEnum status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ResultCodeEnum.SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultCodeEnum.SUCCESS);
    }

    public static ResultModel error(ResultCodeEnum error) {
        return new ResultModel(error);
    }
}
