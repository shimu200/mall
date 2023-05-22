package com.atguigu.gulimall.member.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
@Controller
public class MemberWebController {
    @GetMapping("/memberOrder.html")
    public String memberOrderPage(){
        return "orderList";
    }
}
