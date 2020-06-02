package com.yutu.controller.office;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * 导出简报
 * @author yutu897
 *
 */
@Controller
@RequestMapping("/CreateReport")
public class CreateReportController {

/*
	*//**
	 * 根据年份和区域生成人口情况报告
	 * @param request
	 * @param map
	 *//*
	@RequestMapping(value="/CreatePopulationReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreatePopulationReport(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String orgCode = request.getParameter("orgCode");
		String countryCode = request.getParameter("countryCode");
		String indexCode = request.getParameter("indexCode"); 
		String orgName = request.getParameter("orgName");
		String year = request.getParameter("year");
		//开始年份为当前年份往前推20年
		String startYear = DateUtil.YearCal(-20);
		String endYear = year;
		String fileName = "";
		String field = "";
		Map<String,Object> returnmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		String[] strIndexCode=indexCode.split(",");
		List<String> listIndexCode=Arrays.asList(strIndexCode);
		String[] strCountryCode=countryCode.split(",");
	    List<String> listCountryCode = Arrays.asList(strCountryCode);
	    //组织信息
	    TCodOrganization  orgInfo= serviceFactory.getOrgCountryService().getOrgEnByOrg(orgCode);
	    List<Map<String, Object>> listData=serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode,listIndexCode,startYear,endYear);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("year",year);
		datamap.put("startYear", startYear);
		datamap.put("endYear", endYear);
		datamap.put("orgName", orgName);
		//获得startYear到endYear人口趋势图的数据
		LineChart linedata = getLineChartData(startYear,endYear,listCountryCode,"R007");
		//得到JFreeChart
		JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false,"年份","数量（亿人）");
		//将JFreeChart转换成流
		InputStream  inLine = JfreeChartUtil.getChartStream(linechart, 1000, 800);
		//生成图片
		datamap.put("pic2", WordUtils.getImageInput(inLine));
		//获得startYear到endYear人口趋势图的数据
		BarChart bardata = getBarChartData(year,listCountryCode,"R007");
		//得到JFreeChart
		JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false,"年份","数量（亿人）");
		//将JFreeChart转换成流
		InputStream  inBar = JfreeChartUtil.getChartStream(barchart, 1000, 600);
		datamap.put("pic1", WordUtils.getImageInput(inBar));
		BigDecimal startYearPopulationTotal = null;
		BigDecimal startYearPopulationDensity = null;
		BigDecimal endYearPopulationTotal = null;
		BigDecimal endYearPopulationDensity = null;
		double populationTotalDifference = 0;
		double populationDensityDifference = 0;
		for(Map m : listData){
			String datayear = (String) m.get("DATA_YEAR");
			if(year.equals(datayear)){
				//国家三级编码
				String codeThree = (String) m.get("CODE_THREE");
				//国家中文名
				String countryName = (String)m.get("NAME_CN");
				//人口总量
				double ptValue = (double) m.get("R007");
				//将人口数量转为以亿为单位
				ptValue = ptValue/100000000;
				//保留两位小数
				 BigDecimal valueBigOne = new BigDecimal(ptValue);
				 ptValue = valueBigOne.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(ptValue==0)
				 {
					 ptValue = valueBigOne.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				String populationTotal = String.valueOf(ptValue);
				//人口密度
				double pdValue = (double) m.get("R012");
				//保留两位小数
				 BigDecimal valueBigTwo = new BigDecimal(pdValue);
				 pdValue = valueBigTwo.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(pdValue==0)
				 {
					 pdValue = valueBigTwo.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				String populationDensity = String.valueOf(pdValue);
 				 
				//查询开始年份的人口总量和人口密度
				startYearPopulationTotal = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R007")));
				startYearPopulationDensity = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R012")));
				//查询结束年份的人口总量和人口密度
				endYearPopulationTotal = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R007")));
				endYearPopulationDensity = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R012")));
				//计算开始年份和结束年份的GDP总量和人均GDP的差额
				if(startYearPopulationTotal!=null&&endYearPopulationTotal!=null){
					populationTotalDifference = endYearPopulationTotal.subtract(startYearPopulationTotal).doubleValue();
				}
				if(startYearPopulationDensity!=null&&endYearPopulationDensity!=null){
					populationDensityDifference = endYearPopulationDensity.subtract(startYearPopulationDensity).doubleValue();
				}
				Map<String,Object> reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("Population");
				field = (String)reportMap.get("field");
				String desc = (String)reportMap.get("reportDesc");
				desc = desc.replace("${year}", year);
				desc = desc.replace("${countryName}", countryName);
				desc = desc.replace("${populationTotal}", populationTotal);
				desc = desc.replace("${populationDensity}", populationDensity);
				desc = desc.replace("${startYear}", startYear);
				desc = desc.replace("${endYear}",endYear);
				if(populationTotalDifference>0){
					desc = desc.replace("${populationTotalTrend}", "上涨");
				}else if(populationTotalDifference<0){
					desc = desc.replace("${populationTotalTrend}", "下降");
				}
				if(populationDensityDifference>0){
					desc = desc.replace("${populationDensityTrend}", "上涨");
				}else if(populationDensityDifference<0){
					desc = desc.replace("${populationDensityTrend}", "下降");
				}
				sb.append(desc);
			}
		}
		datamap.put(field, sb);
		datamap.put("objs", listData);
		try {
			fileName = WordUtils.exportWordReport(request,response,datamap,orgInfo.getOrgNameEn()+"_"+year+"_Population","Population.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		returnmap.put("fileName", fileName);
		return JSON.toJSONString(returnmap);
	}
	
	*//**
	 * 根据年份和区域生成GDP报告
	 * @param request
	 * @param map
	 *//*
	@RequestMapping(value="/CreateGDPReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreateGDPReport(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String orgCode = request.getParameter("orgCode");
		String countryCode = request.getParameter("countryCode");
		String indexCode = request.getParameter("indexCode"); 
		String orgName = request.getParameter("orgName");
		String year = request.getParameter("year");
		//开始年份为当前年份往前推20年
		String startYear = DateUtil.YearCal(-20);
		String endYear = year;
		String fileName = "";
		String field = "";
		Map<String,Object> returnmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		String[] strIndexCode=indexCode.split(",");
		List<String> listIndexCode=Arrays.asList(strIndexCode);
		String[] strCountryCode=countryCode.split(",");
	    List<String> listCountryCode = Arrays.asList(strCountryCode);
	    //组织信息
	    TCodOrganization  orgInfo= serviceFactory.getOrgCountryService().getOrgEnByOrg(orgCode);
		List<Map<String, Object>> listData=serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode,listIndexCode,startYear,endYear);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("year",year);
		datamap.put("startYear", startYear);
		datamap.put("endYear", endYear);
		datamap.put("orgName", orgName);
		//获得startYear到endYear人口趋势图的数据
		LineChart linedata = getLineChartData(startYear,endYear,listCountryCode,"R013");
		//得到JFreeChart
		JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false,"年份","数量（千亿美元）");
		//将JFreeChart转换成流
		InputStream  inLine = JfreeChartUtil.getChartStream(linechart, 1000, 800);
		//生成图片
		datamap.put("pic2", WordUtils.getImageInput(inLine));
		//获得startYear到endYear人口趋势图的数据
		BarChart bardata = getBarChartData(year,listCountryCode,"R013");
		//得到JFreeChart
		JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false,"年份","数量（千亿美元）");
		//将JFreeChart转换成流
		InputStream  inBar = JfreeChartUtil.getChartStream(barchart, 1000, 600);
		datamap.put("pic1", WordUtils.getImageInput(inBar));
		BigDecimal startYearGDP = null;
		BigDecimal startYearPCGDP = null;
		BigDecimal endYearGDP = null;
		BigDecimal endYearPCGDP = null;
		double GDPDifference = 0;
		double PCGDPDifference = 0;
		for(Map m : listData){
			String datayear = (String) m.get("DATA_YEAR");
			if(year.equals(datayear)){
				//国家名称的三级编码
				String codeThree = (String)m.get("CODE_THREE");
				//国家中文名
				String countryName = (String)m.get("NAME_CN");
				//GDP总量
				double GDPValue = (double) m.get("R013");
				GDPValue = GDPValue/100000000000L;
				BigDecimal valueBigOne = new BigDecimal(GDPValue);
				GDPValue = valueBigOne.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(GDPValue==0)
				 {
					 GDPValue = valueBigOne.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				 String GDPTotal = String.valueOf(GDPValue);
				//人均GDP
				double PCGDPValue = (double) m.get("R014");
				PCGDPValue = PCGDPValue/10000;
				BigDecimal valueBigTwo = new BigDecimal(PCGDPValue);
				PCGDPValue = valueBigTwo.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(PCGDPValue==0)
				 {
					 PCGDPValue = valueBigTwo.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				 String PCGDP = String.valueOf(PCGDPValue);
				//查询开始年份的GDP总量和人均GDP
				startYearGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R013")));
				startYearPCGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R014")));
				//查询结束年份的GDP总量和人均GDP
				endYearGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R013")));
				endYearPCGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R014")));
				//计算开始年份和结束年份的GDP总量和人均GDP的差额
				if(endYearGDP!=null&&startYearGDP!=null){
					GDPDifference = endYearGDP.subtract(startYearGDP).doubleValue();
				}
				if(endYearPCGDP!=null&&startYearPCGDP!=null){
					PCGDPDifference = endYearPCGDP.subtract(startYearPCGDP).doubleValue();
				}
				Map<String,Object> reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("GDP");
				field = (String)reportMap.get("field");
				String desc = (String)reportMap.get("reportDesc");
				desc = desc.replace("${year}", year);
				desc = desc.replace("${countryName}", countryName);
				desc = desc.replace("${GDPTotal}", GDPTotal);
				desc = desc.replace("${PCGDP}", PCGDP);
				desc = desc.replace("${startYear}", startYear);
				desc = desc.replace("${endYear}",endYear);
				if(GDPDifference>0){
					desc = desc.replace("${GDPTrend}", "上涨");
				}else if(GDPDifference<0){
					desc = desc.replace("${GDPTrend}", "下降");
				}
				if(PCGDPDifference>0){
					desc = desc.replace("${PCGDPTrend}", "上涨");
				}else if(PCGDPDifference<0){
					desc = desc.replace("${PCGDPTrend}", "下降");
				}
				sb.append(desc);
			}
		}
		datamap.put(field, sb);
		datamap.put("objs", listData);
		try {
			fileName = WordUtils.exportWordReport(request,response,datamap,orgInfo.getOrgNameEn()+"_"+year+"_GDP","GDP.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		returnmap.put("fileName", fileName);
		return JSON.toJSONString(returnmap);
	}
	
	
	
	*//**
	 * 根据年份和区域生成AQI报告
	 * @param request
	 * @param map
	 * @throws ParseException 
	 *//*
	@RequestMapping(value="/CreateAQIReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreateAQIReport(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws ParseException{
		String orgCode = request.getParameter("orgCode");
		String countryCode = request.getParameter("countryCode");
		String busiName = request.getParameter("busiName");   //AQI指标值 AQI  PM25
		String orgName = request.getParameter("orgName");
		String dateTime = request.getParameter("dateTime");
		String dateRange = request.getParameter("dateRange");  //范围
		//开始年份为当前天份往前推30天
		String startDateTime = DateUtil.DayCal(Integer.parseInt(dateRange));
		String endDateTime = dateTime;
		String fileName = "";
		String field = "";
		Map<String,Object> returnmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		
		String[] strCountryCode=countryCode.split(",");
	    List<String> listCountryCode = Arrays.asList(strCountryCode);
	    //组织信息代码
	    TCodOrganization  orgInfo= serviceFactory.getOrgCountryService().getOrgEnByOrg(orgCode);
		//国家日平均数据
		List<TBasAqi> listData=serviceFactory.getAqiService().getAqiAvgByOrgDateWork(orgCode,dateTime, busiName);
		//国家平均数据  历史
		List<TBasAqi> listDataEnd=serviceFactory.getAqiService().getAqiAvgByOrgDateWork(orgCode,startDateTime, busiName);
		//监测点个数
		List<Map<String, Object>>  listDataCityInfo=serviceFactory.getAqiService().getAqiByCountryDate(listCountryCode, dateTime);
		//监测点 历史数据
		List<Map<String, Object>>  listDataCityInfoEnd=serviceFactory.getAqiService().getAqiByCountryDate(listCountryCode, startDateTime);
		
		List<Map<String, Object>>  dataListAQI = serviceFactory.getAqiService().getAqiDayCatalogResources(orgCode, dateTime, dateRange);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("dateTime",dateTime);
		datamap.put("startDateTime", startDateTime);
		datamap.put("endDateTime", endDateTime);
		datamap.put("orgName", orgName);
		//获得startYear到endYear人口趋势图的数据
		LineChart linedata = getAQILineChartData(dateTime,busiName,listCountryCode,dateRange);
		//得到JFreeChart
		JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false,"时间",busiName+"数值");
		//将JFreeChart转换成流
		InputStream  inLine = JfreeChartUtil.getChartStream(linechart, 1000, 800);
		//生成图片
		datamap.put("pic2", WordUtils.getImageInput(inLine));
		//获得startYear到endYear人口趋势图的数据
		BarChart bardata = getAQIBarChartData(dateTime,orgCode,busiName);
		//得到JFreeChart
		JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false,"时间",busiName+"数值");
		//将JFreeChart转换成流
		InputStream  inBar = JfreeChartUtil.getChartStream(barchart, 1000, 600);
		datamap.put("pic1", WordUtils.getImageInput(inBar));
		double AQIDifference = 0;
		double AQIDensityTrend = 0;
		for(TBasAqi aqi : listData){
				//国家名称的三级编码
				String codeThree = aqi.getCityNameEn();
				//国家中文名
				String countryName = aqi.getCityNameCn();
				//AQI总量
				double AQIValue = Double.parseDouble(aqi.getAqi());
				//污染类型
				String pollutionType="";
				 if(AQIValue <= 0) {
					 	pollutionType="无";
					} else if (AQIValue <= 50) {
						pollutionType="优";
					} else if (AQIValue <= 100) {
						pollutionType="良";
					} else if (AQIValue <= 150) {
						pollutionType="轻度污染";
					} else if (AQIValue <= 200) {
						pollutionType="中度污染";
					} else if (AQIValue <= 300) {
						pollutionType="重度污染";
					} else if (AQIValue > 300) {
						pollutionType="严重污染";
					}
				 //监测点总数
				 double cityCount=0;
				 //一类数据
				 double valueOne=0;
				 //二类数据
				 double valueTwo=0;
				 //三类数据
				 double valueThr=0;
				 //四类数据
				 double valueFou=0;
				 for (Map<String, Object> mapDian : listDataCityInfo) {
					 if(aqi.getCityNameEn().equals((String) mapDian.get("CODE_THREE")))
					 {
						 cityCount++; //国家个数增加
						 if(mapDian.get("AQI").toString().length()>0)
						 {
						 double aqiValueTwo=Double.parseDouble(mapDian.get("AQI").toString());
						 if (aqiValueTwo <= 100) {
							 valueOne++;
							} else if (aqiValueTwo <= 150) {
								valueTwo++;
							} else if (aqiValueTwo <= 200) {
								valueThr++;
							} else if (aqiValueTwo > 200) {
								valueFou++;
							}
						 }
					 }
				 }
				 
				 //监测点总数
				 String strCityCount=cityCount+"个";
				 double douValueOne=0;
				 double douValueTwo=0;
				 double douValueThr=0;
				 double douValueFou=0;
				 String strValueOne="0%";
				 String strValueTwo="0%";
				 String strValueThr="0%";
				 String strValueFou="0%";
				 //优良
				 if(cityCount>0)
				 {
				 douValueOne=(valueOne/cityCount)*100;
				 BigDecimal bigDouValueOne = new BigDecimal(douValueOne);
				 douValueOne = bigDouValueOne.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 strValueOne=douValueOne+"%";
				 //轻度
				 douValueTwo=(valueTwo/cityCount)*100;
				 BigDecimal bigDouValueTwo = new BigDecimal(douValueTwo);
				 douValueTwo = bigDouValueTwo.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 			 
				 strValueTwo=douValueTwo+"%";
				 //中度
				 douValueThr=(valueThr/cityCount)*100;
				 BigDecimal bigDouValueThr = new BigDecimal(douValueThr);
				 douValueThr = bigDouValueThr.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 				 
				 strValueThr=douValueThr+"%";
				 //重度
				 douValueFou=(valueFou/cityCount)*100;
				 BigDecimal bigDouValueFou = new BigDecimal(douValueFou);
				 douValueFou = bigDouValueFou.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();  
				 strValueFou=douValueFou+"%";
				 }
				 //趋势
				 double aqiEndValue=0;
				 for (TBasAqi tBasAqi : listDataEnd) {
					if(aqi.getCityNameEn().equals(tBasAqi.getCityNameEn()))
					{
						aqiEndValue=Double.parseDouble(tBasAqi.getAqi());
					}
				 }
				 AQIDifference =AQIValue-aqiEndValue;
				
				 //趋势百分百
				 double valueOneLog=0;
				 double cityCountLog=0;
				 for (Map<String, Object> mapDianEnd : listDataCityInfoEnd) {
					 if(aqi.getCityNameEn().equals((String) mapDianEnd.get("CODE_THREE")))
					 {
						 cityCountLog++; //国家个数增加
						 if(mapDianEnd.get("AQI").toString().length()>0)
						 {
						 double aqiValueTwo=Double.parseDouble(mapDianEnd.get("AQI").toString());
						 if (aqiValueTwo <= 100) {
							 valueOneLog++;
							} 
						 }
					 }
				 }
				 //计算百分比
				 double douValueOneLog=0;
				 if(cityCountLog>0)
				 {
					 douValueOneLog=(valueOneLog/cityCountLog)*100;
					 BigDecimal bigDouValueOneLog = new BigDecimal(douValueOneLog);
					 douValueOneLog = bigDouValueOneLog.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 }
				 AQIDensityTrend=douValueOne-douValueOneLog;
				 
				Map<String,Object> reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("AQI");
				field = (String)reportMap.get("field");
				String desc = (String)reportMap.get("reportDesc");
				desc = desc.replace("${dataTime}", dateTime);
				desc = desc.replace("${countryName}", countryName);
				desc = desc.replace("${busiName}", busiName);
				desc = desc.replace("${AQITotal}", AQIValue+"");
				desc = desc.replace("${pollutionType}", pollutionType);
				desc = desc.replace("${cityCount}", strCityCount);
				desc = desc.replace("${valueOne}", strValueOne);
				desc = desc.replace("${valueTwo}", strValueTwo);
				desc = desc.replace("${valueThr}",strValueThr);
				desc = desc.replace("${valueFou}",strValueFou);
				desc = desc.replace("${startDateTime}",startDateTime);
				desc = desc.replace("${endDateTime}",endDateTime);
				
				if(AQIDifference>0){
					desc = desc.replace("${AQITotalTrend}", "上涨趋势");
				}else if(AQIDifference<0){
					desc = desc.replace("${AQITotalTrend}", "下降趋势");
				}
				else{
					desc = desc.replace("${AQITotalTrend}", "稳定");
				}
				if(AQIDensityTrend>0){
					desc = desc.replace("${AQIDensityTrend}", "上涨趋势");
				}else if(AQIDensityTrend<0){
					desc = desc.replace("${AQIDensityTrend}", "下降趋势");
				}
				else
				{
					desc = desc.replace("${AQIDensityTrend}", "稳定");
				}
				sb.append(desc);
		}
		datamap.put(field, sb);
		datamap.put("objs", dataListAQI);
		try {
			fileName = WordUtils.exportWordReport(request,response,datamap,orgInfo.getOrgNameEn()+"_"+dateTime+"_AQI","AQI.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		returnmap.put("fileName", fileName);
		return JSON.toJSONString(returnmap);
	}
	
	*//**
	 * 根据年份和区域生成GDP报告
	 * @param request
	 * @param map
	 *//*
	@RequestMapping(value="/CreateTemperatureReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreateTemperatureReport(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String orgCode = request.getParameter("orgCode");
		String countryCode = request.getParameter("countryCode");
		String indexCode = request.getParameter("indexCode"); 
		String orgName = request.getParameter("orgName");
		String year = request.getParameter("year");
		//开始年份为当前年份往前推20年
		String startYear = DateUtil.YearCal(-20);
		String endYear = year;
		String fileName = "";
		String field = "";
		Map<String,Object> returnmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		String[] strIndexCode=indexCode.split(",");
		List<String> listIndexCode=Arrays.asList(strIndexCode);
		String[] strCountryCode=countryCode.split(",");
	    List<String> listCountryCode = Arrays.asList(strCountryCode);
	    //组织信息
	    TCodOrganization  orgInfo= serviceFactory.getOrgCountryService().getOrgEnByOrg(orgCode);
		List<Map<String, Object>> listData=serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode,listIndexCode,startYear,endYear);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("year",year);
		datamap.put("startYear", startYear);
		datamap.put("endYear", endYear);
		datamap.put("orgName", orgName);
		//获得startYear到endYear人口趋势图的数据
		LineChart linedata = getLineChartData(startYear,endYear,listCountryCode,"R013");
		//得到JFreeChart
		JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false,"年份","数量（千亿美元）");
		//将JFreeChart转换成流
		InputStream  inLine = JfreeChartUtil.getChartStream(linechart, 1000, 800);
		//生成图片
		datamap.put("pic2", WordUtils.getImageInput(inLine));
		//获得startYear到endYear人口趋势图的数据
		BarChart bardata = getBarChartData(year,listCountryCode,"R013");
		//得到JFreeChart
		JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false,"年份","数量（千亿美元）");
		//将JFreeChart转换成流
		InputStream  inBar = JfreeChartUtil.getChartStream(barchart, 1000, 600);
		datamap.put("pic1", WordUtils.getImageInput(inBar));
		BigDecimal startYearGDP = null;
		BigDecimal startYearPCGDP = null;
		BigDecimal endYearGDP = null;
		BigDecimal endYearPCGDP = null;
		double GDPDifference = 0;
		double PCGDPDifference = 0;
		for(Map m : listData){
			String datayear = (String) m.get("DATA_YEAR");
			if(year.equals(datayear)){
				//国家名称的三级编码
				String codeThree = (String)m.get("CODE_THREE");
				//国家中文名
				String countryName = (String)m.get("NAME_CN");
				//GDP总量
				double GDPValue = (double) m.get("R013");
				GDPValue = GDPValue/100000000000L;
				BigDecimal valueBigOne = new BigDecimal(GDPValue);
				GDPValue = valueBigOne.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(GDPValue==0)
				 {
					 GDPValue = valueBigOne.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				 String GDPTotal = String.valueOf(GDPValue);
				//人均GDP
				double PCGDPValue = (double) m.get("R014");
				PCGDPValue = PCGDPValue/10000;
				BigDecimal valueBigTwo = new BigDecimal(PCGDPValue);
				PCGDPValue = valueBigTwo.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				 if(PCGDPValue==0)
				 {
					 PCGDPValue = valueBigTwo.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
				 }
				 String PCGDP = String.valueOf(PCGDPValue);
				//查询开始年份的GDP总量和人均GDP
				startYearGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R013")));
				startYearPCGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(startYear,codeThree,"R014")));
				//查询结束年份的GDP总量和人均GDP
				endYearGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R013")));
				endYearPCGDP = new BigDecimal(Double.toString(serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(endYear,codeThree,"R014")));
				//计算开始年份和结束年份的GDP总量和人均GDP的差额
				if(endYearGDP!=null&&startYearGDP!=null){
					GDPDifference = endYearGDP.subtract(startYearGDP).doubleValue();
				}
				if(endYearPCGDP!=null&&startYearPCGDP!=null){
					PCGDPDifference = endYearPCGDP.subtract(startYearPCGDP).doubleValue();
				}
				Map<String,Object> reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("GDP");
				field = (String)reportMap.get("field");
				String desc = (String)reportMap.get("reportDesc");
				desc = desc.replace("${year}", year);
				desc = desc.replace("${countryName}", countryName);
				desc = desc.replace("${GDPTotal}", GDPTotal);
				desc = desc.replace("${PCGDP}", PCGDP);
				desc = desc.replace("${startYear}", startYear);
				desc = desc.replace("${endYear}",endYear);
				if(GDPDifference>0){
					desc = desc.replace("${GDPTrend}", "上涨");
				}else if(GDPDifference<0){
					desc = desc.replace("${GDPTrend}", "下降");
				}
				if(PCGDPDifference>0){
					desc = desc.replace("${PCGDPTrend}", "上涨");
				}else if(PCGDPDifference<0){
					desc = desc.replace("${PCGDPTrend}", "下降");
				}
				sb.append(desc);
			}
		}
		datamap.put(field, sb);
		datamap.put("objs", listData);
		try {
			fileName = WordUtils.exportWordReport(request,response,datamap,orgInfo.getOrgNameEn()+"_"+year+"_GDP","GDP.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		returnmap.put("fileName", fileName);
		return JSON.toJSONString(returnmap);
	}
	
	*//**
	 * 测试
	 * @param request
	 * @param map
	 *//*
	@RequestMapping(value="/CreateCSReport",produces="application/json;charset=utf-8",method=RequestMethod.POST)
	@ResponseBody
	public String CreateCSReport(HttpServletRequest request, HttpServletResponse response, ModelMap map){
		String orgCode = request.getParameter("orgCode");
		String countryCode = request.getParameter("countryCode");
		String indexCode = request.getParameter("indexCode"); 
		String orgName = request.getParameter("orgName");
		String year = request.getParameter("year");
		//开始年份为当前年份往前推20年
		String startYear = DateUtil.YearCal(-20);
		String endYear = year;
		String fileName = "";
		String field = "";
		Map<String,Object> returnmap = new HashMap<String, Object>();
		StringBuffer sb = new StringBuffer();
		String[] strIndexCode=indexCode.split(",");
		List<String> listIndexCode=Arrays.asList(strIndexCode);
		String[] strCountryCode=countryCode.split(",");
	    List<String> listCountryCode = Arrays.asList(strCountryCode);
		String picurl = WordUtils.class.getClassLoader().getResource("../../").getPath() + "resources/images/reportImages/";
		List<Map<String, Object>> listData=serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode,listIndexCode,startYear,endYear);
		Map<String, Object> datamap = new HashMap<String, Object>();
		datamap.put("year",year);
		datamap.put("startYear", startYear);
		datamap.put("endYear", endYear);
		datamap.put("orgName", orgName);
		//获得startYear到endYear人口趋势图的数据
		LineChart linedata = getLineChartData(startYear,endYear,listCountryCode,"R007");
		//得到JFreeChart
		JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false,"年份","数量");
		//将JFreeChart转换成流
		InputStream  inLine = JfreeChartUtil.getChartStream(linechart, 500, 200);
		//生成图片
		datamap.put("pic1", WordUtils.getImageInput(inLine));
		//获得startYear到endYear人口趋势图的数据
		BarChart bardata = getBarChartData(year,listCountryCode,"R007");
		//得到JFreeChart
		JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false,"年份","数量");
		//将JFreeChart转换成流
		InputStream  inBar = JfreeChartUtil.getChartStream(barchart, 500, 500);
		datamap.put("pic2", WordUtils.getImageInput(inBar));
		for(Map m : listData){
			String datayear = (String) m.get("DATA_YEAR");
			if(year.equals(datayear)){
				//国家中文名
				String countryName = (String)m.get("NAME_CN");
				//GDP总量
				String GDPTotal = String.valueOf(m.get("R013"));
				//人均GDP
				String pcGDP = String.valueOf(m.get("R014"));
				Map<String,Object> reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("GDP");
				field = (String)reportMap.get("field");
				String desc = (String)reportMap.get("reportDesc");
				String str1 = desc.replace("${year}", year);
				String str2 = str1.replace("${countryName}", countryName);
				String str3 = str2.replace("${GDPTotal}", GDPTotal);
				String str4 = str3.replace("${pcGDP}", pcGDP);
				sb.append(str4);
			}
		}
		datamap.put(field, sb);
		datamap.put("objs", listData);
		try {
			fileName = WordUtils.exportWordReport(request,response,datamap,orgCode+"_"+year+"_GDP","GDP.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}    
		returnmap.put("fileName", fileName);
		return JSON.toJSONString(returnmap);
	}
	
	private BarChart getBarChartData(String year,List<String> listCountryCode, String indexCode) {
		BarChart data = new BarChart();
		//图例显示的数据
		List<String> navdata = new ArrayList<String>();
		//X轴显示的数据
		List<String> xAxisdata = new ArrayList<String>();
		String yearValue = String.valueOf(year);
		xAxisdata.add(yearValue);
		List<OData> opinionData = new ArrayList<OData>();
		for(String code : listCountryCode){
			try {
			double value = serviceFactory.getDataService().getYearDataByCountryCodeAndIndexCode(year,code,indexCode);
			//设置中文
			String codeName = serviceFactory.getDataService().getNameCnByCountryCodeAndIndexCode(year,code,indexCode);
			navdata.add(codeName);
			//折线图数据（单条）
			List<Double> odata = new ArrayList<Double>();
			 //切换单位
			 switch (indexCode) {
			 case "R007": //调整单位 亿
					//将人口数量转为以亿为单位
				value = value/100000000;
				break;
			 case "R013":
				    value = value/100000000000L;
				break;
			 case "R014":
					value = value/10000;
				break;
			 }
			 BigDecimal valueBig = new BigDecimal(value);
			 value = valueBig.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
			 if(value==0)
			 {
			    	value = valueBig.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
			 }
			odata.add(value);
			OData Odata = new OData(odata);
			opinionData.add(Odata);
			} catch (Exception e) {
				continue;
			}
		}
		data.setNavdata(navdata);
		data.setxAxisdata(xAxisdata);
		data.setOpinionData(opinionData);
		return data;
	 }
 
	
	*//**
	 * 填充折线图的数据
	 * @return
	 *//*
	private LineChart getLineChartData(String startYear, String endYear,
			List<String> listCountryCode, String indexCode) {
		LineChart data = new LineChart();
		//图例显示的数据
		List<String> navdata = new ArrayList<String>();
		//X轴显示的数据
		List<String> xAxisdata = new ArrayList<String>();
		int startYearValue = Integer.parseInt(startYear);
		int endyYearValue = Integer.parseInt(endYear);
		for(int i=startYearValue;i<=endyYearValue;i++){
			String yearValue = String.valueOf(i);
			xAxisdata.add(yearValue);
		}
		//折线图的数据（多条）
		List<OData> opinionData = new ArrayList<OData>();
		for(String code : listCountryCode){
			List<Map<String, Object>> datamap = serviceFactory.getDataService().getDataByCountryCodeAndIndexCodeBetweenYear(startYear,endYear,code,indexCode);
			
			if(datamap.size()>0)
			{
			String countryName = datamap.get(0).get("countryName").toString();
			navdata.add(countryName);
			//折线图数据（单条）
			List<Double> odata = new ArrayList<Double>();
			//如果为空 补零
			int numLength=endyYearValue-startYearValue;
			for(int i=0;i<=numLength;i++){
				if(i<=datamap.size()-1){
				int year = Integer.valueOf((String) datamap.get(i).get("dataYear"));
				int yearX=startYearValue+i;
				if(year==yearX)
				{
					Double value = Double.valueOf((String) datamap.get(i).get("dataValue"));			   
				    //切换单位
				    switch (indexCode) {
					case "R007": //调整单位 亿
						//将人口数量转为以亿为单位
						value = value/100000000;
						break;
					case "R013":
					    value = value/100000000000L;
						break;
					case "R014":
						value = value/10000;
					break;
					}
				    BigDecimal valueBig = new BigDecimal(value);
				    value = valueBig.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue(); 
				    if(value==0)
					 {
				    	value = valueBig.setScale(2, BigDecimal.ROUND_UP).doubleValue(); 
					 }
					odata.add(value);
					
				}
				else {
					Double value =Double.valueOf(0);
					odata.add(value);
					i--;
					numLength=numLength-1;
				}
				}
			}
			OData Odata = new OData(odata);
			opinionData.add(Odata);
			}
		}
		data.setNavdata(navdata);
		data.setxAxisdata(xAxisdata);
		data.setOpinionData(opinionData);
		return data;
	 }
	
	
	

	*//**
	 * 填充AQI折线图的数据
	 * @return
	 * @throws ParseException 
	 *//*
	private LineChart getAQILineChartData(String dateTime, String busiName,
			List<String> listCountryCode, String dateRange) throws ParseException {
		LineChart data = new LineChart();
		//图例显示的数据
		List<String> navdata = new ArrayList<String>();
		//X轴显示的数据
		List<String> xAxisdata = new ArrayList<String>();
		 int dateRangeLength=Integer.parseInt(dateRange);
		for(int i=0;i>=dateRangeLength;i--){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	        Date dt = sdf.parse(dateTime);  
	        Calendar rightNow = Calendar.getInstance();  
	        rightNow.setTime(dt);  
	        rightNow.add(Calendar.DATE, i);  
	        Date dt1 = rightNow.getTime();  
	        String reStr = sdf.format(dt1);  
			xAxisdata.add(reStr);
		}
		//折线图的数据（多条）
		List<OData> opinionData = new ArrayList<OData>();
		for(String code : listCountryCode){
			List<String> listCode=new ArrayList<String>();
			listCode.add(code);
			List<TBasAqi> datamap = serviceFactory.getAqiService().getAqiDayByCountryDateWork(listCode, busiName, dateTime,dateRange);
			if(datamap.size()>0)
			{
			String countryName = datamap.get(0).getCityNameCn();
			navdata.add(countryName);
			//折线图数据（单条）
			List<Double> odata = new ArrayList<Double>();
			 for(TBasAqi aqi : datamap){
			    double value=0;
			    switch (busiName) {
				case "AQI":
					value = Double.parseDouble(aqi.getAqi());
					odata.add(value);
					break;
				case "PM25":
					value = Double.parseDouble(aqi.getPm25());
					odata.add(value);
					break;
				case "PM10":
					value = Double.parseDouble(aqi.getPm10());
					odata.add(value);
					break;
				case "O3":
					value = Double.parseDouble(aqi.getO3());
					odata.add(value);
					break;
				case "NO2":
					value = Double.parseDouble(aqi.getNo2());
					odata.add(value);
					break;
				case "SO2":
					value = Double.parseDouble(aqi.getSo2());
					odata.add(value);
					break;
				case "CO":
					value = Double.parseDouble(aqi.getCo());
					odata.add(value);
					break;
				}		
				
			    
			} 
			OData Odata = new OData(odata);
		    opinionData.add(Odata);
			}
		}
		data.setNavdata(navdata);
		data.setxAxisdata(xAxisdata);
		data.setOpinionData(opinionData);
		return data;
	 }
	
	
	*//***
	 * AQI柱状图生成
	 * @author:zhaobc
	 * @param year
	 * @param listCountryCode
	 * @param indexCode
	 * @return
	 * @creatTime:2018-10-29 - 下午1:34:22
	 *//*
	private BarChart getAQIBarChartData(String dateTime , String orgName, String busiName) {
		BarChart data = new BarChart();
		//图例显示的数据
		List<String> navdata = new ArrayList<String>();
		//X轴显示的数据
		List<String> xAxisdata = new ArrayList<String>();
		String yearValue = String.valueOf(dateTime);
		xAxisdata.add(yearValue);
		List<OData> opinionData = new ArrayList<OData>();
		List<TBasAqi> aqiList = serviceFactory.getAqiService().getAqiAvgByOrgDateWork(orgName, dateTime, busiName);
		for(TBasAqi aqi : aqiList){ 
			navdata.add(aqi.getCityNameCn());
			//折线图数据（单条）
			List<Double> odata = new ArrayList<Double>();
		    double value=0;
			 //切换单位
			 switch (busiName) {
				case "AQI":
					value = Double.parseDouble(aqi.getAqi());
					odata.add(value);
					break;
				case "PM25":
					value = Double.parseDouble(aqi.getPm25());
					odata.add(value);
					break;
				case "PM10":
					value = Double.parseDouble(aqi.getPm10());
					odata.add(value);
					break;
				case "O3":
					value = Double.parseDouble(aqi.getO3());
					odata.add(value);
					break;
				case "NO2":
					value = Double.parseDouble(aqi.getNo2());
					odata.add(value);
					break;
				case "SO2":
					value = Double.parseDouble(aqi.getSo2());
					odata.add(value);
					break;
				case "CO":
					value = Double.parseDouble(aqi.getCo());
					odata.add(value);
					break;
			 }
			OData Odata = new OData(odata);
			opinionData.add(Odata);
		}
		data.setNavdata(navdata);
		data.setxAxisdata(xAxisdata);
		data.setOpinionData(opinionData);
		return data;
	 }*/
	

}
