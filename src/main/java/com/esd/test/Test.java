package com.esd.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.esd.common.CatDao;
import com.esd.config.BaseConfig;
import com.esd.config.PageConfig;
import com.esd.util.Md5;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

public class Test {

	private static Logger logger = Logger.getLogger(Test.class);

	private CatDao dao = new CatDao();
	
	@org.junit.Test
	public void moni() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException {
		final String URL = "http://www.caacts.org.cn:8080/struts2_spring3_hibernate3_1.0/tsTianjia.action";
		WebClient webClient = new WebClient();
		webClient.getOptions().setJavaScriptEnabled(true);
		webClient.getOptions().setCssEnabled(false);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		HtmlPage page1 = (HtmlPage) webClient.getCurrentWindow().getEnclosedPage();
		page1 = webClient.getPage(URL);
		HtmlSelect provinceSelect = (HtmlSelect) page1.getElementById("province");
		provinceSelect.setSelectedAttribute("1", true);
		Thread.sleep(500);
		
		HtmlSelect citySelect = (HtmlSelect) page1.getElementById("city");
		citySelect.setSelectedAttribute("9C ", true);
		Thread.sleep(500);
		
		HtmlSelect city1Select = (HtmlSelect) page1.getElementById("city1");
		city1Select.setSelectedAttribute("I", true);
		
		HtmlSelect cptFile_passengerType = (HtmlSelect) page1.getElementById("cptFile_passengerType");
		cptFile_passengerType.setSelectedAttribute("1", true);
		
		HtmlInput passengerName = (HtmlInput) page1.getElementsByName("cptFile.passengerName").get(0);
		passengerName.setValueAttribute("刘凯2");
		
		HtmlSelect zhengjianid = (HtmlSelect) page1.getElementsByName("cptFile.zhengjianid").get(0);
		zhengjianid.setSelectedAttribute("身份证", true);
		
		HtmlInput shenfenId = (HtmlInput) page1.getElementsByName("cptFile.shenfenId").get(0);
		shenfenId.setValueAttribute("230921199309083212");
		
		HtmlInput flightNo = (HtmlInput)page1.getElementsByName("cptFile.flightNo").get(0);
		flightNo.setValueAttribute("CA3333");
		
		HtmlInput flightTime = (HtmlInput)page1.getElementsByName("cptFile.flightTime").get(0);
		flightTime.setValueAttribute("2016-06-06");
		
		HtmlInput passengerTel = (HtmlInput)page1.getElementsByName("cptFile.passengerTel").get(0);
		passengerTel.setValueAttribute("13206696177");			
		
		HtmlInput email = (HtmlInput)page1.getElementsByName("cptFile.Email").get(0);
		email.setValueAttribute("123123123@foxmail.com");	
		
		HtmlSelect depPort = (HtmlSelect)page1.getElementById("cptFile_depPort");
		depPort.setSelectedAttribute("NAY", true);
		
		HtmlSelect arrPort = (HtmlSelect)page1.getElementById("cptFile_arrPort");
		arrPort.setSelectedAttribute("BHY", true);			
		
		HtmlTextArea cptContent = (HtmlTextArea)page1.getElementsByName("cptFile.cptContent").get(0);
		cptContent.setTextContent("无障碍无障碍无障碍无障碍");
		
		
		
		
		
        // 创建文件输入流对象  
        FileInputStream is = new FileInputStream("D:\\image1.png");  
        // 设定读取的字节数  
        int n = 512;  
        byte[] buffer=new byte[2048]; 
        // 读取输入流  
        while ((is.read(buffer, 0, n) != -1) && (n > 0)) {  
            System.out.print(new String(buffer));  
        }  
        System.out.println();  
        // 关闭输入流  
        is.close();  
        
		
		HtmlFileInput uploadFile1 = (HtmlFileInput) page1.getElementsByName("uploadFile").get(0);
//		uploadFile1.setData(buffer);
		System.out.println(buffer);
		
		
		uploadFile1.setValueAttribute("D:\\image1.png");
		
		System.out.println(uploadFile1.getData());
		
//		HtmlAnchor submitBtn = (HtmlAnchor) page1.getElementById("submitBtn");
//		HtmlPage hp = submitBtn.click();
//		
//		HtmlButton tijiaoButton = (HtmlButton) page1.getElementsByTagName("button").get(0);
//		hp = tijiaoButton.click();
//		if (hp != null) {
//			String pageUrl = hp.getUrl().toString().trim();
//			System.out.println(pageUrl);
//		}
		
	}
	
	@org.junit.Test
	public void ReadFile() throws IOException {
        // 创建文件输入流对象  
        FileInputStream is = new FileInputStream("D:\\image1.png");  
        // 设定读取的字节数  
        int n = 512;  
        byte buffer[] = new byte[n];  
        // 读取输入流  
        while ((is.read(buffer, 0, n) != -1) && (n > 0)) {  
            System.out.print(new String(buffer));  
        }  
        System.out.println();  
        // 关闭输入流  
        is.close();  
	}
	

	@org.junit.Test
	public void getSource() throws IOException {
		String url = "http://wza.caacca.org/";
		Document htmlSource = Jsoup.connect(url).get();
		System.out.println(htmlSource);
//		Elements links = htmlSource.select("a[href],area[href],iframe[src]");
		
//		for (Element e : links) {
//			String href = e.attr("abs:href").trim();
//			if (href.equals("")) {
//				href = e.attr("abs:src").trim();
//				if (href == null) {
//					continue;
//				}
//			}
//		}
	}

	@org.junit.Test
	public void findPageConfigFile() {
		String url = "http://www.szft.gov.cn/xxgk/zdly/sjxx/gzbg/201606/t20160629_510236.html";
		BaseConfig.PG_ROOT = "E:\\apache-tomcat-iac\\webapps\\iac\\web" + File.separator + "db";
		dao.collectPageConfig();
		PageConfig pageConfig = dao.findPageConfig(url);
		System.out.println(pageConfig.getDb());
	}

	@org.junit.Test
	public void getHtmlSource() throws IOException {
		String url = "http://www.baic.gov.cn/zxbs/htsfwb/shxfl/";
		Connection jsoup = Jsoup.connect(url);
		Element htmlSource = jsoup.get().select(".htwbtable").get(0);
		Elements links = htmlSource.select("a");
		for (Element link : links) {
			String onclick = link.attr("onclick");
			String str[] = onclick.split(",");
			String href = str[1].substring(str[1].indexOf("'") + 3, str[1].lastIndexOf("'"));
			System.out.println("http://www.baic.gov.cn/zxbs/htsfwb/shxfl/" + href);
		}
	}

	
	@org.junit.Test
	public void getMd5() {
		StringBuffer str;
		str = new StringBuffer("http://www.caac.gov.cn/");
		Md5 md5 = new Md5();
		System.out.println(md5.getMd5(str));
	}
	
	@org.junit.Test
	public void parseHTML() {
		String str = "";
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream("D:\\test.txt"), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			read.close();
			br.close();
//			System.out.println(sb);
			Document doc = Jsoup.parse(sb.toString());
			Elements options = doc.getElementsByTag("select").get(0).getElementsByTag("option");
			for (Element option : options) {
			System.out.println("city1option.add(new Option(\""+option.text()+"\",\""+option.attr("value")+"\"));");
		}			
			
			
		} catch (Exception e) {
		}
		
		
		
		
//		String html = "<select>  
//<option value="MS ">A埃及航空公司                  </option><option value="ET ">A埃塞俄比亚航空</option><option value="OS ">A奥地利航空公司                </option><option value="QF ">A澳洲航空公司          </option><option value="NX ">A澳门航空公司                  </option><option value="J2 ">A阿塞拜疆航空公司</option><option value="RQ ">A阿富汗卡姆航空公司</option><option value="4Q ">A阿富汗萨菲航空公司</option><option value="FG ">A阿富汗阿里亚纳航空公司</option><option value="AH ">A阿尔及利亚航空公司</option><option value="KC ">A阿斯塔纳航空公司              </option><option value="EK ">A阿联酋航空公司                </option><option value="EY ">A阿联酋阿提哈德航空公司</option><option value="SK ">B北欧航空公司                  </option><option value="NL ">B巴基斯坦沙欣航空公司</option><option value="PK ">B巴基斯坦航空公司              </option><option value="CM ">B巴拿马航空公司                </option><option value="LO ">B波兰航空公司</option><option value="JS ">C朝鲜航空公司</option><option value="3E ">D东亚航空公司</option><option value="CO ">D大陆航空公司</option><option value="KE ">D大韩航空                      </option><option value="AB ">D德国柏林航空                  </option><option value="U6 ">E俄罗斯乌拉尔航空公司</option><option value="SU ">E俄罗斯国际航空公司            </option><option value="UN ">E俄罗斯洲际航空公司            </option><option value="AU ">E俄罗斯联合航空公司            </option><option value="S7 ">E俄罗斯西伯利亚航空公司</option><option value="H8 ">E俄罗斯远东航空公司</option><option value="7P ">F发达飞航空公司</option><option value="AF ">F法国航空公司                  </option><option value="XF ">F符拉迪沃斯托克航空公司</option><option value="AY ">F芬兰航空公司                  </option><option value="5J ">F菲律宾宿务太平洋航空公司</option><option value="PR ">F菲律宾航空公司                </option><option value="Z2 ">F菲律宾菲亚航</option><option value="ZZ ">F菲律宾飞龙航空</option><option value="CX ">G国泰航空公司</option><option value="KA ">G港龙航空                      </option><option value="US ">H合众国际航空公司              </option><option value="LH ">H德国汉莎航空公司                  </option><option value="GF ">H海湾航空公司                  </option><option value="KL ">H荷兰皇家航空公司              </option><option value="HZ ">H赫兹航空公司</option><option value="OZ ">H韩亚航空                      </option><option value="LJ ">H韩国真航空</option><option value="AC ">J加拿大航空公司                </option><option value="GI ">J吉尔吉克斯坦航空公司</option><option value="QH ">J吉尔吉斯斯坦黄金航空公司</option><option value="9W ">J捷达航空(印度)有限公司</option><option value="K6 ">J柬埔寨吴哥航空公司</option><option value="ZA ">J柬埔寨天翼亚洲航空公司</option><option value="UM ">J津巴布韦航空公司</option><option value="7B ">K克拉斯诺亚尔斯克股份开放式航空公司</option><option value="QR ">K卡塔尔航空公司                </option><option value="KQ ">K肯尼亚航空公司                </option><option value="QV ">L老挝航空公司</option><option value="AM ">M墨西哥航空公司</option><option value="MX ">M墨西哥航空公司                </option><option value="PG ">M曼谷航空公司                  </option><option value="MK ">M毛里求斯航空公司</option><option value="5M ">M美佳航空</option><option value="UA ">M美国联合航空公司              </option><option value="AA ">M美国航空公司                  </option><option value="NW ">M美国西北航空公司              </option><option value="DL ">M美国达美航空公司                  </option><option value="E3 ">M莫杰航空公司</option><option value="MO ">M蒙古国航空公司</option><option value="OM ">M蒙古航空公司</option><option value="AK ">M马来西亚亚洲航空有限公司</option><option value="D7 ">M马来西亚亚洲航空（长途）有限公司</option><option value="MH ">M马来西亚航空公司              </option><option value="SA ">N南非航空公司                  </option><option value="RA ">N尼泊尔航空公司</option><option value="FV ">P普尔科沃航空公司</option><option value="NH ">Q全日空航空公司                </option><option value="JL ">R日本航空公司                  </option><option value="LX ">R瑞士国际航空公司              </option><option value="DV ">S斯卡特航空公司</option><option value="UL ">S斯里兰卡航空公司</option><option value="CI ">T台湾中华航空公司</option><option value="AE ">T台湾华信航空公司</option><option value="GE ">T台湾复兴航空公司</option><option value="B7 ">T台湾立荣航空公司</option><option value="BR ">T台湾长荣航空公司              </option><option value="T5 ">T土库曼斯坦航空公司</option><option value="TK ">T土耳其航空公司                </option><option value="7J ">T塔吉克斯坦航空公司</option><option value="OX ">T泰国东方航空公司</option><option value="FD ">T泰国亚洲航空公司</option><option value="XJ ">T泰国亚洲航空（长途）有限公司</option><option value="TG ">T泰国国际航空公司              </option><option value="WE ">T泰国微笑航空公司</option><option value="DD ">T泰国飞鸟航空</option><option value="PS ">W乌克兰国际航空</option><option value="VV ">W乌克兰空中世界</option><option value="HY ">W乌兹别克斯坦航空公司</option><option value="G6 ">W吴哥航空公司</option><option value="BI ">W文莱王家航空公司              </option><option value="VS ">W维珍航空公司                  </option><option value="3K ">X新加坡捷星亚洲航空私人有限公司</option><option value="MI ">X新加坡胜安航空公司</option><option value="SQ ">X新加坡航空公司                </option><option value="TZ ">X新加坡酷航</option><option value="NZ ">X新西兰航空公司                </option><option value="TR ">X欣丰虎航空公司</option><option value="UO ">X香港快运航空有限公司</option><option value="HK ">X香港航空公司</option><option value="LY ">Y以色列航空公司                </option><option value="IR ">Y伊朗航空公司                  </option><option value="W5 ">Y伊朗马汉航空公司</option><option value="QZ ">Y印度尼西亚亚洲航空有限公司</option><option value="GA ">Y印度尼西亚鹰航空公司     </option><option value="AI ">Y印度航空公司                  </option><option value="AZ ">Y意大利航空公司                </option><option value="BA ">Y英国航空公司                  </option><option value="VN ">Y越南航空公司                  </option><option value="R3 ">Y雅库茨克航空公司</option></select>";
//		
//		Document doc = Jsoup.parse(html);
//		Elements options = doc.getElementsByTag("select").get(0).getElementsByTag("option");
//		for (Element option : options) {
//			
//			System.out.println("city1option.add(new Option(\""+option.text()+"\",\""+option.attr("value")+"\"));");
//		}
		
		
		
		
		
		
	}	
	

	public static void main(String[] args) throws IOException, ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String ss = "2016-12-11";
		
		System.out.println(sdf.parse(ss).toString());
		
		
		
//		// 这里的数后面加“D”是表明它是Double类型，否则相除的话取整，无法正常使用
//		double percent = 50.5D / 150D;
//		// 输出一下，确认你的小数无误
//		System.out.println("小数：" + percent);
//		// 获取格式化对象
//		NumberFormat nt = NumberFormat.getPercentInstance();
//		// 设置百分数精确度2即保留两位小数
//		nt.setMinimumFractionDigits(2);
//		// 最后格式化并输出
//		System.out.println("百分数：" + nt.format(percent));
		
	}

	public static Document downLoadTemple(String templePath) {
		String str = "";
		StringBuffer sb = new StringBuffer();
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(templePath), "UTF-8");
			BufferedReader br = new BufferedReader(read);
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			read.close();
			br.close();
		} catch (Exception e) {
			logger.error(e);
		}
		Document doc = Jsoup.parse(sb.toString());
		return doc;
	}

	public static void createHTMLFile(String content, String filePath) {
		try {
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
			BufferedWriter bw = new BufferedWriter(write);
			bw.write(content);
			bw.close();
			write.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
