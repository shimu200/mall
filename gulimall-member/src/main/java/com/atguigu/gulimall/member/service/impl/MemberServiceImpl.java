package com.atguigu.gulimall.member.service.impl;

import com.atguigu.gulimall.member.dao.MemberLevelDao;
import com.atguigu.gulimall.member.entity.MemberLevelEntity;
import com.atguigu.gulimall.member.exception.PhoneException;
import com.atguigu.gulimall.member.exception.UsernameException;
import com.atguigu.gulimall.member.vo.MemberLoginVo;
import com.atguigu.gulimall.member.vo.MemberRegistVo;
import com.atguigu.gulimall.member.vo.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;

import com.atguigu.gulimall.member.dao.MemberDao;
import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.service.MemberService;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void regist(MemberRegistVo vo) {
        MemberDao memberDao = this.baseMapper;
        MemberEntity entity = new MemberEntity();
        //设置默认等级
        MemberLevelEntity levelEntity = memberLevelDao.getDefaultLevel();
        entity.setLevelId(levelEntity.getId());
        //检查用户名和手机号是否唯一
        checkPhoneUnique(vo.getPhone());
        checkUsernameUnique(vo.getUserName());


        entity.setMobile(vo.getPhone());
        entity.setUsername(vo.getUserName());
        entity.setNickname(vo.getUserName());
        //密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(vo.getPassword());
        entity.setPassword(encode);
        //保存
        memberDao.insert(entity);
    }
    @Override
    public void checkPhoneUnique(String phone) throws PhoneException {
        MemberDao baseMapper = this.baseMapper;
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (count > 0) {
            throw new PhoneException();
        }

    }

    @Override
    public void checkUsernameUnique(String username) throws UsernameException {
        MemberDao baseMapper = this.baseMapper;
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", username));
        if (count > 0) {
            throw new UsernameException();
        }

    }

    @Override
    public MemberEntity login(MemberLoginVo vo) {
        String loginacct = vo.getLoginacct();
        String password = vo.getPassword();
        MemberEntity member = this.baseMapper.selectOne(new QueryWrapper<MemberEntity>()
                .eq("username", loginacct).or().eq("mobile", loginacct).or().eq("email", loginacct));
        String password1 = null;
        if (member != null) {
            password1 = member.getPassword();
        } else {
            return null;
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean matches = bCryptPasswordEncoder.matches(password, password1);
        if (matches) {
            return member;
        } else {
            return null;
        }
    }

    @Override
    public MemberEntity login(SocialUser socialUser) throws ParseException {
        //登录和注册合并逻辑
        MemberEntity member = new MemberEntity();
        long socialId = socialUser.getId();
        MemberDao memberDao = this.baseMapper;
        //判断当前社交用户是否登录过
        MemberEntity socialUid = memberDao.selectOne(new QueryWrapper<MemberEntity>().eq("social_uid", socialId));
        if (socialUid !=null) {
            //这个用户已经注册过了
            MemberEntity entity = new MemberEntity();
            entity.setAccessToken(socialUser.getAccess_token());
            entity.setId(socialUid.getId());
            entity.setUsername(socialUser.getName());
            entity.setNickname(socialUser.getName());
            entity.setRegisterType(1);
            long expiresIn = socialUser.getExpires_in();
            entity.setExpireIn((int)expiresIn);
            entity.setSocialUid(Long.toString(socialId));
            memberDao.updateById(entity);
            return entity;
        }else {
            //没有就注册
            MemberEntity memberRegist = new MemberEntity();
            try {
                if (socialUid.getId()!=null){
                    memberRegist.setId(socialUid.getId());
                }
                memberRegist.setUsername(socialUser.getName());
                memberRegist.setNickname(socialUser.getName());
                memberRegist.setRegisterType(1);
                memberRegist.setLevelId(1L);
            }catch (Exception e){}
            memberRegist.setExpireIn((int) socialUser.getExpires_in());
            memberRegist.setSocialUid(Long.toString(socialId));
            memberRegist.setAccessToken(socialUser.getAccess_token());
            memberDao.insert(memberRegist);
            return memberRegist;
        }
    }
}