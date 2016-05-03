package com.esd.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/*
 * 用于把一个图片转化为12x18的01矩阵
 * 
 * @author snailzhang@126.com
 * @version 1.0, 02/04/2010
 */
public class BiImage {
	/** 图片的宽 */
	protected int width;

	/** 图片的高 */
	protected int height;

	/** 将image中的像素读取出来，存放在本变量中 */
	protected int pixels[];

	/** The constructor */
	public BiImage() {

	}

	/**
	 * 判断一个TYPE_INT_ARGB彩色是靠近白色还是靠近黑色
	 * 
	 * @param pixel
	 *            一个 TYPE_INT_ARGB颜色
	 * @return 对应的黑色或白色
	 */
	private static int convertToBlackWhite(int pixel) {
		int result = 0;

		// int alpha = (pixel >> 24) & 0xff; // not used
		int red = (pixel >> 16) & 0xff;
		int green = (pixel >> 8) & 0xff;
		int blue = (pixel) & 0xff;

		result = 0xff000000; // 这样，白色就为全F，即 -1

		int tmp = red * red + green * green + blue * blue;
		if (tmp > 3 * 128 * 128) { // 大于，则是白色
			result += 0x00ffffff;
		} else { // 是黑色

		}

		return result;
	}

	/**
	 * 初始化函数
	 * 
	 * @param imageFile
	 *            文件路径
	 * @return 无
	 * @throws IOException
	 * @throws Exception
	 * @exception 无
	 */
	public void initialize(byte[] b) throws Exception {
		InputStream in = null;
		in = InputStreamUtils.byteTOInputStream(b);
		BufferedImage bi = ImageIO.read(in);

		// 得到宽和高
		width = bi.getWidth(null);
		height = bi.getHeight(null);
		// 读取像素
		pixels = new int[width * height];
		bi.getRGB(0, 0, width, height, pixels, 0, width);
		bi = null;
		in.close();
	}

	/**
	 * 将图片转化为黑白图片
	 * 
	 * @param imgFile
	 *            输出文件路径
	 * @return
	 * @throws IOException
	 */
	public byte[] monochrome() {
		int newPixels[] = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			newPixels[i] = convertToBlackWhite(pixels[i]);
		}
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		bi.setRGB(0, 0, width, height, newPixels, 0, width);
		newPixels = null;
		ByteArrayOutputStream out = null;
		byte[] b = null;
		try {
			out = new ByteArrayOutputStream();
			ImageIO.write(bi, "JPEG", out);
			b = out.toByteArray();
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return b;
	}
}