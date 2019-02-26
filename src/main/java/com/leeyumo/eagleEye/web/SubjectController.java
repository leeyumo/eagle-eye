package com.leeyumo.eagleEye.web;

import com.leeyumo.adk.learningSpring.api.SubjectApi;
import com.leeyumo.adk.learningSpring.invoker.ApiException;
import com.leeyumo.adk.learningSpring.model.*;
import com.leeyumo.common.constants.BaseCodeMsg;
import com.leeyumo.common.models.JsonResult;
import com.leeyumo.eagleEye.constants.EagleEyeCodeMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "subject")
@Api(description = "科目接口")
public class SubjectController {
    @Autowired
    private SubjectApi subjectApi;

    @PostMapping(value = "getSubjectsByPage")
    @ApiOperation(value = "科目分页")
    public JsonResult<PageSubjectVO> getSubjectsByPage(@RequestBody PageRequestWrapper wrapper) throws ApiException{
            JsonResultPageSubjectVO res = subjectApi.getSubjectsByPage(wrapper);
            if(Objects.equals(res.getCode(), BaseCodeMsg.Success.getCode())){
                return JsonResult.success(res.getResult());
            }else {
                return JsonResult.fail(res.getMsg());
            }
    }

    @GetMapping(value = "findById/{id}")
    @ApiOperation(value = "查找单个")
    public JsonResult<Subject> findById(@PathVariable Long id) throws ApiException{
            JsonResultSubject res = subjectApi.findByPhase(id);
            if(Objects.equals(res.getCode(), BaseCodeMsg.Success.getCode())){
                return JsonResult.success(res.getResult());
            }else {
                return JsonResult.fail(res.getMsg());
            }
    }

    @PostMapping(value = "createSubject")
    @ApiOperation(value = "创建科目")
    public JsonResult<Boolean> createSubject(@RequestBody SubjectCreator req) throws ApiException{
            JsonResultBoolean res = subjectApi.createSubject(req);
            if(Objects.equals(res.getCode(), BaseCodeMsg.Success.getCode())){
                return JsonResult.success(true);
            }else {
                return JsonResult.fail(res.getMsg());
            }
    }

    @PostMapping(value = "updateSubject/{id}")
    @ApiOperation(value = "更新科目")
    public JsonResult<Boolean> updateSubject(@RequestBody SubjectUpdater req,@PathVariable Long id) throws ApiException{
            JsonResultBoolean res = subjectApi.updateSubject(id,req);
            if(Objects.equals(res.getCode(), BaseCodeMsg.Success.getCode())){
                return JsonResult.success(true);
            }else {
                return JsonResult.fail(res.getMsg());
            }
    }
}
