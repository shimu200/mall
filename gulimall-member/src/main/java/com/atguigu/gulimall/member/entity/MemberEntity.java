package com.atguigu.gulimall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ��Ա
 * 
 * @author shimu
 * @email 3039375697@qq.com
 * @date 2023-04-01 13:19:10
 */
@Data
@TableName("ums_member")
public class MemberEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 社交平台uid
	 */
	private String socialUid;
	/**
	 * 社交平台给的访问令牌
	 */
	private String accessToken;
	/**
	 * 当次令牌的过期时间，单位s
	 */
	private Integer expireIn;
	/**
	 * 注册类型[0-本平台，1-微博，2-微信]
	 */
	private Integer registerType;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * ��Ա�ȼ�id
	 */
	private Long levelId;
	/**
	 * �û���
	 */
	private String username;
	/**
	 * ����
	 */
	private String password;
	/**
	 * �ǳ�
	 */
	private String nickname;
	/**
	 * �ֻ�����
	 */
	private String mobile;
	/**
	 * ����
	 */
	private String email;
	/**
	 * ͷ��
	 */
	private String header;
	/**
	 * �Ա�
	 */
	private Integer gender;
	/**
	 * ����
	 */
	private Date birth;
	/**
	 * ���ڳ���
	 */
	private String city;
	/**
	 * ְҵ
	 */
	private String job;
	/**
	 * ����ǩ��
	 */
	private String sign;
	/**
	 * �û���Դ
	 */
	private Integer sourceType;
	/**
	 * ����
	 */
	private Integer integration;
	/**
	 * �ɳ�ֵ
	 */
	private Integer growth;
	/**
	 * ����״̬
	 */
	private Integer status;
	/**
	 * ע��ʱ��
	 */
	private Date createTime;

}
