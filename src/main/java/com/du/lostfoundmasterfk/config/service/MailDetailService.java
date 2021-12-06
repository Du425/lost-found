package com.du.lostfoundmasterfk.config.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.du.lostfoundmasterfk.dao.TUserMapper;
import com.du.lostfoundmasterfk.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailDetailService implements UserDetailsService {

    @Autowired
    TUserMapper tUserMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null || "".equals(username)) {
            throw new UsernameNotFoundException("用户名不能为空");
        }
        String qq = username.substring(0, username.indexOf("@"));
        final QueryWrapper<TUser> wrapper = new QueryWrapper<TUser>().eq("qq", qq);
        final TUser user1 = tUserMapper.selectOne(wrapper);
        TUser tUser = null;
        if (user1 == null) {
            tUser = new TUser() {
                {
                    setNickName(username);
                    setPassword(username);
                    setQq(qq);
                }
            };
            try {
                tUserMapper.insert(tUser);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            tUser = user1;
        }

        List<GrantedAuthority> user = AuthorityUtils.commaSeparatedStringToAuthorityList("user");
        return new User(tUser.getNickName(), tUser.getPassword(), true, true, true, true, user);

    }
}
