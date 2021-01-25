package cn.gxx.starforum.service;

import cn.gxx.starforum.entity.UserInfo;

public interface UserInfoService {

    UserInfo getOneByName(String name);
    int signup(UserInfo userInfo);
    boolean checkNameExist(String name);
}
