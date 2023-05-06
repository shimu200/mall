package com.atguigu.gulimall.authserver.fegin;
import com.atguigu.common.utils.R;
import com.atguigu.gulimall.authserver.vo.UserLoginVo;
import com.atguigu.gulimall.authserver.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient("gulimall-member")
public interface MemberFeignService {
    @PostMapping("/member/member/regist")
    R regist(UserRegistVo vo);

    @PostMapping("/member/member/login")
    R login(@RequestBody UserLoginVo vo);
//
//    @PostMapping(value = "/member/member/oauth2/login")
//    R oauthLogin(@RequestBody GiteeUserInfo giteeUserInfo);
}
