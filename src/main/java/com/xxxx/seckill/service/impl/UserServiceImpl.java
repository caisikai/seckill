package com.xxxx.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.seckill.exception.GlobalException;
import com.xxxx.seckill.mapper.UserMapper;
import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.utils.CookieUtil;
import com.xxxx.seckill.utils.MD5Util;
import com.xxxx.seckill.utils.UUIDUtil;
import com.xxxx.seckill.vo.LoginVo;
import com.xxxx.seckill.vo.RespBean;
import com.xxxx.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author sky
 * @since 2023-02-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
//        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
//        if(!ValidateUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        User user=userMapper.selectById(mobile);
        if(user==null){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDBPass(password, user.getSlat()).equals(user.getPassword())){
            throw  new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        String ticket = UUIDUtil.uuid();
        //request.getSession().setAttribute(ticket, user);

        //用户信息存入redis
        redisTemplate.opsForValue().set("user:"+ticket, user);
        CookieUtil.setCookie(request, response, "userTicket", ticket);

        return RespBean.success();


    }

    @Override
    public User getUserByCookie(String ticket, HttpServletRequest request, HttpServletResponse response) {

        if(StringUtils.isEmpty(ticket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + ticket);
        if(user!=null){
            CookieUtil.setCookie(request, response, "userTicket", ticket);
        }
        return user;
    }
}
