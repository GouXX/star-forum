package cn.gxx.starforum.security.filter;


import cn.gxx.starforum.common.ResultCodeEnum;
import cn.gxx.starforum.common.ResultModel;
import cn.gxx.starforum.entity.UserInfo;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 用户登录验证产生Token
 * @author: Gxx
 * @time: 2020-12-24 16:40
 */
public class UsernamePwdTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationManager authenticationManager;

    public UsernamePwdTokenAuthenticationFilter(AuthenticationManager authenticationManager){
        //调用父类构造设置拦截路径
        super(new AntPathRequestMatcher("/user/login", "POST"));
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        if (!httpServletRequest.getMethod().equals("POST")){
            throw new AuthenticationServiceException("Authentication method not supported: " + httpServletRequest.getMethod());
        }else{
            UserInfo user = new ObjectMapper()
                    .readValue(httpServletRequest.getInputStream(), UserInfo.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        }
    }

    //用户信息验证通过后调用，产生token并添加到响应头中
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        Map<String, Object> clMap = new HashMap<>();
        String astr = authResult.getAuthorities().toString();
        clMap.put("roles", astr.substring(1, astr.length()-1).replace(" ", ""));
        String token = Jwts.builder()
                .setClaims(clMap)
                .setSubject(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        response.addHeader("Authorization", "Bearer " + token);
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(ResultModel.ok()));
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        ResultModel resultModel = new ResultModel("50000", failed.getMessage());
        if (failed instanceof BadCredentialsException){
            response.setStatus(200);
            resultModel = new ResultModel(ResultCodeEnum.USER_LOGIN_FAIL);
        }else{

        }
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(resultModel));
    }

}
