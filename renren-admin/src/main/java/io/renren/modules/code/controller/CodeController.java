package io.renren.modules.code.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.code.entity.ApplyEntity;
import io.renren.modules.code.entity.CodeEntity;
import io.renren.modules.code.entity.StatuEntity;
import io.renren.modules.code.service.CodeService;
import io.renren.modules.sys.entity.SysDictEntity;
import io.renren.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
@RestController
@RequestMapping("sys/code")
public class CodeController {
    @Autowired
    private CodeService codeService;

    @Autowired
    private SysDictService sysDictService;

    @RequestMapping("/getStatus")
    public R status(@RequestParam Map<String, Object> params){
        List<SysDictEntity> status = sysDictService.queryByType("codestatus");

        List<SysDictEntity> timers = sysDictService.queryByType("addtimer");

        R r = R.ok();
        r.put("status",status);
        r.put("timers",timers);
        return r;
    }
    @RequestMapping(value = "/apply",method = RequestMethod.POST)
    public R apply(@RequestBody ApplyEntity applyData){
        R r = codeService.apply(applyData);
        return r;
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:code:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = codeService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:code:info")
    public R info(@PathVariable("id") Integer id){
        CodeEntity code = codeService.getById(id);

        return R.ok().put("code", code);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:code:save")
    public R save(@RequestBody CodeEntity code){
        codeService.save(code);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:code:update")
    public R update(@RequestBody CodeEntity code){
        ValidatorUtils.validateEntity(code);
        codeService.updateById(code);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:code:delete")
    public R delete(@RequestBody Integer[] ids){
        codeService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
