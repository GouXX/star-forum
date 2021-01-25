package cn.gxx.starforum.dao;

import cn.gxx.starforum.entity.UserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoMapper {

    UserInfo getUserByName(String name);
    Integer insertForSignup(UserInfo user);
    Integer countByName(String name);
    List<String> getRolesByUserName(String uname);

}
