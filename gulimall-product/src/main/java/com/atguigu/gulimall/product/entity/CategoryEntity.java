package com.atguigu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ��Ʒ��������
 * 
 * @author shimu
 * @email 3039375697@qq.com
 * @date 2023-03-31 19:16:32
 */
@Data
@TableName("pms_category")
public class CategoryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ����id
	 */
	@TableId
	private Long catId;
	/**
	 * ��������
	 */
	private String name;
	/**
	 * ������id
	 */
	private Long parentCid;
	/**
	 * �㼶
	 */
	private Integer catLevel;
	/**
	 * �Ƿ���ʾ[0-����ʾ��1��ʾ]
	 */
	private Integer showStatus;
	/**
	 * ����
	 */
	private Integer sort;
	/**
	 * ͼ���ַ
	 */
	private String icon;
	/**
	 * ������λ
	 */
	private String productUnit;
	/**
	 * ��Ʒ����
	 */
	private Integer productCount;

}
