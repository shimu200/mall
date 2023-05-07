package com.atguigu.gulimall.member.service;

import com.atguigu.gulimall.member.exception.PhoneException;
import com.atguigu.gulimall.member.exception.UsernameException;
import com.atguigu.gulimall.member.vo.MemberLoginVo;
import com.atguigu.gulimall.member.vo.MemberRegistVo;
import com.atguigu.gulimall.member.vo.SocialUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.member.entity.MemberEntity;

import java.text.ParseException;
import java.util.Map;

/**
 * ��Ա
 *
 * @author shimu
 * @email 3039375697@qq.com
 * @date 2023-04-01 13:19:10
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void regist(MemberRegistVo vo);
    void checkPhoneUnique(String phone) throws PhoneException;
    void checkUsernameUnique(String username) throws UsernameException;

    MemberEntity login(MemberLoginVo vo);
    MemberEntity login(SocialUser socialUser) throws ParseException;
}

