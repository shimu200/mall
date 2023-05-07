package com.atguigu.gulimall.authserver.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.atguigu.common.utils.HttpUtils;
import com.atguigu.common.utils.R;
import com.atguigu.common.vo.MemberRespVo;
import com.atguigu.gulimall.authserver.entity.GiteeAccessToken;
import com.atguigu.gulimall.authserver.entity.GiteeUserInfo;
import com.atguigu.gulimall.authserver.fegin.MemberFeignService;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/*
*处理社交登录请求
 */
@Controller
public class OAuth2Controller {
    @Autowired
    MemberFeignService memberFeignService;
    /*
    社交登录成功回调
     */
    @GetMapping("/oauth2.0/gitee/success")
    public String gitee(@RequestParam("code") String code, HttpSession httpSession) throws Exception {
        Map<String, String> bodys = new HashMap<>();
        bodys.put("grant_type", "authorization_code");
        bodys.put("code", code);
        bodys.put("client_id", "e0a1136e3d633d1f0301e70e0188547e09daf5d6243c8fde907339f3466a8166");
        bodys.put("redirect_uri", "http://auth.gulimall.com/oauth2.0/gitee/success");
        bodys.put("client_secret", "1b180bebad5f449bb6796a3f3b53ed032922c36f00772bcae933c8095cd75679");
        //1.根据code换取GiteeAccessToken;
        HttpResponse response_GiteeAccesstoken = HttpUtils.doPost("https://gitee.com", "/oauth/token", "post", new HashMap(), new HashMap(), bodys);

        //处理返回值
        if (response_GiteeAccesstoken.getStatusLine().getStatusCode() == 200) {
            //获取到了GiteeAccessToken
            String tokenJson = EntityUtils.toString(response_GiteeAccesstoken.getEntity());
            GiteeAccessToken token = JSON.parseObject(tokenJson, GiteeAccessToken.class);
            //根据Token，通过查询Gitee Open Api获取用户信息
            Map<String, String> query = new HashMap<>();
            query.put("access_token", token.getAccess_token());
            HttpResponse response_userInfo = HttpUtils.doGet("https://gitee.com", "/api/v5/user", "get", new HashMap(), query);
            String giteeUserInfoJson = EntityUtils.toString(response_userInfo.getEntity());
            GiteeUserInfo giteeUserInfo = JSON.parseObject(giteeUserInfoJson, GiteeUserInfo.class);
            giteeUserInfo.setAccess_token(token.getAccess_token());
            giteeUserInfo.setExpires_in(token.getExpires_in());
            //登录或者注册这个社交用户
            //1.当前用户如果是第一次进入网站，就自动注册（为当前社交用户生成一个会员信息账号，以后这个社交账号就对应指定的会员）
            R r = memberFeignService.oauthLogin(giteeUserInfo);
            if (r.getCode() == 0) {
                //2.登录成功后跳回首页
                MemberRespVo data = r.getData("data", new TypeReference<MemberRespVo>() {});
                System.out.println("登陆成功"+data);
                httpSession.setAttribute("loginUser",data);
                return "redirect:http://gulimall.com";
            } else {
                return "redirect:http://auth.gulimall.com/login.html";
            }

        }return "redirect:http://auth.gulimall.com/login.html";
    }
}
