package com.wafer.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wafer.business.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
