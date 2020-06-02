package com.yutu.controller.office;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.lf5.viewer.configure.ConfigurationManager;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/excel")
public class CreateExcelController {
	
/*
	
	*//**
	 * 环境专题中历史对比的详情页面的数据导出成excel
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 *//*
	@RequestMapping(value="/export", produces="application/json;charset=utf-8")
	public String CreateExcelData(HttpServletRequest request,HttpServletResponse response){
		String titles = request.getParameter("titles");
		String fields = request.getParameter("fields");
		String reDatas = request.getParameter("reDatas");
		Map<String,Object> returnmap = new HashMap<>();
		List<Map<String,Object>> list = (List<Map<String, Object>>) JSONObject.parse(reDatas); 
		String[] Titles = titles.split(",");
		String[] Fields = fields.split(",");
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			String path = ExcelUtil.class.getClassLoader().getResource("../../").getPath() + "resources/uploadExcel/";
			Date date = new Date();
            String fileName = "Data_" + date.getTime() + ".xls";
			HSSFWorkbook wb = (HSSFWorkbook) ExcelUtil.createWorkBook(list, Fields, Titles);
			wb.write(bos);
			byte[] content = bos.toByteArray();
			is = new ByteArrayInputStream(content);
			os = new FileOutputStream(path + fileName);
			int len;
			while ((len = is.read())!=-1) {
				os.write(len);
			}
//			request.setCharacterEncoding("UTF-8");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			Date datenow = new Date();
//			String createTime = sdf.format(datenow);
//			String datenowFile = "数据表_" + createTime + ".xls";
//			response.setContentType("application/vnd.ms-excel,charset=gbk");
//			response.setHeader(
//					"Content-Disposition",
//					"attachment;filename=\""
//							+ java.net.URLEncoder.encode(datenowFile, "UTF-8")
//							+ "\"");
//			wb.write(response.getOutputStream());
//			ExcelUtil.ComExcel(response, list, "datacell", Fields, Titles);
			returnmap.put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				os.close();
				is.close();
			} catch (IOException e) {
			}
		}
		return returnmap;
	}
	
	
	@RequestMapping(value="/CreateResExcelData", produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreateResExcelData(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception{
		String titles = request.getParameter("titles");
		String fields = request.getParameter("fields");
		String type = request.getParameter("type");
		List<String>listCountry=new ArrayList<String>();
		String indexCode = request.getParameter("indexCode");
		//将indexCode进行转换
		TStorCatalogresourcemapping mapping= serviceFactory.getSourceDirectoryService().getTStorMappingInfo(indexCode);
		indexCode=mapping.getResourcesCode();
		String[] strIndexCode=indexCode.split(",");
		List<String> listIndexCode=Arrays.asList(strIndexCode);
		String countryCode = request.getParameter("countryCode");
		if(countryCode.contains("ORG"))
		{
			List<TCodCountry> listCountryInfo=serviceFactory.getOrgCountryService().getCountryByOrg(countryCode);
			for (int i = 0; i < listCountryInfo.size(); i++) {
				listCountry.add(listCountryInfo.get(i).getUuid());
			}
		}
		else {
			listCountry.add(countryCode);
		}
		Map<String,Object> returnmap = new HashMap<>();
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		int downloadNumber = Integer.parseInt(ConfigurationManager.getValue("downloadNumber"));
		
		//通用全球
		if(type.equals("T1")){
//			urlStr='../Data/getCatalogResourcesOne.do?orgName='
//					+vmDetail.countryCode+'&indexCode='
//					+vmDetail.indexCode+'&dataTotal='+vmDetail.dataTotal;
			String orgName = countryCode;
			list = serviceFactory.getDataService().getCatalogResourcesOne(orgName,listIndexCode,0,downloadNumber);
		}
		//通用国家
		else if(type.equals("T2")){
//			urlStr='../Data/getCatalogResourcesTwo.do?countryCode='
//					+vmDetail.countryCode+'&indexCode='
//	    		+vmDetail.indexCode+'&dataTotal='+vmDetail.dataTotal;
			 
			list=serviceFactory.getDataService().getCatalogResourcesTwo(countryCode,listIndexCode,0,downloadNumber);
		}
		//AQI所有
		else if(type.equals("F1")){
//			urlStr='../Aqi/getCatalogResourcesAQI.do?countryCode='
//					+vmDetail.countryCode+'&dataTotal='+vmDetail.dataTotal
			//判断传递过来的值
			list = serviceFactory.getAqiService().getCatalogResourcesAQI(listCountry, 0,downloadNumber);
		}
		//全球重点城市年度气温情况
		else if(type.equals("F2")){
//			urlStr='../Weather/getCatalogResourcesWeatherYear.do?countryCode='
//					+vmDetail.countryCode+'&dataTotal='+vmDetail.dataTotal
			list=serviceFactory.getWeatherService().getCatalogResourcesWeatherYear(listCountry, 0,downloadNumber);
		}
		//全球重点城市实时天气情况
		else if(type.equals("F3")){
//			urlStr='../Weather/getCatalogResourcesWeatherRealtime.do?countryCode='
//					+vmDetail.countryCode+'&dataTotal='+vmDetail.dataTotal
			list=serviceFactory.getWeatherService().getCatalogResourcesWeatherRealtime(listCountry, 0,downloadNumber);
		}
		//全球重点城市天气预报情况
		else if(type.equals("F4")){
			
		}
		//全球重点河流水质信息
		else if(type.equals("F5")){
//			urlStr='../WaterQuality/getCatalogResourcesWaterQuality.do?countryCode='
//					+vmDetail.countryCode+'&waterType=R&dataTotal='+vmDetail.dataTotal
			list=serviceFactory.getWaterQualityService().getCatalogResourcesWaterQuality(listCountry, "R", 0, downloadNumber);
		}
		//全球重点湖泊水质信息
		else if(type.equals("F6")){
//			urlStr='../WaterQuality/getCatalogResourcesWaterQuality.do?countryCode='
//					+vmDetail.countryCode+'&waterType=L&dataTotal='+vmDetail.dataTotal
			list=serviceFactory.getWaterQualityService().getCatalogResourcesWaterQuality(listCountry, "L", 0, downloadNumber);
		}
		String[] Titles = titles.split(",");
		String[] Fields = fields.split(",");
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			String path = ExcelUtil.class.getClassLoader().getResource("../../").getPath() + "resources/uploadExcel/";
			Date date = new Date();
            String fileName = "Data_" + date.getTime() + ".xls";
			HSSFWorkbook wb = (HSSFWorkbook) ExcelUtil.createWorkBook(list, Fields, Titles);
			wb.write(bos);
			byte[] content = bos.toByteArray();
			is = new ByteArrayInputStream(content);
			os = new FileOutputStream(path + fileName);
			int len;
			while ((len = is.read())!=-1) {
				os.write(len);
			}
//			request.setCharacterEncoding("UTF-8");
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			Date datenow = new Date();
//			String createTime = sdf.format(datenow);
//			String datenowFile = "数据表_" + createTime + ".xls";
//			response.setContentType("application/vnd.ms-excel,charset=gbk");
//			response.setHeader(
//					"Content-Disposition",
//					"attachment;filename=\""
//							+ java.net.URLEncoder.encode(datenowFile, "UTF-8")
//							+ "\"");
//			wb.write(response.getOutputStream());
//			ExcelUtil.ComExcel(response, list, "datacell", Fields, Titles);
			returnmap.put("fileName", fileName);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				os.close();
				is.close();
			} catch (IOException e) {
			}
		}
		return JSON.toJSONString(returnmap);
	}*/
}
