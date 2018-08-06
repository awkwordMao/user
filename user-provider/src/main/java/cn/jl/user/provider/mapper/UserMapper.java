package cn.jl.user.provider.mapper;

import cn.jl.uer.api.model.User;
import com.einwin.framework.mapper.GenericMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper extends GenericMapper<User, Long> {
    User getByName(String username);
    List<User> listUser();
}
