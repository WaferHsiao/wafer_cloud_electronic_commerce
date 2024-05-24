package com.wafer.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wafer.business.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends IService<User> {
    User getUser(String userName);

    int updateUser(User user);

    int deleteUser(String userName);

    int addUser (User user);
}
