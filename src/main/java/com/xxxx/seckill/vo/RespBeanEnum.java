package com.xxxx.seckill.vo;

/*
公共返回对象枚举
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum RespBeanEnum {

    SUCCESS(200, "success"),
    ERROR(500, "服务端异常"),
    LOGIN_ERROR(500210, "用户名或密码错误"),
    MOBILE_ERROR(500211, "手机号码格式不正确"),
    BIND_ERROR(500212, "参数校验异常"),
    EMPTY_STOCK(505210,"库存为空"),
    REPEAT_ERROR(505211, "限购一件"),
    ;

    private final Integer code;
    private final String message;
}
