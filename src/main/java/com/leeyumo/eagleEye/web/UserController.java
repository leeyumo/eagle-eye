package com.leeyumo.eagleEye.web;

import com.leeyumo.adk.userCenter.api.UserApi;
import com.leeyumo.adk.userCenter.invoker.ApiException;
import com.leeyumo.adk.userCenter.model.JsonResultPageUserVO;
import com.leeyumo.adk.userCenter.model.PageRequestWrapper;
import com.leeyumo.adk.userCenter.model.PageUserVO;
import com.leeyumo.common.models.JsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@Api(value = "用户管理", description = "用户管理")
public class UserController extends BaseController {

    @Autowired
    private UserApi userApi;
    /**
     * 获取用户列表
     * @return
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping("/userList")
    @Secured(value = {"ROLE_ADMIN"})
    public JsonResult<PageUserVO> userList(
            @RequestBody PageRequestWrapper wrapper) throws ApiException {
        JsonResultPageUserVO res = userApi.getAllUsers(wrapper);
        return JsonResult.success(res.getResult());

    }

    @ApiOperation(value = "查询用户权限")
    @GetMapping("/authorityList")
    public List<String> authorityList(){
        List<String> authentication = getAuthentication();
        return authentication;
    }

}
