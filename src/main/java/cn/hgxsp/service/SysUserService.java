package cn.hgxsp.service;

import cn.hgxsp.dao.SysUserMapper;
import cn.hgxsp.exception.ParamException;
import cn.hgxsp.model.SysUser;
import cn.hgxsp.param.UserParam;
import cn.hgxsp.util.BeanValidator;
import cn.hgxsp.util.LevelUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.bind.ValidationException;
import java.util.Date;

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

        String password = "123456";
        SysUser sysUser = SysUser.builder()
                .username(userParam.getUsername())
                .mail(userParam.getMail())
                .deptId(userParam.getDeptId())
                .password(password)
                .telephone(userParam.getTelephone())
                .status(userParam.getStatus())
                .remark(userParam.getRemark())
                .build();

        sysUser.setOperator("system");//TODO
        sysUser.setOperateIp("127.0.0.1");//TODO
        sysUser.setOperateTime(new Date());

        //TODO 发送Email

        sysUserMapper.insertSelective(sysUser) ;
    }


    //验证邮箱是否存在
    public boolean checkEmailExist(String mail , Integer userId){
        return false ;
    }

    //验证邮箱是否存在
    public boolean checkTelPhoneExist(String telPhone , Integer userId){
        return false ;
    }


}
