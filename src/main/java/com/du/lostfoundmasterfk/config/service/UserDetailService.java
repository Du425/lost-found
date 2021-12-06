package com.du.lostfoundmasterfk.config.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.du.lostfoundmasterfk.core.error.BusinessException;
import com.du.lostfoundmasterfk.core.error.EmBusinessError;
import com.du.lostfoundmasterfk.dao.TUserMapper;
import com.du.lostfoundmasterfk.entity.TUser;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userdetailservice")
public class UserDetailService {

    @Resource
    private TUserMapper tUserMapper;

    @SneakyThrows
//    @Override
    public UserDetails loadUserByName(String username) throws UsernameNotFoundException{
        if (username == null || "".equals(username)){
            throw new UsernameNotFoundException("用户名不能为空");
        }

        final QueryWrapper<TUser> queryWrapper = new QueryWrapper<TUser>().eq("nickname",username);
        final TUser user = tUserMapper.selectOne(queryWrapper);
        if (user == null){
            throw new BusinessException(EmBusinessError.PRIMARY_ERROR,"用户不存在");
        }
        //这里通过查找获取权限
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("user");

        return new User(user.getNickName(), user.getPassword(), auths);
    }
}
