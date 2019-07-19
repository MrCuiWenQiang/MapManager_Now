package io.renren.modules.code.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.code.entity.CodeRelationEntity;
import io.renren.modules.code.service.CodeRelationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 注册码关系表
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 09:58:05
 */
@RestController
@RequestMapping("sys/coderelation")
public class CodeRelationController {
    @Autowired
    private CodeRelationService codeRelationService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:coderelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = codeRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:coderelation:info")
    public R info(@PathVariable("id") Integer id){
        CodeRelationEntity codeRelation = codeRelationService.getById(id);

        return R.ok().put("codeRelation", codeRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:coderelation:save")
    public R save(@RequestBody CodeRelationEntity codeRelation){
        codeRelationService.save(codeRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:coderelation:update")
    public R update(@RequestBody CodeRelationEntity codeRelation){
        ValidatorUtils.validateEntity(codeRelation);
        codeRelationService.updateById(codeRelation);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:coderelation:delete")
    public R delete(@RequestBody Integer[] ids){
        codeRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
