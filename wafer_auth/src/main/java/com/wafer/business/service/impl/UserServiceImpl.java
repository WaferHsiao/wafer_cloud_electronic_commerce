package com.wafer.business.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mysql.cj.util.StringUtils;
import com.wafer.business.entity.User;
import com.wafer.business.exception.CommonException;
import com.wafer.business.mapper.UserMapper;
import com.wafer.business.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(String userName) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
    }

    @Override
    public int updateUser(User user) {
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (existUser == null){
            log.error("用户信息更新失败，根据userName查询不出用户信息：userName-"+user.getUsername());
            throw new CommonException("用户信息更新失败");
        }
        return userMapper.update(user, new LambdaUpdateWrapper<User>().eq(User::getId,existUser.getId()));
    }

    @Override
    public int deleteUser(String userName) {
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, userName));
        if (existUser == null){
            log.error("用户信息删除失败，根据userName查询不出用户信息：userName-"+userName);
            throw new CommonException("用户信息更新失败");
        }
        return userMapper.deleteById(existUser.getId());
    }

    @Override
    public int addUser(User user) {

        //校验字段
        if(StrUtil.isBlank(user.getUsername())){
            log.error("新增用户失败，username为空");
            throw new CommonException("用户信息新增失败");
        }
        else if (StrUtil.isBlank(user.getPassword())){
            log.error("新增用户失败，password为空");
            throw new CommonException("用户信息新增失败");
        }
        //判断用户名是否已存在
        User existUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername()));
        if (existUser != null){
            //用户存在
            log.error("用户信息新增失败，该用户已存在，userName:"+user.getUsername());
            throw new CommonException("用户信息新增失败，改用户已存在！");
        }
        return userMapper.insert(user);
    }
}
