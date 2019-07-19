package io.renren.modules.code.service.impl;

import io.renren.common.utils.DateUtils;
import io.renren.common.utils.R;
import io.renren.modules.code.dao.CodeDao;
import io.renren.modules.code.entity.ApplyEntity;
import io.renren.modules.code.entity.CodeEntity;
import io.renren.modules.code.entity.CodeRelationEntity;
import io.renren.modules.code.entity.request.RegisterInfoBean;
import io.renren.modules.code.service.CodeRelationService;
import io.renren.modules.code.service.CodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import org.springframework.transaction.annotation.Transactional;


@Service("codeService")
public class CodeServiceImpl extends ServiceImpl<CodeDao, CodeEntity> implements CodeService {

    @Autowired
    CodeRelationService relationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CodeEntity> page = this.page(
                new Query<CodeEntity>().getPage(params),
                new QueryWrapper<CodeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R add(RegisterInfoBean data) {
        if (data.getType() == 1) {//1注册码注册
            String code = data.getCode();
            if (StringUtils.isEmpty(code)) {
                return R.error("请填写注册码");
            }
            if (code.length() < 13) {
                return R.error("注册码格式错误");
            }
            QueryWrapper wrapper = new QueryWrapper<CodeEntity>().eq("code", code);
            CodeEntity entity = baseMapper.selectOne(wrapper);
            if (entity == null) {
                return R.error("注册码不存在");
            }
            if (entity.getStatus() == 2) {
                return R.error("注册码审核中");

            } else if (entity.getStatus() == 3) {
                return R.error("注册码审核失败");

            } else if (entity.getStatus() == 1) {
                return R.error("注册码已停用");

            } else if (entity.getStatus() == 0) {
                CodeRelationEntity relation = new CodeRelationEntity();
                relation.setStatus(0);
                relation.setCode(entity.getCode());
                relation.setAndroiduuid(data.getAndroidUUID());
                relation.setLogintimer(new Date());
                relation.setCreatetimer(new Date());
                int status = relationService.add(relation);

                if (status != 1) {
                    return R.error("注册失败");
                } else {
                    return R.ok("注册成功");
                }
            } else {
                return R.error("状态异常");
            }
        } else if (data.getType() == 2) {//验证信息注册
            // TODO: 2019/7/16 UUID设置短些
            String uuid = UUID.randomUUID().toString();
            CodeEntity codeEntity = new CodeEntity();
            BeanUtils.copyProperties(data, codeEntity);
            codeEntity.setCreatetimer(new Date());
            codeEntity.setStatus(2);
            codeEntity.setCode(uuid);
            baseMapper.insert(codeEntity);

            CodeRelationEntity relation = new CodeRelationEntity();
            relation.setStatus(0);
            relation.setAndroiduuid(data.getAndroidUUID());
            relation.setCode(uuid);
            relation.setCreatetimer(new Date());
            int status = relationService.add(relation);

            if (status != 1) {
                return R.error("提交失败");
            } else {
                return R.ok_api("提交成功,请等待后台审核");
            }
        }
        return R.error("状态异常");
    }

    @Override
    @Transactional
    public CodeEntity selectByCode(String code) {
        QueryWrapper wrapper = new QueryWrapper<>();
        wrapper.eq("code", code);
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public R apply(ApplyEntity applyEntity) {
        CodeEntity codeEntity = baseMapper.selectById(applyEntity.getId());
        CodeRelationEntity codeRelationEntity = relationService.queryForCode(codeEntity.getCode());

        if (codeEntity == null || codeRelationEntity == null) {
            return R.error("审核信息不存在");
        }

        int status = -1;
        try {
            status = Integer.valueOf(applyEntity.getStatus());
        } catch (Exception e) {
            System.err.println(e);
            status = -1;
        }

        if (status != -1) {
            codeEntity.setStatus(status);
            if (status == 0) {
                codeRelationEntity.setStatus(0);
            } else {
                codeRelationEntity.setStatus(1);
            }
        }

        Date createDate = codeRelationEntity.getCreatetimer();

        String u_timer = applyEntity.getExt();
        String value = null;
        Date updateDate = null;
        try {
            if (u_timer.contains("Y")) {
                value = u_timer.split("Y")[0];
                updateDate = DateUtils.addDateYears(createDate, Integer.valueOf(value));
            } else if (u_timer.contains("M")) {
                value = u_timer.split("M")[0];
                updateDate = DateUtils.addDateMonths(createDate, Integer.valueOf(value));
            } else if (u_timer.contains("D")) {
                value = u_timer.split("D")[0];
                updateDate = DateUtils.addDateDays(createDate, Integer.valueOf(value));
            }
        } catch (Exception e) {
            System.err.println(e);
        }

        if (value == null || updateDate == null) {
            return R.error("有效期错误");
        }

        codeEntity.setExpTimer(updateDate);
        codeEntity.setUpdatetimer(new Date());

        baseMapper.updateById(codeEntity);
        relationService.getBaseMapper().updateById(codeRelationEntity);
        return R.ok("审核成功");
    }

}
