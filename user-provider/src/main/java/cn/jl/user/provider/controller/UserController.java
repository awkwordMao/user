package cn.jl.user.provider.controller;

import cn.jl.uer.api.model.User;
import cn.jl.user.provider.service.UserService;
import com.github.pagehelper.PageHelper;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;


import java.sql.Timestamp;
import java.util.Date;


@Controller
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/hello")
    public String hello(){
        logger.info("controller!!!!!!!!!!");
        return "hello";
    }

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/getuser/{id}")
    public User getById(@PathVariable("id") Long id){
        User user = userService.get(id);
        return user;
    }
    /**
     * 进入用户登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     * 处理用户登录请求
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/logging", method = RequestMethod.GET)
    public String logging(@Param("username") String username, @Param("password") String password, ModelMap map){
        HystrixRequestContext.initializeContext();
        User user = userService.getByName(username);
        logger.info(user.toString());
        User user1 = userService.getByName(username);
        logger.info(user1.toString());

        if(password.equals(user.getPassword())){
            map.addAttribute("username",username);
            return "hello";
        }
        return "error";
    }
    /**
     * 遍历所有用户，返回到页面
     * @param map
     * @return
     */
    @RequestMapping(value = "/listusers", method = RequestMethod.GET)
    public String getUserList(ModelMap map) {
        PageHelper.startPage(2,2);
        map.addAttribute("userList", userService.listUser());
        return "userList";
    }
    /**
     * 进入创建用户页面
     * @return
     */
    @RequestMapping(value = "/creatuserh", method = RequestMethod.GET)
    public String creatUserH(){
        return "creatUserH";
    }

    /**
     * 注册新用户
     * @param username
     * @param password1
     * @param password2
     * @param map
     * @return
     */
    @RequestMapping(value = "/creatuser", method = RequestMethod.GET)
    public String creatUser(@Param("username") String username, @Param("password1") String password1, @Param("password2") String password2, ModelMap map){
        if(password1.equals(password2)){
            User user = new User();
            user.setUserName(username);
            user.setPassword(password1);
            user.setCreationDate(new Timestamp(new Date().getTime()));
            int result = userService.insert(user);
            if(result == 1){
                return "login";
            }
        }
        map.addAttribute("msg", "两次密码不一致！");
        map.addAttribute("username", username);
        return "creatuserh";
    }

    /**
     * 进入用户更新页面
     * @param id
     * @param map
     * @return
     */
    @RequestMapping(value = "/updateh/{id}", method = RequestMethod.GET)
    public String updateH(@PathVariable("id") Long id, ModelMap map){
//        System.out.println(id);
        User user = userService.get(id);
        user.setUpdationDate(new Timestamp(new Date().getTime()));
        map.addAttribute("id", user.getId());
        map.addAttribute("username", user.getUserName());
        map.addAttribute("password", user.getPassword());
        return "updateH";
    }

    /**
     * 用户更新处理
     * @param id
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String update(@Param("id") Long id, @Param("username") String username, @Param("password") String password){
        User user = userService.get(id);
        user.setUserName(username);
        user.setPassword(password);
        user.setUpdationDate(new Timestamp(new Date().getTime()));
        userService.update(user);
        return "hello";
    }
}
