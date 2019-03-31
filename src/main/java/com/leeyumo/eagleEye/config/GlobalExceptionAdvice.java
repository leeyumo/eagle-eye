package com.leeyumo.eagleEye.config;


import com.leeyumo.adk.learningSpring.invoker.ApiException;
import com.leeyumo.common.constants.BaseCodeMsg;
import com.leeyumo.common.exception.BusinessException;
import com.leeyumo.common.models.JsonResult;
import com.leeyumo.eagleEye.constants.EagleEyeCodeMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionAdvice.class);
    @ExceptionHandler(AccessDeniedException.class)
    public JsonResult handleAccessDeniedException(AccessDeniedException e){
        return JsonResult.fail("拒绝访问，缺少相关权限");
    }

    @ExceptionHandler(AuthenticationException.class)
    public JsonResult handleAuthenticationException(AuthenticationException e){
        return JsonResult.res(EagleEyeCodeMsg.AuthFailure,e.getMessage());
    }


    @ExceptionHandler(BusinessException.class)
    public JsonResult handleBusinessException(BusinessException e){
        return JsonResult.res(e.getMsg(),e.getData());
    }

    @ExceptionHandler(Exception.class)
    public JsonResult handleException(Exception e){
        logger.error("系统异常",e);
        return JsonResult.fail(BaseCodeMsg.SystemError);
    }

    @ExceptionHandler({
            com.leeyumo.adk.learningSpring.invoker.ApiException.class,
            com.leeyumo.adk.userCenter.invoker.ApiException.class})
    public JsonResult handleLsApiException(ApiException e){
        logger.error("adk请求异常",e);
        return JsonResult.fail(EagleEyeCodeMsg.requestFail);
    }


}
