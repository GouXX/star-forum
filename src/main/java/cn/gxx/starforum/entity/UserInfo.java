package cn.gxx.starforum.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import cn.gxx.starforum.entity.validate.singup;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class UserInfo {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer id; //用户ID

    private String uNumber;    //用户编号

    @NotBlank(groups = {singup.class},message = "用户名不能为空")
    private String username;    //用户名

    private String nickName;    //昵称

    private String realName;   //真实姓名

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(groups = {singup.class},message = "密码不能为空")
    private String password;    //密码

    private String mPhone; //移动电话

//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8" )
    private Date registerTime; //注册时间

    private String email;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer signupType; //注册类型

    public Integer getSignupType() {
        return signupType;
    }

    public void setSignupType(Integer signupType) {
        this.signupType = signupType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getuNumber() {
        return uNumber;
    }

    public void setuNumber(String uNumber) {
        this.uNumber = uNumber;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
