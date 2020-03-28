package com.weicoder.test;

import com.weicoder.cache.annotation.Cache;
import com.weicoder.cache.annotation.Key;
import com.weicoder.common.binary.Binary;
import com.weicoder.protobuf.Protobuf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户信息
 * 
 * @author WD
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Protobuf 
@Accessors(chain = true)
@Cache
public class Users implements Binary{
	@Key
	private long    uid;
	private boolean anchor;
	private byte    admin;
	private short   nid;
	private int     sex;
	private float   exp; 
	private double  level;
	private String  nickname;
	private byte[]  b;
}
