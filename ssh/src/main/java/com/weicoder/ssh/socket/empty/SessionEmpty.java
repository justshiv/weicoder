package com.weicoder.ssh.socket.empty;

import com.weicoder.common.constants.ArrayConstants;
import com.weicoder.ssh.socket.Session;

/**
 * 空Session实现
 * @author WD 
 * @version 1.0 
 */
public final class SessionEmpty implements Session {
	/** 空Session */
	public final static Session EMPTY = new SessionEmpty();

	@Override
	public void close() {}

	@Override
	public int id() {
		return -1;
	}

	@Override
	public byte[] send(short id, Object message) {
		return ArrayConstants.BYTES_EMPTY;
	}

	@Override
	public byte[] send(Object message) {
		return ArrayConstants.BYTES_EMPTY;
	}

	@Override
	public byte[] send(byte[] data) {
		return ArrayConstants.BYTES_EMPTY;
	}

	@Override
	public void write(byte[] data) {}

	@Override
	public String ip() {
		return null;
	}

	@Override
	public int port() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	private SessionEmpty() {}

	@Override
	public byte[] buffer(short id, Object message) {
		return null;
	}

	@Override
	public byte[] buffer(Object message) {
		return null;
	}

	@Override
	public void flush() {}
}
