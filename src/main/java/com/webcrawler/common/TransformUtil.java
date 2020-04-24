package com.webcrawler.common;

import com.esd.config.VoiceConfig;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 
 * @author 亿时代 语音转换的工具类
 */
public class TransformUtil {

	public static final String VOICEPATH = "web/voice";

	/**
	 * 获取填充好的信息通信类
	 * 
	 * @param request
	 * @param content
	 * @return
	 */
	public static VoiceConfig getVoiceConfig(HttpServletRequest request, String content) {
		VoiceConfig voiceConfig = new VoiceConfig();
		if (request != null) {
			// 设置将要转换的文本
			voiceConfig.setContent(content);
			// 获得将要输出语音文件的绝对路径
			String realPath = request.getSession().getServletContext().getRealPath(TransformUtil.VOICEPATH) + "/";
			// 设置绝对路径
			voiceConfig.setRealPath(realPath);
			// 获取将要播放语音文件网络路经也就是项目的网址
//			String path = request.getContextPath();
			String httpPath = request.getScheme() + "://" + request.getServerName() + "/";
			// 获得到项目的根路径后须要加上文件夹的名称
			// http://192.168.1.11:8080/yzm/Voice/生成的语音文件名称
			httpPath = httpPath + TransformUtil.VOICEPATH + "/";
			voiceConfig.setHttpPath(httpPath);
		} else {
			return null;
		}

		return voiceConfig;
	}

	/**
	 * 
	 * @param content
	 * @param output
	 * @param prefix
	 *            true为区为大小写有前缀，false为不区为大小写无前缀
	 * @return
	 */
	private static String mark(String content, String output, boolean prefix) {
		// String msg = content.toLowerCase();
		String msg = content;
		StringBuffer sb = new StringBuffer();
		sb.append(output);
		sb.append(",yzm.mp3");
		for (int i = 0; i < msg.length(); i++) {
			if (prefix) {
				char ch = msg.charAt(i);
				sb.append("," + ((int) ch) + ".mp3?" + System.currentTimeMillis() + ((int) ch));
			} else {
				char ch = msg.toLowerCase().charAt(i);
				sb.append("," + ch + ".mp3?" + System.currentTimeMillis() + ((int) ch));
			}
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

	/**
	 * 获取处理完成的可以播放的网络路径
	 * 
	 * @param voiceConfig
	 * @return 成功返回网络访问路径,失败返回null;
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static String getVoiceHttpPath(VoiceConfig voiceConfig) throws IOException, ClassNotFoundException {
		String output = voiceConfig.getHttpPath();
		String content = voiceConfig.getContent();
		String httpPath = mark(content, output, true);
		return httpPath;
	}

	public static String getVoiceHttpPath(VoiceConfig voiceConfig, boolean prefix) throws IOException, ClassNotFoundException {
		String output = voiceConfig.getHttpPath();
		String content = voiceConfig.getContent();
		String httpPath = null;
		if (prefix) {
			httpPath = mark(content, output, prefix);
		} else {
			httpPath = mark(content, output, prefix);
		}
		return httpPath;
	}
}
