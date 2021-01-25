package cn.gxx.starforum.security;

import com.alibaba.fastjson.JSON;
import cn.gxx.starforum.common.ResultCodeEnum;
import cn.gxx.starforum.common.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @description:
 * @author: Gxx
 * @time: 2021-01-07 17:37
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new ResponseEntity<>(new ResultModel(ResultCodeEnum.SERVER_ERROR), HttpStatus.INTERNAL_SERVER_ERROR)));
    }
}
