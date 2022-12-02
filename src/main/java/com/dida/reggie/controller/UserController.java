package com.dida.reggie.controller;

import com.dida.reggie.common.Result;
import com.dida.reggie.entity.User;
import com.dida.reggie.service.UserService;
import com.dida.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送验证码
     *
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session) {
        //获取手机号
        String phone = user.getPhone();

        //判断手机号是否为空
        if (StringUtils.isNotEmpty(phone)) {
            //生成随机的四位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code: {}", code);

            //将生成的验证码保存到session中
            //session.setAttribute(phone, code);

            //将生成的验证码缓存到Redis中，并且设置有效期为5分钟
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

            return Result.success("手机验证码发送成功");
        }
        return Result.error("发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();

        //从session中获取保存的验证码
        //Object codeInSession = session.getAttribute(phone);

        //从Redis获取到我们缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //进行验证码的比对，比对成功说明登录成功
        if (codeInSession != null && codeInSession.equals(code)) {
            //判断该用户是否存在，不存在则说明是新用户，这时候为其进行自动注册
            User user = userService.getUserByPhone(phone);
            if (user == null) {
                //注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);

                userService.save(user);

            }
            //将该用户的id存到session中
            Long userId = userService.getUserByPhone(phone).getId();
            session.setAttribute("user", userId);
            //如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);
            return Result.success(user);
        }

        return Result.error("登录失败");
    }
}
