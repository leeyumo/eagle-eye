package com.leeyumo.eagleEye.web;

import com.leeyumo.adk.userCenter.api.UserApi;
import com.leeyumo.adk.userCenter.invoker.ApiException;
import com.leeyumo.adk.userCenter.model.JsonResultBoolean;
import com.leeyumo.adk.userCenter.model.UserCreator;
import com.leeyumo.common.constants.BaseCodeMsg;
import com.leeyumo.common.exception.BusinessException;
import com.leeyumo.common.models.JsonResult;
import com.leeyumo.eagleEye.constants.EagleEyeCodeMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/users")
@Api(value = "注册管理", description = "注册管理")
public class RegisterController{

    @Autowired
    private UserApi userApi;

    /**
     * 注册用户 默认开启白名单
     * @param creator
     */
    @ApiOperation(value = "注册用户")
    @PostMapping("/signUp")
    public JsonResult<Boolean> signUp(@RequestBody UserCreator creator){
        try {
            /*user.setPassword(DigestUtils.md5DigestAsHex((user.getPassword()).getBytes()));*/
//            creator.setPassword(bCryptPasswordEncoder.encode(creator.getPassword()));
            JsonResultBoolean res = userApi.save(creator);
            if(Objects.equals(res.getCode(), BaseCodeMsg.Success.getCode())){
                return JsonResult.success(true);
            }else {
                return JsonResult.fail(res.getMsg());
            }
        } catch (ApiException e) {
            e.printStackTrace();
            throw new BusinessException(EagleEyeCodeMsg.requestFail);
        }
    }

}
