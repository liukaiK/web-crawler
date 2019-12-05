package com.webcrawler.config;

import java.io.Serializable;

/**
 * 语音转换的信息类:主要用来通信时携带相关信息.
 */
public class VoiceConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7413371604560457402L;

	private boolean bVoice;
	/**
	 * 须要转换的文本
	 */
	private String content;
	/**
	 * 生成语音文件的绝对路径
	 */
	private String realPath;
	/**
	 * 播放语音文件的网络路径
	 */
	private String httpPath;
	/**
	 * 语音文件的名称
	 */
	private String voiceName;

	/**
	 * 判断是否转换成功的函数
	 * 
	 * @return
	 */
	public boolean hasVoice() {
		return bVoice;
	}

	/**
	 * 获取生成语音文件的绝对路径
	 */
	public String getRealPath() {
		return realPath;
	}

	/**
	 * 设置生成语音文件的绝对路径
	 */
	public void setRealPath(String realPath) {
		this.realPath = realPath;
	}

	/**
	 * 获取播放语音文件的网络路径
	 */
	public String getHttpPath() {
		return httpPath;
	}

	/**
	 * 设置播放语音文件的网络路径
	 */
	public void setHttpPath(String httpPath) {
		this.httpPath = httpPath;
	}

	/**
	 * 获取语音文件的名称
	 */
	public String getVoiceName() {
		return voiceName;
	}

	/**
	 * 设置语音文件的名称
	 */
	public void setVoiceName(String voiceName) {
		this.voiceName = voiceName;
	}

	/**
	 * 获取须要转换的文本
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置须要转换的文本
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 检测转换是否成功;转换成功返回true.失败返回false
	 */
	public void setbVoice(boolean bVoice) {
		this.bVoice = bVoice;
	}

}
