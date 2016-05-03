package com.esd.common;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class AutoDiscern {
	private int[] num = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
	private int black = -14000000;
	private static final int[] s0 = new int[] { 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, 0, 0 };
	private static final int[] s1 = new int[] { 0, 0, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, -1, -1, 0 };
	private static final int[] s2 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0 };
	private static final int[] s3 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0 };
	private static final int[] s4 = new int[] { 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, 0, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, 0, -1, 0, 0, -1, -1, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0 };
	private static final int[] s5 = new int[] { 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0 };
	private static final int[] s6 = new int[] { 0, 0, 0, 0, 0, -1, -1, -1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, -1, 0, -1, -1, -1, 0, 0, -1, -1, -1, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0 };
	private static final int[] s7 = new int[] { 0, 0, -1, -1, -1, -1, -1, -1, 0, 0, -1, -1, -1, -1, -1, -1, 0, -1, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0 };
	private static final int[] s8 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, -1, 0, 0 };
	private static final int[] s9 = new int[] { 0, 0, -1, -1, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, -1, -1, 0, 0, 0, -1, -1, 0, 0, -1, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, -1, -1, 0, 0, 0, -1, -1, -1, 0, 0, 0, 0, 0 };
	private static final int[] s10 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
	private static final int[] s11 = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };

	private static final ArrayList<int[]> list = new ArrayList<int[]>(11) {
		private static final long serialVersionUID = 6732234070993079758L;
		{
			add(s0);
			add(s1);
			add(s2);
			add(s3);
			add(s4);
			add(s5);
			add(s6);
			add(s7);
			add(s8);
			add(s9);
			add(s10);
			add(s11);
		}
	};

	private int[] proc(int[] s) {
		for (int m = 0; m < s.length; m++) {
			if (s[m] > black) {
				s[m] = 0;
			} else {
				s[m] = -1;
			}
		}
		return s;
	}

	public String discernPic(byte[] b, int type) {
		BiImage biImage = new BiImage();
		byte[] bb = null;
		BufferedImage b1 = null, b2 = null, b3 = null, b4 = null;
		try {
			biImage.initialize(b);
			bb = biImage.monochrome();
			if (type == 1) {
				b1 = cut(bb, 6, 4, 8, 12);
				b2 = cut(bb, 19, 4, 8, 12);
				b3 = cut(bb, 32, 4, 8, 12);
				b4 = cut(bb, 45, 4, 8, 12);
				int s1 = discern(b1);
				int s2 = discern(b2);
				int s3 = discern(b3);
				int s4 = discern(b4);
				StringBuffer sb = new StringBuffer();
				sb.append(s1).append(s2).append(s3).append(s4);
				return sb.toString();
			} else if (type == 2) {
				int m = 0;
				while (true) {
					if (m == 0) {
						// 加法
						b1 = cut(bb, 2, 4, 8, 12);// 第一位原先是6
						b2 = cut(bb, 11, 4, 8, 12);// 第一位原先是19
						b3 = cut(bb, 21, 4, 8, 12);// 第一位原先是32
						b4 = cut(bb, 45, 4, 8, 12);// 第一位原先是45
					} else if (m == 1) {
						// 减法
						b1 = cut(bb, 2, 4, 8, 12);// 第一位原先是6
						b2 = cut(bb, 9, 4, 8, 12);// 第一位原先是19
						b3 = cut(bb, 17, 4, 8, 12);// 第一位原先是32
						b4 = cut(bb, 45, 4, 8, 12);// 第一位原先是45
						break;
					}
					int s2 = discern(b2);
					if (s2 == 10) {
						m = 1;
					} else {
						break;
					}
				}
				int s1 = discern(b1);
				int s2 = discern(b2);
				int s3 = discern(b3);
				int result = 0;
				if (s2 == 10) {
					result = s1 - s3;
				} else if (s2 == 11) {
					result = s1 + s3;
				}
				StringBuffer sb = new StringBuffer();
				sb.append(result);

				return sb.toString();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int discern(BufferedImage bufferedImage) {
		int value = -1;
		int resemble = 0;
		int[] pixels = new int[96];
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "JPEG", out);
			byte[] b = out.toByteArray();
			BufferedImage bi = ImageIO.read(InputStreamUtils.byteTOInputStream(b));
			int[] src = getRGB(bi, 0, 0, 8, 12, pixels);
			src = proc(src);
			for (int l = 0; l < list.size(); l++) {
				int tmp = 0;
				int[] it = (int[]) list.get(l);
				for (int i = 0; i < src.length; i++) {
					if (src[i] == it[i]) {
						tmp++;
					}
				}
				if (tmp > resemble) {
					resemble = tmp;
					value = l;
				}
			}
			return num[value];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;
	}

	public int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType();
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
		return image.getRGB(x, y, width, height, pixels, 0, width);
	}

	/**
	 * 图像切割(按指定起点坐标和宽高切割)
	 * 
	 * @param srcImageFile
	 *            源图像地址
	 * @param result
	 *            切片后的图像地址
	 * @param x
	 *            目标切片起点坐标X
	 * @param y
	 *            目标切片起点坐标Y
	 * @param width
	 *            目标切片宽度
	 * @param height
	 *            目标切片高度
	 */
	public BufferedImage cut(byte[] b, int x, int y, int width, int height) {
		InputStream in = null;
		try {
			// 图片流
			in = InputStreamUtils.byteTOInputStream(b);
			BufferedImage bi = ImageIO.read(in);
			int srcHeight = bi.getHeight(); // 源图宽度
			int srcWidth = bi.getWidth(); // 源图高度
			if (srcWidth > 0 && srcHeight > 0) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				// 四个参数分别为图像起点坐标和宽高
				// 即: CropImageFilter(int x,int y,int width,int height)
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
				g.dispose();
				return tag;
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
