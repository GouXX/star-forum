package cn.gxx.starforum.security;

import cn.gxx.starforum.dao.UserInfoMapper;
import cn.gxx.starforum.entity.UserInfo;
import cn.gxx.starforum.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Gxx
 * @time: 2020-04-01 22:30
 */
@Service
public class MyUserDetailsServiceImpl implements UserDetailsService {

    private UserInfoMapper userMapper;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 通过构造器注入UserRepository
     * @param userMapper
     */
    public MyUserDetailsServiceImpl(UserInfoMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo user = userMapper.getUserByName(username);
        if(user == null){
            throw new UsernameNotFoundException(username);
        }
        List<String> roles = userMapper.getRolesByUserName(username);
        List<GrantedAuthority> authorities = roles.stream().filter(r -> r != null).map(r -> new SimpleGrantedAuthority(r)).collect(Collectors.toList());
        //存储用户信息到redis
        if(redisUtil.hasKey(username)){
            redisUtil.del(username);
        }
        redisUtil.set(user.getUsername(),user,1000*60*30);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
