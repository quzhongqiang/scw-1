package wyg.scw.scwproject.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wyg.scw.common.bean.AppResponse;
import wyg.scw.common.utils.AppUtils;
import wyg.scw.scwproject.bean.TReturn;
import wyg.scw.scwproject.constant.ProjectConstant;
import wyg.scw.scwproject.service.ProjectService;
import wyg.scw.scwproject.utils.OssTemplate;
import wyg.scw.scwproject.vo.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class ProjectCreateController {
    @Autowired
    private OssTemplate ossTemplate;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectService projectService;

    /**
     * 上传图片
     * @param files
     * @return
     */
    @ApiOperation("上传图片")
    @PostMapping("uploadImg")
    public AppResponse uploadImg(MultipartFile[] files){
        if (ArrayUtils.isEmpty(files)) {
            return AppResponse.fail("上传文件为空");
        }
        int failCount = 0;
        int successCount = 0;
        ArrayList<String> imgs = new ArrayList<>();
        for (MultipartFile file : files) {
            if (StringUtils.isEmpty(file)){
                failCount++;
                continue;
            }
            String urlStr = ossTemplate.uploadImg(file);
            successCount++;
            imgs.add(urlStr);
        }
        log.debug("用户上传了{}张图片 , 成功了{}张 , 失败了{}张", files.length,successCount,failCount);
        return AppResponse.ok().data("imgs",imgs);
    }

    /**
     * 初始化发布项目
     * @param accessToken
     * @return
     */
    @ApiOperation("初始化发布项目")
    @PostMapping("initProject")
    public AppResponse initProject(String accessToken){
        //判断是否登录
        UserRespVo userRespVo = AppUtils.getObjectFromRedis(redisTemplate, UserRespVo.class, accessToken);
        if (StringUtils.isEmpty(userRespVo)){
            return AppResponse.fail("请登录后再发布项目");
        }
        //初始化bigVo
        ProjectRedisStorageVo bigVo = new ProjectRedisStorageVo();
        String projectToken = ProjectConstant.TEMP_PROJECT_PREFIX + AppUtils.getUUIDstr();
        bigVo.setProjectToken(projectToken);
        bigVo.setMemberid(userRespVo.getId());
        bigVo.setAccessToken(accessToken);
        //将bigVo存入redis
        AppUtils.setObjectToRedis(redisTemplate,projectToken,bigVo);
        return AppResponse.ok("项目初始化成功");
    }

    /**
     * 创建项目第一步：收集项目基本信息
     * @param projectBaseInfoVo
     * @return
     */
    @ApiOperation("发布项目第一步：收集信息")
    @PostMapping("createProjectStep1")
    public AppResponse createStep1(ProjectBaseInfoVo projectBaseInfoVo){
        //判断用户登录是否超时
        UserRespVo userRespVo = AppUtils.getObjectFromRedis(redisTemplate, UserRespVo.class, projectBaseInfoVo.getAccessToken());
        if (StringUtils.isEmpty(userRespVo)){
            return AppResponse.fail("登录超时,请登录后再发布项目");
        }
        //获取bigVo
        ProjectRedisStorageVo bigVo = AppUtils.getObjectFromRedis(redisTemplate, ProjectRedisStorageVo.class, projectBaseInfoVo.getProjectToken());
        if (StringUtils.isEmpty(bigVo)){
            return AppResponse.fail("获取项目信息失败");
        }
        //拷贝projectBaseInfoVo到bigVo
        log.debug("projectBaseInfoVo:{},bigVo:{}",projectBaseInfoVo,bigVo);
        BeanUtils.copyProperties(projectBaseInfoVo,bigVo);
        log.debug("projectBaseInfoVo:{},bigVo:{}",projectBaseInfoVo,bigVo);
        //将bigVo存入redis
        AppUtils.setObjectToRedis(redisTemplate,projectBaseInfoVo.getProjectToken(),bigVo);
        return AppResponse.ok("收集项目基本完成").data("bigVo",bigVo);
    }

    /**
     * 创建项目第二步：设置回报信息
     * @param returnVos
     * @return
     */
    @PostMapping("createProjectStep2")
    public AppResponse createStep2(@RequestBody List<ProjectReturnVo> returnVos){
        //判断用户是否登录
        if (CollectionUtils.isEmpty(returnVos)){
            return AppResponse.fail("收集项目回报信息失败");
        }
        UserRespVo userRespVo = AppUtils.getObjectFromRedis(redisTemplate, UserRespVo.class, returnVos.get(0).getAccessToken());
        if (StringUtils.isEmpty(userRespVo)){
            return AppResponse.fail("登录超时");
        }
        //获取项目bigVo
        ProjectRedisStorageVo bigVo = AppUtils.getObjectFromRedis(redisTemplate, ProjectRedisStorageVo.class, returnVos.get(0).getProjectToken());
        if (StringUtils.isEmpty(bigVo)){
            return AppResponse.fail("获取项目信息失败");
        }
        //将returnVos封装到bigVo
        List<TReturn> returns = new ArrayList<>();
        for (ProjectReturnVo returnVo : returnVos) {
            TReturn tReturn = new TReturn();
            BeanUtils.copyProperties(returnVo,tReturn);
            returns.add(tReturn);
        }
        bigVo.setProjectReturns(returns);
        AppUtils.setObjectToRedis(redisTemplate,bigVo.getProjectToken(),bigVo);
        return AppResponse.ok().data("bigVo",bigVo);
    }

    @PostMapping("createProjectStep3")
    public AppResponse createStep3(ProjectConfirmInfoVo projectConfirmInfoVo){
        UserRespVo userRespVo = AppUtils.getObjectFromRedis(redisTemplate, UserRespVo.class, projectConfirmInfoVo.getAccessToken());
        if (StringUtils.isEmpty(userRespVo)){
            return AppResponse.fail("登录超时");
        }
        ProjectRedisStorageVo bigVo = AppUtils.getObjectFromRedis(redisTemplate, ProjectRedisStorageVo.class, projectConfirmInfoVo.getProjectToken());
        if (StringUtils.isEmpty(bigVo)){
            return AppResponse.fail("获取项目信息失败");
        }
        bigVo.getProjectInitiator().setAccount(projectConfirmInfoVo.getAccount());
        bigVo.getProjectInitiator().setIdcard(projectConfirmInfoVo.getIdcard());
        if (projectConfirmInfoVo.getType()==0){
            String jsonString = JSON.toJSONString(bigVo);
            redisTemplate.opsForValue().set(projectConfirmInfoVo.getProjectToken(),jsonString,7, TimeUnit.DAYS);
            return AppResponse.ok("保存草稿,有效期为7天");
        }else {
            projectService.createProject(bigVo);
            return AppResponse.ok("创建项目成功");
        }
    }
}
