package com.face.backend.controller.phoneNumber;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.util.Strings;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.aip.ocr.AipOcr;
import com.face.backend.controller.BaseController;
import com.face.backend.pojo.Result;
import com.face.backend.pojo.SearchResultInfo;

@RestController
@RequestMapping("/phone")
public class PhoneSearchController extends BaseController {

	//	private static byte[] readInputStream(InputStream inStream) throws Exception {
	//		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
	//		//创建一个Buffer字符串  
	//		byte[] buffer = new byte[1024];
	//		//每次读取的字符串长度，如果为-1，代表全部读取完毕  
	//		int len = 0;
	//		//使用一个输入流从buffer里把数据读取出来  
	//		while ((len = inStream.read(buffer)) != -1) {
	//			//用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
	//			outStream.write(buffer, 0, len);
	//		}
	//		//关闭输入流  
	//		inStream.close();
	//		//把outStream里的数据写入内存  
	//		return outStream.toByteArray();
	//	}

	@GetMapping("/check")
	public SearchResultInfo phoneCheck(String tel) {
		CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
		AipOcr client = new AipOcr("16759796", "ul5TQmWsVkc9t6onLtT9jsi2", "NV6OiNmwXRjjGwViUc1UmUYicXwOVqLB");
		String allString = "";
		String flagList = "";
		SearchResultInfo locationFrom3RdInfo = new SearchResultInfo();
		try {
			URL url = new URL("https://www.so.com/s?q=" + tel);
			URLConnection URLconnection = url.openConnection();
			HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				System.err.println("成功");
				InputStream in = httpConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(in,"utf-8");
				BufferedReader bufr = new BufferedReader(isr);
				String str;
				while ((str = bufr.readLine()) != null) {
					allString += str;
				}
				bufr.close();
			} else {
				System.err.println("失败");
			}

			String strs[] = allString.split("style=\"background-color:#e76639\">");

			if (strs.length >= 2) {
				String strsSub[] = strs[1].split("</span>");
				flagList = strsSub[0];
			}

			URL urlPhoneLocation = new URL("https://ifish.fun/tools/phone?tel=" + tel);
			URLConnection URLconnectionPhoneLocation = urlPhoneLocation.openConnection();
			HttpURLConnection httpConnectionPhoneLocation = (HttpURLConnection) URLconnectionPhoneLocation;
			int responseCodePhoneL = httpConnectionPhoneLocation.getResponseCode();

			String jsonStr = "";
			if (responseCodePhoneL == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnectionPhoneLocation.getInputStream();
				InputStreamReader isr = new InputStreamReader(in, "utf-8");
				BufferedReader bufr = new BufferedReader(isr);

				String str;
				while ((str = bufr.readLine()) != null) {
					jsonStr += str;
				}
				bufr.close();

			} else {
				System.err.println("失败");
			}
			
			System.err.println(jsonStr);

			locationFrom3RdInfo = com.alibaba.fastjson.JSON.parseObject(jsonStr, SearchResultInfo.class);

			//			if (Strings.isNotBlank(flagList)) {
			//
			//				URL urlFile = new URL("http://www.114best.com/dhpic/" + fileList.get(1));
			//				//打开链接  
			//				HttpURLConnection conn = (HttpURLConnection) urlFile.openConnection();
			//				//设置请求方式为"GET"  
			//				conn.setRequestMethod("GET");
			//				//超时响应时间为5秒  
			//				conn.setConnectTimeout(5 * 1000);
			//				//通过输入流获取图片数据  
			//				InputStream inStream = conn.getInputStream();
			//
			//				BufferedImage image;
			//				try {
			//					image = ImageIO.read(inStream);
			//					ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			//					ImageIO.write(image, "png", outStream);
			//					JSONObject res = client.basicAccurateGeneral(outStream.toByteArray(),
			//							new HashMap<String, String>());
			//					System.out.println(res.toString(2));
			//					org.json.JSONArray jsonArray = res.getJSONArray("words_result");
			//					if (jsonArray != null && jsonArray.length() > 0) {
			//						JSONObject jsonObjectWordJsonObject = jsonArray.getJSONObject(0);
			//                        flagString = jsonObjectWordJsonObject.getString("words");
			//					}
			//
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//
			//			}
			//		}
			//
		} catch (Exception e) {
			e.printStackTrace();
		}

		SearchResultInfo aSearchResultInfo = new SearchResultInfo();

		aSearchResultInfo.setError_code("0");
		aSearchResultInfo.setReason("查询成功");
		Result result = new Result();

		result.setCountry("");
		result.setProvince(Strings.isNotBlank(locationFrom3RdInfo.getResult().getCity()) ? locationFrom3RdInfo.getResult().getProvince()+locationFrom3RdInfo.getResult().getCity() :locationFrom3RdInfo.getResult().getName());
		result.setCity(flagList.replace("\t", ""));
		result.setPhone(tel);
		result.setName("");
		result.setDistrict("");
		result.setCompany("");
		aSearchResultInfo.setResult(result);

		/*      
		File file = new File("d:/33.gif");
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			File f = new File("d:/3333.jpg");
			ImageIO.write(image, "jpg", f);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/

		return aSearchResultInfo;
	}
}
