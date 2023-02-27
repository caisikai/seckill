package com.xxxx.seckill.controller;

import com.xxxx.seckill.pojo.User;
import com.xxxx.seckill.service.IGoodsService;
import com.xxxx.seckill.service.IUserService;
import com.xxxx.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/toList")
    public String tolsit(Model model, User user){
        /*if(StringUtils.isEmpty(ticket)){
            return "login";
        }

        // User user = (User)session.getAttribute(ticket);
        User user = userService.getUserByCookie(ticket, request, response);
        if(null == user){
            return "login";
        }*/

        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVo());
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public  String toDetail(Model model, User user, @PathVariable Long goodsId){

        model.addAttribute("user", user);

        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);

        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        int secKillStatus = 0;
        int remainSeconds = 0;
        if(nowDate.before(startDate)){
            remainSeconds = (int)(startDate.getTime() - nowDate.getTime())/1000;
            secKillStatus=0;
        } else if (nowDate.after(endDate)){
            secKillStatus=2;
            remainSeconds =-1;
        } else{
            secKillStatus= 1;
            remainSeconds = 0;
        }
        model.addAttribute("secKillStatus", secKillStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        return "goodsDetail";
    }

}
