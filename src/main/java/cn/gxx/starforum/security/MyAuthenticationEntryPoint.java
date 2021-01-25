package cn.gxx.starforum.security;

import com.alibaba.fastjson.JSON;
import cn.gxx.starforum.common.ResultCodeEnum;
import cn.gxx.starforum.common.ResultModel;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Gxx
 * @time: 2021-01-07 15:44
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultModel resultModel = makeResponse(e);
        if (resultModel != null){
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.getWriter().write(JSON.toJSONString(resultModel));
        }else{
            httpServletResponse.getWriter().write("login failed:" + e.getMessage());
        }

    }

    private ResultModel makeResponse(AuthenticationException e){
        if (e instanceof BadCredentialsException){
            return new ResultModel(ResultCodeEnum.USER_LOGIN_FAIL);
        }
        return null;
    }
}
