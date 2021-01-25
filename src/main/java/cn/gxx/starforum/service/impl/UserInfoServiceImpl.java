package cn.gxx.starforum.service.impl;

import cn.gxx.starforum.dao.UserInfoMapper;
import cn.gxx.starforum.entity.UserInfo;
import cn.gxx.starforum.service.UserInfoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo getOneByName(String name) {
        return userInfoMapper.getUserByName(name);
    }

    @Override
    public int signup(UserInfo userInfo) {
        //密码加密
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userInfo.setRegisterTime(new Date());
        return userInfoMapper.insertForSignup(userInfo);
    }

    @Override
    public boolean checkNameExist(String name) {
        return userInfoMapper.countByName(name) > 0 ? false : true;
    }
}
