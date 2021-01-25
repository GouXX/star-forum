package cn.gxx.starforum.controller;

import cn.gxx.starforum.common.ResultCodeEnum;
import cn.gxx.starforum.common.ResultModel;
import cn.gxx.starforum.entity.UserInfo;
import cn.gxx.starforum.entity.validate.singup;
import cn.gxx.starforum.util.RedisUtil;
import cn.gxx.starforum.service.ISequencesService;
import cn.gxx.starforum.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api(value = "用户接口")
@RestController
@RequestMapping("user")
public class UserInfoController {

    @Resource
    private ISequencesService sequencesService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private RedisUtil redisUtil;

    @PostMapping(value = "/signup")
    @ApiOperation(value = "用户注册")
    public ResponseEntity<ResultModel> singup(@RequestBody @Validated({singup.class}) UserInfo user){
        //验证用户名是email或者手机号码
        String eLNamePhone = "^1[3584]\\d{9}$";
        String eLNameEmail = "^\\w+@[a-zA-Z0-9]{2,10}(?:\\.[a-z]{2,4}){1,3}$";
        boolean checkName = false;
        //密码规则:8～16位 包含数字和字母
        String eLPass = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,16}$";
        Pattern p = Pattern.compile(eLNamePhone);
        Matcher m = p.matcher(user.getUsername());
        if (m.matches()) {
            checkName = true;
            user.setSignupType(0);
        }
        p = Pattern.compile(eLNameEmail);
        m = p.matcher(user.getUsername());
        if (m.matches()){
            checkName = true;
            user.setSignupType(1);
        }
        if (!checkName){
            return new ResponseEntity<>(
                    new ResultModel(ResultCodeEnum.USER_NAME_WRONG), HttpStatus.OK);
        }
        p = Pattern.compile(eLPass);
        m = p.matcher(user.getPassword());
        if (!m.matches()) {
            return new ResponseEntity<>(
                    new ResultModel(ResultCodeEnum.USER_PWS_WRONG), HttpStatus.OK);
        }
        try {
            //判断用户账号是否已经注册
            if (!userInfoService.checkNameExist(user.getUsername()))
                return new ResponseEntity<>(
                        new ResultModel(ResultCodeEnum.USER_NAME_EXIST), HttpStatus.OK);
            //获取用户编号序列值
            Integer useq = sequencesService.getSeqAndIncBykey("user_num");
            user.setuNumber("U"+String.format("%09d", useq));
            user.setNickName(user.getUsername());
            Integer res = userInfoService.signup(user);
            if (res > 0){
                return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SUCCESS), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(value = "/{userName}/userInfo")
    @ApiOperation(value = "获取单个用户信息")
    public ResponseEntity<ResultModel> getUser(@PathVariable("userName") @NotBlank(message = "用户名不能为空") String userName){
        try {
            UserInfo user = userInfoService.getOneByName(userName);
            if(user != null){
                user.setId(null);
                user.setPassword(null);
                user.setSignupType(null);
                return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SUCCESS, user), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ResultModel(ResultCodeEnum.NOT_FOUND), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "获取当前用户信息")
    @GetMapping("/getCurrUserInfo")
    public ResponseEntity<ResultModel> getCurrentUserInfo(){
        try {
            String principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
            UserInfo user = (UserInfo) redisUtil.get(principal);
            if(user != null){
                user.setId(null);
                user.setPassword(null);
                user.setSignupType(null);
                return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SUCCESS, user), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ResultModel(ResultCodeEnum.NOT_FOUND), HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ResultModel(ResultCodeEnum.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
