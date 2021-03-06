package cn.hgxsp.service;

import cn.hgxsp.beans.PageQuery;
import cn.hgxsp.beans.PageResult;
import cn.hgxsp.common.RequestHolder;
import cn.hgxsp.dao.SysUserMapper;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysUser;
import cn.hgxsp.param.UserParam;
import cn.hgxsp.util.*;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;
import java.util.Date;
import java.util.List;

/**
 * DESC：用户service类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2019/3/31
 * Time : 21:51
 */
@Service
public class SysUserService {

    @Resource
    SysUserMapper sysUserMapper ;

    public void save(UserParam userParam){

        BeanValidator.check(userParam);
        if(checkEmailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已经被占用");
        }

        if(checkTelPhoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("电话已经被占用");
        }


        String password = PasswordUtil.randowPassword();

        //TODO
        password = "123456";

        String encryptedPassword = MD5Util.encrypt(password) ;


        SysUser sysUser = SysUser.builder()
                .username(userParam.getUsername())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .password(encryptedPassword)
                .telephone(userParam.getTelephone())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();

        sysUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        sysUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        sysUser.setOperateTime(new Date());

        //TODO 发送Email

        sysUserMapper.insertSelective(sysUser) ;
    }

    public void update(UserParam userParam){

        BeanValidator.check(userParam);
        if(checkEmailExist(userParam.getMail(), userParam.getId())){
            throw new ParamException("邮箱已经被占用");
        }

        if(checkTelPhoneExist(userParam.getTelephone(), userParam.getId())){
            throw new ParamException("电话已经被占用");
        }

        SysUser beforeUser = sysUserMapper.selectByPrimaryKey(userParam.getId());
        Preconditions.checkNotNull(beforeUser, "待更新用户不存在");

        SysUser afterUser =   SysUser.builder()
                .id(userParam.getId())
                .username(userParam.getUsername())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .telephone(userParam.getTelephone())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();


        afterUser.setOperator(RequestHolder.getCurrentUser().getUsername());
        afterUser.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        afterUser.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(afterUser) ;
    }


    public SysUser findByKeyWord(String key){
        return sysUserMapper.findByKeyWord(key)  ;
    }


    //验证邮箱是否存在
    public boolean checkEmailExist(String mail , Integer id){
        return sysUserMapper.countByMail(mail ,id) > 0 ;
}

    //验证邮箱是否存在
    public boolean checkTelPhoneExist(String telPhone , Integer id){

        return sysUserMapper.countByTelephone(telPhone , id) > 0 ;
    }

    public PageResult<SysUser> getPageByDeptId(int deptId , PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId) ;
        if(count > 0 ){
            List<SysUser> pageByDeptId = sysUserMapper.getPageByDeptId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(pageByDeptId).build() ;
        }
        return PageResult.<SysUser>builder().build() ;
    }
}
