package cn.jl.user.provider.service;

import cn.jl.uer.api.model.User;
import cn.jl.user.provider.mapper.UserMapper;
import com.einwin.framework.mapper.GenericMapper;
import com.einwin.framework.service.GenericService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userservice")
public class UserService extends GenericService<User, Long> {
    public UserService(GenericMapper<User, Long> genericMapper) {
        super(genericMapper);
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    UserMapper userMapper;

    @CacheResult
    @HystrixCommand
    public User getByName(String username){
        LOGGER.info("没有缓存，亲自查询..............");
        return userMapper.getByName(username);
    }
    public List<User> listUser(){
        return userMapper.listUser();
    }
}
