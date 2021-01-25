package cn.gxx.starforum.handler;

import cn.gxx.starforum.common.ResultCodeEnum;
import cn.gxx.starforum.common.ResultModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 控制器通知处理类
 * @author: Gxx
 * @time: 2020-03-26 15:19
 */
@ControllerAdvice
public class AppWideExceptionHandler {

    /**
     * @description: 参数错误异常统一处理
     * @author: Gxx
     * @time: 2020/3/26 5:46 下午
     */       
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultModel> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        System.out.println("===================methodArgumentNotValidExceptionHandler Occur============");
        List<Map<String, String>> argumentsInvalidResponseBeanList = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            Map<String, String> map = new HashMap<>();
            map.put("argumentName", error.getField());
            map.put("msg", error.getDefaultMessage());

            argumentsInvalidResponseBeanList.add(map);
        }
        ResultModel result = new ResultModel(ResultCodeEnum.ILLEGAL_REQUEST, argumentsInvalidResponseBeanList);
        return new ResponseEntity<>(result, HttpStatus.NOT_ACCEPTABLE);
    }
}
