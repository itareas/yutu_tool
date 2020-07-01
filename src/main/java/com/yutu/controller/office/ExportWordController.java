package com.yutu.controller.office;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.yutu.entity.chart.ChartData;
import org.jfree.chart.JFreeChart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RestController;

/**
 * 导出总报告
 *
 * @author:zhaobc
 * @creatTime:2018-11-8-上午11:33:08
 * @description:
 **/
@RestController
@RequestMapping("/export/word")
public class ExportWordController {
//
//    //设置主键值对替换对象
//    private Map<String, Object> dataMap = new HashMap<String, Object>();
//
//    /**
//     * 生成国家情况报告
//     *
//     * @param request
//     * @param
//     */
//    @RequestMapping(value = "/start", produces = "application/json;charset=utf-8")
//    public String start(HttpServletRequest request, HttpServletResponse response) {
//        String year = request.getParameter("year");
//        String countryCode = request.getParameter("countryCode");
//        String countryName = request.getParameter("countryName");
//        String fileName = "";
//        //开始年份为当前年份往前推20年
//        String startYear = String.valueOf(Integer.parseInt(year) - 18);
//        String endYear = year;
//        //设置返回的对象
//        Map<String, Object> returnMap = new HashMap<String, Object>();
//        //开始替换占位符
//        dataMap.put("startYear", "2000");
//        dataMap.put("endYear", "2020");
//        dataMap.put("orgName", "中国");
//        //详细介绍
//        List<String> listCountryCode = new ArrayList<String>();
//        listCountryCode.add(countryCode);
//        List<String> listIndexCode = new ArrayList<String>();
//        List<String> soilIndexCode = new ArrayList<String>();
//        soilIndexCode.add("R164");
//        soilIndexCode.add("R165");
//        soilIndexCode.add("R166");
//        soilIndexCode.add("R167");
//        soilIndexCode.add("R168");
//        soilIndexCode.add("R169");
//        soilIndexCode.add("R170");
//        soilIndexCode.add("R171");
//        soilIndexCode.add("R172");
//        //受威胁物种图1
//        List<String> dangerIndexCode = new ArrayList<String>();
//        dangerIndexCode.add("R149");
//        dangerIndexCode.add("R150");
//        dangerIndexCode.add("R146");
//        dangerIndexCode.add("R151");
//        dangerIndexCode.add("R147");
//        dangerIndexCode.add("R148");
//        dangerIndexCode.add("R152");
//        dangerIndexCode.add("R153");
//
//        //数据计算
//        List<Map<String, Object>> listWeatherYearT = this.serviceFactory.getWeatherService().getWeatherYearByCountryIndexRange(listCountryCode, "T", startYear, endYear);
//        List<Map<String, Object>> listWeatherMonthT = this.serviceFactory.getWeatherService().getWeatherMonthByCountryIndex(listCountryCode, "T", listWeatherYearT.get(listWeatherYearT.size() - 1).get("year").toString());
//        List<Map<String, Object>> listWeatherYearP = this.serviceFactory.getWeatherService().getWeatherYearByCountryIndexRange(listCountryCode, "P", startYear, endYear);
//        List<Map<String, Object>> listWeatherMonthP = this.serviceFactory.getWeatherService().getWeatherMonthByCountryIndex(listCountryCode, "P", listWeatherYearP.get(listWeatherYearP.size() - 1).get("year").toString());
//        List<Map<String, Object>> listDataPopulation = serviceFactory.getDataService().getDataByCountryCodeAndIndexCodeBetweenYear(startYear, endYear, countryCode, "R007");
//        List<Map<String, Object>> listGDP = serviceFactory.getDataService().getDataByCountryCodeAndIndexCodeBetweenYear(startYear, endYear, countryCode, "R013");
//        List<Map<String, Object>> listAQIOne = serviceFactory.getAqiService().getAqiMonthByCountryDateWorkMap(listCountryCode, "AQI", endYear);
//        List<Map<String, Object>> listAQITwo = serviceFactory.getAqiService().getAqiYearByCountryDateWorkMap(listCountryCode, "AQI", startYear, endYear);
//        List<Map<String, Object>> listWaterSource = serviceFactory.getDataService().getDataByCountryCodeAndIndexCodeBetweenYear(startYear, endYear, countryCode, "R090");
//        List<Map<String, Object>> listSoilUse = serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode, soilIndexCode, startYear, endYear);
//        List<Map<String, Object>> listDangerSpecies = serviceFactory.getDataService().getDataByCountryIndexBetweenYear(listCountryCode, dangerIndexCode, startYear, endYear);
//        List<Map<String, Object>> listWaterProjectsSpecies = serviceFactory.getGlobalDamService().getDamInfoByCountryYear(endYear, listCountryCode);
//
//        //--====---文本替换
//        dataMap.put("TemperatureDesc", getTextDesc(listWeatherYearT, "TemperatureDesc", startYear, endYear, countryCode, countryName));  //气温
//        dataMap.put("RainDesc", getTextDesc(listWeatherYearP, "RainDesc", startYear, endYear, countryCode, countryName));   //降水
//        dataMap.put("QihouDesc", getTextDesc(null, "QihouDesc", startYear, endYear, countryCode, countryName));   //气候
//        dataMap.put("ReliefDesc", getTextDesc(null, "ReliefDesc", startYear, endYear, countryCode, countryName));   //地形
//        dataMap.put("populationDesc", getTextDesc(listDataPopulation, "populationDesc", startYear, endYear, countryCode, countryName));   //人口
//        dataMap.put("GDPDesc", getTextDesc(listGDP, "GDPDesc", startYear, endYear, countryCode, countryName));   //GDP
//        dataMap.put("AQI_YearDesc", getTextDesc(listAQITwo, "AQI_YearDesc", startYear, endYear, countryCode, countryName));   //AQI
//        dataMap.put("WaterSourceDesc", getTextDesc(listWaterSource, "WaterSourceDesc", startYear, endYear, countryCode, countryName));    //水资源
//        dataMap.put("SoilUseDesc", getTextDesc(listSoilUse, "SoilUseDesc", startYear, endYear, countryCode, countryName));    //土地利用
//        dataMap.put("DangerSpeciesDesc", getTextDesc(listDangerSpecies, "DangerSpeciesDesc", startYear, endYear, countryCode, countryName));    //受威胁物种
//        dataMap.put("WaterProjectsDesc", getTextDesc(listDangerSpecies, "WaterProjectsDesc", startYear, endYear, countryCode, countryName));    //水利工程
//
//        //--===--表格操作
//        List<String> listTitle = new ArrayList<String>();
//        listTitle.add("yearOne");
//        listTitle.add("yearTwo");
//        listTitle.add("yearThr");
//        listTitle.add("yearFou");
//        listTitle.add("yearFiv");
//        listTitle.add("yearSix");
//        listTitle.add("yearSev");
//        listTitle.add("yearEig");
//        listTitle.add("yearNin");
//        listTitle.add("yearTen");
//
//        //替换表头
//        Map<String, String> mapTableTitle = new HashMap<String, String>();
//        //表头
//        int intStartYear = Integer.parseInt(startYear);
//        for (int j = 0; j < listTitle.size(); j++) {
//            mapTableTitle.put(listTitle.get(j), String.valueOf(intStartYear + j * 2));
//            dataMap.put(listTitle.get(j), intStartYear + j * 2);
//        }
//
//        //获得相应的表格数据
//        List<Map<String, Object>> listMonthWeatherT = getTableConvert(listWeatherMonthT, mapTableTitle, "monthWeather", countryName, startYear, endYear);
//        List<Map<String, Object>> listNunberWeatherT = getTableConvert(listWeatherYearT, mapTableTitle, "nunberWeather", countryName, startYear, endYear);
//        List<Map<String, Object>> listMonthWeatherP = getTableConvert(listWeatherMonthP, mapTableTitle, "monthWeather", countryName, startYear, endYear);
//        List<Map<String, Object>> listNunberWeatherP = getTableConvert(listWeatherYearP, mapTableTitle, "nunberWeather", countryName, startYear, endYear);
//        List<Map<String, Object>> listNunberPopOne = getTableConvert(listDataPopulation, mapTableTitle, "nunberData", countryName, startYear, endYear);
//        List<Map<String, Object>> listNunberGDPTwo = getTableConvert(listGDP, mapTableTitle, "nunberData", countryName, startYear, endYear);
//        List<Map<String, Object>> listNumberAQIOne = getTableConvert(listAQIOne, mapTableTitle, "monthAqi", countryName, startYear, endYear);
//        List<Map<String, Object>> listMonthAQITwo = getTableConvert(listAQITwo, mapTableTitle, "nunberAQI", countryName, startYear, endYear);
//        List<Map<String, Object>> listNumberWeather = getTableConvert(listWaterSource, mapTableTitle, "nunberData", countryName, startYear, endYear);
//        List<Map<String, Object>> listMonthSoil = getTableConvert(listSoilUse.subList(listSoilUse.size() - 1, listSoilUse.size()), mapTableTitle, "monthSoilUse", countryName, startYear, endYear);
//        List<Map<String, Object>> listNumberSoil = getTableConvert(listSoilUse, mapTableTitle, "nunberSoilUse", countryName, startYear, endYear);
//        List<Map<String, Object>> listMonthDanger = getTableConvert(listDangerSpecies.subList(listDangerSpecies.size() - 1, listDangerSpecies.size()), mapTableTitle, "monthDangerSpecies", countryName, startYear, endYear);
//        List<Map<String, Object>> listNumberDanger = getTableConvert(listDangerSpecies, mapTableTitle, "nunberDangerSpecies", countryName, startYear, endYear);
//        List<Map<String, Object>> listNumverProject = getTableConvert(listWaterProjectsSpecies, mapTableTitle, "nunberWaterProjects", countryName, startYear, endYear);
//        //替换表格占位符
//        dataMap.put("objsWeatherOne", listMonthWeatherT);
//        dataMap.put("objsWeatherTwo", listNunberWeatherT);
//        dataMap.put("objsRainOne", listMonthWeatherP);
//        dataMap.put("objsRainTwo", listNunberWeatherP);
//        dataMap.put("objsPopulationOne", listNunberPopOne);
//        dataMap.put("objsGDPOne", listNunberGDPTwo);
//        dataMap.put("objsAQIOne", listNumberAQIOne);
//        dataMap.put("objsAQITwo", listMonthAQITwo);
//        dataMap.put("objsWaterSourceOne", listNumberWeather);
//        dataMap.put("objsSoilUseOne", listMonthSoil);
//        dataMap.put("objsSoilUseTwo", listNumberSoil);
//        dataMap.put("objsDangerSpeciesOne", listMonthDanger);
//        dataMap.put("objsDangerSpeciesTwo", listNumberDanger);
//        dataMap.put("objsWaterProjectsOne", listNumverProject);
//
//        //--===--图表操作替换
//        //气温图1
//        getChartIntegrate(listWeatherMonthT, listIndexCode, endYear, "月份", "温度（°C）", "weather", "bar", "PicTemperatureOne");
//        //气温图2
//        getChartIntegrate(listWeatherYearT, listIndexCode, endYear, "年份", "温度（°C）", "weather", "line", "PicTemperatureTwo");
//        //降水图1
//        getChartIntegrate(listWeatherMonthP, listIndexCode, endYear, "月份", "降水（mm）", "weather", "bar", "PicRainOne");
//        //降水图2
//        getChartIntegrate(listWeatherYearP, listIndexCode, endYear, "年份", "降水（mm）", "weather", "line", "PicRainTwo");
//        //人口图1
//        getChartIntegrate(listDataPopulation, listIndexCode, endYear, "年份", "人数（亿）", "data", "line", "PicPopulationOne");
//        //GDP图1
//        getChartIntegrate(listGDP, listIndexCode, endYear, "年份", "GDP（千亿美元）", "data", "line", "PicGDPOne");
//        //AQI图1
//        getChartIntegrate(listAQIOne, listIndexCode, endYear, "月份", "AQI指数", "AQI", "bar", "PicAQIOne");
//        //AQI图2
//        getChartIntegrate(listAQITwo, listIndexCode, endYear, "年份", "AQI指数", "AQI", "line", "PicAQITwo");
//        //水环境图1
//        getChartIntegrate(listWaterSource, listIndexCode, endYear, "年份", "人均淡水量（m3）", "data", "line", "PicWaterSourceOne");
//        //土地利用图1
//        getChartIntegrate(listSoilUse.subList(listSoilUse.size() - 1, listSoilUse.size()), soilIndexCode, endYear, "土地利用类型", "面积（KM2）", "dataTwo", "bar", "PicSoilUseOne");
//        //土地利用图2
//        getChartIntegrate(listSoilUse, soilIndexCode, endYear, "受威胁物种类型", "面积（km2）", "dataTwo", "line", "PicSoilUseTwo");
//        //威胁物种1
//        getChartIntegrate(listDangerSpecies.subList(listDangerSpecies.size() - 1, listDangerSpecies.size()), dangerIndexCode, endYear, "年份", "物种数（个）", "dataTwo", "bar", "PicDangerSpeciesOne");
//        //受威胁物种2
//        getChartIntegrate(listDangerSpecies, dangerIndexCode, endYear, "年份", "物种数（个）", "dataTwo", "line", "PicDangerSpeciesTwo");
//        //大坝图1
//        getChartIntegrate(listWaterProjectsSpecies, listIndexCode, endYear, "规模", "数量（个）", "gam", "bar", "PicWaterProjectsOne");
//        //--===--最终生成文档
//        TCodCountry countryInfo = serviceFactory.getOrgCountryService().getCountryByCode(countryCode);
//        System.out.print("==========>>>>>>>>>" + JSON.toJSONString(dataMap));
//        try {
//            fileName = WordUtils.exportWordReport(request, response, dataMap, "China Ecological environment report_", "CountryTemp.ftl");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        returnMap.put("fileName", fileName);
//        return JSON.toJSONString(returnMap);
//    }
//
//
//    /***
//     *图表操作总集成
//     * @author:zhaobc
//     * @param dataMap  键值对替换
//     * @param listData  数据集合
//     * @param endYear   年
//     * @param xName   x标题
//     * @param yName   y标题
//     * @param indexType   指标类型
//     * @param placeholder   占位符
//     * @creatTime:2018-11-12 - 下午2:38:28
//     */
//    public void getChartIntegrate(List<Map<String, Object>> listData, List<String> indexCode, String endYear, String xName, String yName, String indexType, String chartType, String placeholder) {
//        switch (chartType) {
//            case "bar":
//                ChartData bardata = getBarChartData(listData, indexCode, indexType, endYear);
//                //得到JFreeChart
//                JFreeChart barchart = JfreeChartUtil.getBarChart("", bardata, false, xName, yName);
//                //将JFreeChart转换成流
//                InputStream inBar = JfreeChartUtil.getChartStream(barchart, 1000, 600);
//                dataMap.put(placeholder, WordUtils.getImageInput(inBar));
//                break;
//
//            case "line":
//                ChartData linedata = getLineChartData(listData, indexCode, indexType);
//                //得到JFreeChart
//                JFreeChart linechart = JfreeChartUtil.getLineChart("", linedata, false, xName, yName);
//                //将JFreeChart转换成流
//                InputStream inLine = JfreeChartUtil.getChartStream(linechart, 1000, 800);
//                //生成图片
//                dataMap.put(placeholder, WordUtils.getImageInput(inLine));
//                break;
//        }
//
//    }
//
//    /***
//     * 获得文字简介信息
//     * @author:zhaobc
//     * @param type
//     * @return
//     * @creatTime:2018-11-8 - 下午12:00:01
//     */
//    public String getTextDesc(List<Map<String, Object>> listData, String type, String startYear, String endYear, String countryCode, String countryName) {
//        Map<String, Object> reportMap = new HashMap<String, Object>();
//        String reportDesc = "";
//        //不同的种类不同的算法
//        switch (type) {
//            //气温
//            case "TemperatureDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("Temperature");
//                reportDesc = (String) reportMap.get("reportDesc");
//                String temperatureTotal = "";
//                String temperatureMaxMonth = "";
//                String temperatureMaxMonthValue = "";
//                String temperatureMinMonth = "";
//                String temperatureMinMonthValue = "";
//                String temperatureTotalTrend = "";
//                String temperatureMaxYear = "";
//                String temperatureMaxYearValue = "";
//                String temperatureMinYear = "";
//                String temperatureMinYearValue = "";
//                //实际数据最大时间
//                String temStartYear = listData.get(0).get("year").toString();
//                //实际数据最小时间
//                String temEndYear = listData.get(listData.size() - 1).get("year").toString();
//
//                List<String> temListCountryCode = new ArrayList<String>();
//                temListCountryCode.add(countryCode);
//                List<Map<String, Object>> temWeatherAvg = this.serviceFactory.getWeatherService().getAvgWeatherInfoByCountryIndexYear(temListCountryCode, "T", temEndYear);
//                if (temWeatherAvg != null && temWeatherAvg.size() > 0) {
//                    List<TBasWeathermonth> temWeatherMonthMaxMin = this.serviceFactory.getWeatherService().getMonthMaxMinValue(countryCode, "T", temEndYear);
//                    List<TBasWeathermonth> temWeatheryearMaxMin = this.serviceFactory.getWeatherService().getYearMaxMinValue(countryCode, "T", temStartYear, temEndYear);
//                    List<TBasWeathermonth> temWeatheryearStartEnd = this.serviceFactory.getWeatherService().getYearStartEndValue(countryCode, "T", temStartYear, temEndYear);
//                    temperatureTotal = temWeatherAvg.get(0).get("VALUE").toString();
//
//                    //月度
//                    temperatureMinMonth = temWeatherMonthMaxMin.get(0).getMonth();
//                    temperatureMinMonthValue = temWeatherMonthMaxMin.get(0).getValue();
//                    temperatureMaxMonth = temWeatherMonthMaxMin.get(1).getMonth();
//                    temperatureMaxMonthValue = temWeatherMonthMaxMin.get(1).getValue();
//                    //年度
//                    temperatureMinYear = temWeatheryearMaxMin.get(0).getYear();
//                    temperatureMinYearValue = temWeatheryearMaxMin.get(0).getValue();
//                    temperatureMaxYear = temWeatheryearMaxMin.get(1).getYear();
//                    temperatureMaxYearValue = temWeatheryearMaxMin.get(1).getValue();
//                    //趋势
//                    String startValue = temWeatheryearStartEnd.get(0).getValue();
//                    double douStartValue = 0;
//                    if (temWeatheryearStartEnd.size() >= 1 && startValue != null && startValue.length() > 0) {
//                        douStartValue = Double.parseDouble(temWeatheryearStartEnd.get(0).getValue());
//                    }
//                    String endValue = temWeatheryearStartEnd.get(1).getValue();
//                    double douEndValue = 0;
//                    if (temWeatheryearStartEnd.size() >= 1 && endValue != null && endValue.length() > 0) {
//                        douEndValue = Double.parseDouble(temWeatheryearStartEnd.get(1).getValue());
//                    }
//                    if (douEndValue - douStartValue > 0) {
//                        temperatureTotalTrend = "上涨";
//                    } else if (douEndValue - douStartValue < 0) {
//                        temperatureTotalTrend = "下降";
//                    } else {
//                        temperatureTotalTrend = "平稳";
//                    }
//
//                    //保留小数部分
//                    temperatureTotal = RetainedDecimal(temperatureTotal, 2, 1.0);
//                    temperatureMaxMonthValue = RetainedDecimal(temperatureMaxMonthValue, 2, 1.0);
//                    temperatureMinMonthValue = RetainedDecimal(temperatureMinMonthValue, 2, 1.0);
//                    temperatureMaxYearValue = RetainedDecimal(temperatureMaxYearValue, 2, 1.0);
//                    temperatureMinYearValue = RetainedDecimal(temperatureMinYearValue, 2, 1.0);
//                    //替换图片占位符
//                    dataMap.put("yearTemp", temEndYear);
//                    reportDesc = reportDesc.replace("${year}", temEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${TemperatureTotal}", temperatureTotal);   //年度平均气温
//                    reportDesc = reportDesc.replace("${TemperatureMaxMonth}", temperatureMaxMonth);    //月度最高气温月份
//                    reportDesc = reportDesc.replace("${TemperatureMaxMonthValue}", temperatureMaxMonthValue);    //最高气温温度
//                    reportDesc = reportDesc.replace("${TemperatureMinMonth}", temperatureMinMonth);    //月度最低气温月份
//                    reportDesc = reportDesc.replace("${TemperatureMinMonthValue}", temperatureMinMonthValue);   //最低气温温度
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${populationTotalTrend}", temperatureTotalTrend);    //趋势
//                    reportDesc = reportDesc.replace("${TemperatureMaxYear}", temperatureMaxYear);    //年度最高气温
//                    reportDesc = reportDesc.replace("${TemperatureMaxYearValue}", temperatureMaxYearValue);    //最高气温值
//                    reportDesc = reportDesc.replace("${TemperatureMinYear}", temperatureMinYear);     //年度最低气温
//                    reportDesc = reportDesc.replace("${TemperatureMinYearValue}", temperatureMinYearValue);    //最低气温值
//                }
//                break;
//            //降水
//            case "RainDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("Rain");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置变
//                String rainTotal = "";
//                String rainMaxMonth = "";
//                String rainMaxMonthValue = "";
//                String rainMinMonth = "";
//                String rainMinMonthValue = "";
//                String rainTotalTrend = "";
//                String rainMaxYear = "";
//                String rainMaxYearValue = "";
//                String rainMinYear = "";
//                String rainMinYearValue = "";
//                //实际数据最大时间
//                String rainStartYear = listData.get(0).get("year").toString();
//                //实际数据最小时间
//                String rainEndYear = listData.get(listData.size() - 1).get("year").toString();
//
//                List<String> rainListCountryCode = new ArrayList<String>();
//                rainListCountryCode.add(countryCode);
//                List<Map<String, Object>> rainWeatherAvg = this.serviceFactory.getWeatherService().getAvgWeatherInfoByCountryIndexYear(rainListCountryCode, "P", rainEndYear);
//                if (rainWeatherAvg != null && rainWeatherAvg.size() > 0) {
//                    List<TBasWeathermonth> rainWeatherMonthMaxMin = this.serviceFactory.getWeatherService().getMonthMaxMinValue(countryCode, "P", rainEndYear);
//                    List<TBasWeathermonth> rainWeatheryearMaxMin = this.serviceFactory.getWeatherService().getYearMaxMinValue(countryCode, "P", rainStartYear, rainEndYear);
//                    List<TBasWeathermonth> rainWeatheryearStartEnd = this.serviceFactory.getWeatherService().getYearStartEndValue(countryCode, "P", rainStartYear, rainEndYear);
//                    rainTotal = rainWeatherAvg.get(0).get("VALUE").toString();
//                    //月度
//                    rainMinMonth = rainWeatherMonthMaxMin.get(0).getMonth();
//                    rainMinMonthValue = rainWeatherMonthMaxMin.get(0).getValue();
//                    rainMaxMonth = rainWeatherMonthMaxMin.get(1).getMonth();
//                    rainMaxMonthValue = rainWeatherMonthMaxMin.get(1).getValue();
//                    //年度
//                    rainMinYear = rainWeatheryearMaxMin.get(0).getYear();
//                    rainMinYearValue = rainWeatheryearMaxMin.get(0).getValue();
//                    rainMaxYear = rainWeatheryearMaxMin.get(1).getYear();
//                    rainMaxYearValue = rainWeatheryearMaxMin.get(1).getValue();
//                    //趋势
//                    double douStartValue = Double.parseDouble(rainWeatheryearStartEnd.get(0).getValue());
//                    double douEndValue = Double.parseDouble(rainWeatheryearStartEnd.get(1).getValue());
//                    if (douEndValue - douStartValue > 0) {
//                        rainTotalTrend = "上涨";
//                    } else if (douEndValue - douStartValue < 0) {
//                        rainTotalTrend = "下降";
//                    } else {
//                        rainTotalTrend = "平稳";
//                    }
//                    //保留小数部分
//                    rainTotal = RetainedDecimal(rainTotal, 2, 1.0);
//                    rainMaxMonthValue = RetainedDecimal(rainMaxMonthValue, 2, 1.0);
//                    rainMinMonthValue = RetainedDecimal(rainMinMonthValue, 2, 1.0);
//                    rainMaxYearValue = RetainedDecimal(rainMaxYearValue, 2, 1.0);
//                    rainMinYearValue = RetainedDecimal(rainMinYearValue, 2, 1.0);
//
//                    //替换图示占位符
//                    dataMap.put("yearRain", rainEndYear);
//                    reportDesc = reportDesc.replace("${year}", rainEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${RainTotal}", rainTotal);   //年度平均降水
//                    reportDesc = reportDesc.replace("${RainMaxMonth}", rainMaxMonth);    //月度最高降水月份
//                    reportDesc = reportDesc.replace("${RainMaxMonthValue}", rainMaxMonthValue);    //最高降水温度
//                    reportDesc = reportDesc.replace("${RainMinMonth}", rainMinMonth);    //月度最低降水月份
//                    reportDesc = reportDesc.replace("${RainMinMonthValue}", rainMinMonthValue);   //最低降水温度
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${RainTotalTrend}", rainTotalTrend);    //趋势
//                    reportDesc = reportDesc.replace("${RainMaxYear}", rainMaxYear);    //年度最高降水
//                    reportDesc = reportDesc.replace("${RainMaxYearValue}", rainMaxYearValue);    //最高降水值
//                    reportDesc = reportDesc.replace("${RainMinYear}", rainMinYear);     //年度最低降水
//                    reportDesc = reportDesc.replace("${RainMinYearValue}", rainMinYearValue);    //最低降水值
//                }
//                break;
//            case "QihouDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("Qihou");
//                reportDesc = reportMap.get("reportDesc").toString();
//                //查找数据
//                TCodCountrydetail qiHouOrgInfo = this.serviceFactory.getOrgCountryService().getCountryInfoByCode(countryCode);
//                reportDesc = reportDesc.replace("${Qihou}", qiHouOrgInfo.getClimate());
//                break;
//            case "ReliefDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("Relief");
//                reportDesc = reportMap.get("reportDesc").toString();
//                //查找数据
//                TCodCountrydetail relOrgInfo = this.serviceFactory.getOrgCountryService().getCountryInfoByCode(countryCode);
//
//                reportDesc = reportDesc.replace("${Relief}", relOrgInfo.getTopographic());
//                break;
//            case "populationDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("population");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                String populationTotal = "";
//                String populationDensity = "";
//                String populationTotalTrend = "";
//                String populationDensityTrend = "";
//                //实际数据最大时间
//                String popStartYear = listData.get(0).get("dataYear").toString();
//                //实际数据最小时间
//                String popEndYear = listData.get(listData.size() - 1).get("dataYear").toString();
//
//                //进行计算
//                List<String> popIndexCode = new ArrayList<String>();
//                popIndexCode.add("R007");
//                popIndexCode.add("R012");
//                List<String> popCountryCode = new ArrayList<String>();
//                popCountryCode.add(countryCode);
//
//                List<TBasData> popDataInfo = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(popIndexCode, popCountryCode, popEndYear);
//                if (popDataInfo != null && popDataInfo.size() > 0) {
//                    for (TBasData tBasData : popDataInfo) {
//                        switch (tBasData.getIndexCode()) {
//                            case "R007":
//                                populationTotal = tBasData.getDataValue();
//                                break;
//                            case "R012":
//                                populationDensity = tBasData.getDataValue();
//                                break;
//                        }
//                    }
//                    //获得历史数据
//                    List<TBasData> popDataInfoStart = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(popIndexCode, popCountryCode, popStartYear);
//                    for (TBasData tBasDataTwo : popDataInfoStart) {
//                        switch (tBasDataTwo.getIndexCode()) {
//                            case "R007":
//                                populationTotalTrend = getIndexTrend(tBasDataTwo, populationTotal);
//                                break;
//                            case "R012":
//                                populationDensityTrend = getIndexTrend(tBasDataTwo, populationDensity);
//                                break;
//                        }
//                    }
//                    //保留小数部分  人口单位     个换算成 亿
//                    populationTotal = RetainedDecimal(populationTotal, 2, 100000000.0);
//                    populationDensity = RetainedDecimal(populationDensity, 2, 1.0);
//                    reportDesc = reportDesc.replace("${year}", popEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${populationTotal}", populationTotal);    //人口数量
//                    reportDesc = reportDesc.replace("${populationDensity}", populationDensity);   //人口密度
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${populationTotalTrend}", populationTotalTrend);    //人口数量趋势
//                    reportDesc = reportDesc.replace("${populationDensityTrend}", populationDensityTrend);    //人口密度趋势
//                }
//                break;
//            case "GDPDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("GDP");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                String GDPTotal = "";
//                String GDPDensity = "";
//                String GDPTotalTrend = "";
//                String GDPDensityTrend = "";
//                //实际数据最大时间
//                String gdpStartYear = listData.get(0).get("dataYear").toString();
//                //实际数据最小时间
//                String gdpEndYear = listData.get(listData.size() - 1).get("dataYear").toString();
//
//                //进行计算
//                List<String> gdpIndexCode = new ArrayList<String>();
//                gdpIndexCode.add("R013");
//                gdpIndexCode.add("R014");
//                List<String> gdpCountryCode = new ArrayList<String>();
//                gdpCountryCode.add(countryCode);
//                List<TBasData> gdpDataInfo = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(gdpIndexCode, gdpCountryCode, gdpEndYear);
//                if (gdpDataInfo != null && gdpDataInfo.size() > 0) {
//                    for (TBasData tBasData : gdpDataInfo) {
//                        switch (tBasData.getIndexCode()) {
//                            case "R013":
//                                GDPTotal = tBasData.getDataValue();
//                                break;
//                            case "R014":
//                                GDPDensity = tBasData.getDataValue();
//                                break;
//                        }
//                    }
//                    //获得历史数据
//                    List<TBasData> gdpDataInfoStart = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(gdpIndexCode, gdpCountryCode, gdpStartYear);
//                    for (TBasData tBasDataTwo : gdpDataInfoStart) {
//                        switch (tBasDataTwo.getIndexCode()) {
//                            case "R013":
//                                GDPTotalTrend = getIndexTrend(tBasDataTwo, GDPTotal);
//                                break;
//                            case "R014":
//                                GDPDensityTrend = getIndexTrend(tBasDataTwo, GDPDensity);
//                                break;
//                        }
//
//                    }
//                    //保留小数部分   GDP单位换算 元换算成千亿       人均GDP产值  元换算成万元
//                    GDPTotal = RetainedDecimal(GDPTotal, 2, 100000000000.0);
//                    GDPDensity = RetainedDecimal(GDPDensity, 2, 10000.0);
//                    reportDesc = reportDesc.replace("${year}", gdpEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${GDPTotal}", GDPTotal);    //GDP数量
//                    reportDesc = reportDesc.replace("${PCGDP}", GDPDensity);   //GDP密度
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${GDPTrend}", GDPTotalTrend);    //GDP数量趋势
//                    reportDesc = reportDesc.replace("${PCGDPTrend}", GDPDensityTrend);    //GDP密度趋势
//                }
//                break;
//            case "AQI_YearDesc":  //模板需修改
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("AQI_Year");
//                reportDesc = (String) reportMap.get("reportDesc");
//                String busiName = "AQI";
//                String aQITotal = "";
//                String pollutionType = "";
//                int valueOne = 0;
//                int valueTwo = 0;
//                int valueThr = 0;
//                int valueFou = 0;
//                int valueSum = 0;  //总数
//                String aQIMonthMax = "";
//                String aQIMonthMaxValue = "";
//                String aQIMonthMin = "";
//                String aQIMonthMinValue = "";
//                double aqiyearValue = 0;  //计算总数
//                //开始计算
//                List<String> aqiCountryCode = new ArrayList<String>();
//                aqiCountryCode.add(countryCode);
//
//                String dateTime = endYear + "-12-31";
//                int dateRange = 0;
//                double year = Integer.parseInt(endYear);
//                //判断闰年
//                if (year % 4 == 0 && year % 100 != 0) {
//                    dateRange = -365;
//                } else {
//                    dateRange = -364;
//                }
//                //获得一年的aqi数据
//                List<TBasAqi> aqiInfo = this.serviceFactory.getAqiService().getAqiDayByCountryDateWork(aqiCountryCode, busiName, dateTime, String.valueOf(dateRange));
//                if (aqiInfo.size() > 0) {
//                    for (TBasAqi tBasAqi : aqiInfo) {
//                        try {
//                            //AQI总量 计算天数
//                            double AQIValue = Double.parseDouble(tBasAqi.getAqi());
//                            if (AQIValue > 0 && AQIValue <= 100) {
//                                valueOne++;
//                            } else if (AQIValue <= 150) {
//                                valueTwo++;
//                            } else if (AQIValue <= 200) {
//                                valueThr++;
//                            } else if (AQIValue > 200) {
//                                valueFou++;
//                            }
//
//                            //计算总数，求全年平均数
//                            aqiyearValue += AQIValue;
//                            valueSum++;
//                        } catch (Exception e) {
//                            continue;
//                        }
//                    }
//                    //计算总量
//                    double douAQITotal = aqiyearValue / valueSum;
//                    BigDecimal valueBigTwo = new BigDecimal(douAQITotal);
//                    aQITotal = String.valueOf(valueBigTwo.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
//                    //判断空气质量类型
//                    if (douAQITotal <= 0) {
//                        pollutionType = "无";
//                    } else if (douAQITotal <= 50) {
//                        pollutionType = "优";
//                    } else if (douAQITotal <= 100) {
//                        pollutionType = "良";
//                    } else if (douAQITotal <= 150) {
//                        pollutionType = "轻度污染";
//                    } else if (douAQITotal <= 200) {
//                        pollutionType = "中度污染";
//                    } else if (douAQITotal <= 300) {
//                        pollutionType = "重度污染";
//                    } else if (douAQITotal > 300) {
//                        pollutionType = "严重污染";
//                    }
//                    //计算趋势
//                    List<TBasAqi> aqiInfoMonth = this.serviceFactory.getAqiService().getAqiMonthByCountryDateWork(aqiCountryCode, busiName, endYear);
//                    aQIMonthMax = aqiInfoMonth.get(0).getMonitortime();
//                    aQIMonthMaxValue = aqiInfoMonth.get(0).getAqi();
//                    aQIMonthMin = aqiInfoMonth.get(aqiInfoMonth.size() - 1).getMonitortime();
//                    aQIMonthMinValue = aqiInfoMonth.get(aqiInfoMonth.size() - 1).getAqi();
//                    ;
//                    //保留小数部分
//                    aQITotal = RetainedDecimal(aQITotal, 2, 1.0);
//                    aQIMonthMaxValue = RetainedDecimal(aQIMonthMaxValue, 2, 1.0);
//                    aQIMonthMinValue = RetainedDecimal(aQIMonthMinValue, 2, 1.0);
//
//                    //替换图示占位符
//                    dataMap.put("yearAQI", endYear);
//                    reportDesc = reportDesc.replace("${year}", endYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${busiName}", busiName);    //指数
//                    reportDesc = reportDesc.replace("${AQITotal}", aQITotal);   //指数值
//                    reportDesc = reportDesc.replace("${pollutionType}", pollutionType);  //空气质量
//                    reportDesc = reportDesc.replace("${valueOne}", String.valueOf(valueOne) + "天");   //优良比例
//                    reportDesc = reportDesc.replace("${valueTwo}", String.valueOf(valueTwo) + "天");   //轻度污染比例
//                    reportDesc = reportDesc.replace("${valueThr}", String.valueOf(valueThr) + "天");   //中度污染比例
//                    reportDesc = reportDesc.replace("${valueFou}", String.valueOf(valueFou) + "天");   //重度污染比例
//                    reportDesc = reportDesc.replace("${AQIMonthMax}", aQIMonthMax);   //最好月份数值
//                    reportDesc = reportDesc.replace("${AQIMonthMaxValue}", aQIMonthMaxValue);    //最好月份数值
//                    reportDesc = reportDesc.replace("${AQIMonthMin}", aQIMonthMin);   //最好月份数值
//                    reportDesc = reportDesc.replace("${AQIMonthMinValue}", aQIMonthMinValue);    //最好月份数值
//                } else {
//                    reportDesc = "暂无" + startYear + "到" + endYear + "AQI数据";
//                }
//
//                break;
//            case "WaterSourceDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("WaterSource");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                String waterSourceTotal = "";
//                String waterSourceTotalTrend = "";
//                String waterSourceMaxYear = "";
//                String waterSourceMaxYearValue = "";
//                String waterSourceMinYear = "";
//                String waterSourceMinYearValue = "";
//                //实际数据最大时间
//                String waterStartYear = listData.get(0).get("dataYear").toString();
//                //实际数据最小时间
//                String waterEndYear = listData.get(listData.size() - 1).get("dataYear").toString();
//
//                //进行计算
//                List<String> waterIndexCode = new ArrayList<String>();
//                waterIndexCode.add("R090");
//                List<String> waterCountryCode = new ArrayList<String>();
//                waterCountryCode.add(countryCode);
//                List<TBasData> waterDataInfo = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(waterIndexCode, waterCountryCode, waterEndYear);
//                if (waterDataInfo != null && waterDataInfo.size() > 0) {
//                    for (TBasData tBasData : waterDataInfo) {
//                        switch (tBasData.getIndexCode()) {
//                            case "R090":
//                                waterSourceTotal = tBasData.getDataValue();
//                                break;
//                        }
//                    }
//                    //获得历史数据
//                    List<TBasData> waterDataInfoStart = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(waterIndexCode, waterCountryCode, waterStartYear);
//                    for (TBasData tBasDataTwo : waterDataInfoStart) {
//                        switch (tBasDataTwo.getIndexCode()) {
//                            case "R090":
//                                waterSourceTotalTrend = getIndexTrend(tBasDataTwo, waterSourceTotal);
//                                break;
//                        }
//                    }
//
//                    List<TBasData> waterDataMaxMinValue = this.serviceFactory.getDataService().getDataMaxMinValue("R090", countryCode, startYear, endYear);
//                    waterSourceMaxYear = waterDataMaxMinValue.get(0).getDataYear();
//                    waterSourceMaxYearValue = waterDataMaxMinValue.get(0).getDataValue();
//                    waterSourceMinYear = waterDataMaxMinValue.get(1).getDataYear();
//                    waterSourceMinYearValue = waterDataMaxMinValue.get(1).getDataValue();
//                    //保留小数部分
//                    waterSourceTotal = RetainedDecimal(waterSourceTotal, 2, 1.0);
//                    waterSourceMaxYearValue = RetainedDecimal(waterSourceMaxYearValue, 2, 1.0);
//                    waterSourceMinYearValue = RetainedDecimal(waterSourceMinYearValue, 2, 1.0);
//
//                    reportDesc = reportDesc.replace("${year}", waterEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${WaterSourceTotal}", waterSourceTotal);    //淡水资源
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${WaterSourceTotalTrend}", waterSourceTotalTrend);    //年度数量趋势
//                    reportDesc = reportDesc.replace("${WaterSourceMaxYear}", waterSourceMaxYear);    //最高年份
//                    reportDesc = reportDesc.replace("${WaterSourceMaxYearValue}", waterSourceMaxYearValue);    //最高数量
//                    reportDesc = reportDesc.replace("${WaterSourceMinYear}", waterSourceMinYear);    //最低年份
//                    reportDesc = reportDesc.replace("${WaterSourceMinYearValue}", waterSourceMinYearValue);    //最低数量
//                }
//                break;
//            //土地利用
//            case "SoilUseDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("SoilUse");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                String caoYuanValue = "0";
//                String caoYuanPercentage = "";
//                String zhiBeiValue = "0";
//                String zhiBeiPercentage = "";
//                String luoLouValue = "0";
//                String luoLouPercentage = "";
//                String renGongValue = "0";
//                String renGongPercentage = "";
//                String neiLuShuiValue = "0";
//                String neiLuShuiPercentage = "";
//                String nongTianValue = "0";
//                String nongTianPercentage = "";
//                String shuMuValue = "0";
//                String shuMuPercentage = "";
//                String guanGaiValue = "0";
//                String guanGaiPercentage = "";
//                String shiDiValue = "0";
//                String shiDiPercentage = "";
//                String caoYuanTotalTrend = "平稳";
//                String zhiBeiTotalTrend = "平稳";
//                String luoLouTotalTrend = "平稳";
//                String renGongTotalTrend = "平稳";
//                String neiLuShuiTotalTrend = "平稳";
//                String nongTianTotalTrend = "平稳";
//                String shuMuTotalTrend = "平稳";
//                String guanGaiTotalTrend = "平稳";
//                String shiDiTotalTrend = "平稳";
//                //实际数据最大时间
//                String soilStartYear = listData.get(0).get("DATA_YEAR").toString();
//                //实际数据最小时间
//                String soilEndYear = listData.get(listData.size() - 1).get("DATA_YEAR").toString();
//
//                //进行计算
//                List<String> soilIndexCode = new ArrayList<String>();
//                soilIndexCode.add("R164");
//                soilIndexCode.add("R165");
//                soilIndexCode.add("R166");
//                soilIndexCode.add("R167");
//                soilIndexCode.add("R168");
//                soilIndexCode.add("R169");
//                soilIndexCode.add("R170");
//                soilIndexCode.add("R171");
//                soilIndexCode.add("R172");
//                soilIndexCode.add("R173");
//                soilIndexCode.add("R174");
//                soilIndexCode.add("R175");
//                soilIndexCode.add("R176");
//                soilIndexCode.add("R177");
//                soilIndexCode.add("R178");
//                soilIndexCode.add("R179");
//                soilIndexCode.add("R180");
//                soilIndexCode.add("R181");
//                List<String> soilCountryCode = new ArrayList<String>();
//                soilCountryCode.add(countryCode);
//                List<TBasData> soilDataInfo = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(soilIndexCode, soilCountryCode, soilEndYear);
//                if (soilDataInfo != null && soilDataInfo.size() > 0) {
//                    for (TBasData tBasData : soilDataInfo) {
//                        switch (tBasData.getIndexCode()) {
//                            case "R164":
//                                caoYuanValue = tBasData.getDataValue();
//                                break;
//                            case "R165":
//                                zhiBeiValue = tBasData.getDataValue();
//                                break;
//                            case "R166":
//                                luoLouValue = tBasData.getDataValue();
//                                break;
//                            case "R167":
//                                renGongValue = tBasData.getDataValue();
//                                break;
//                            case "R168":
//                                neiLuShuiValue = tBasData.getDataValue();
//                                break;
//                            case "R169":
//                                nongTianValue = tBasData.getDataValue();
//                                break;
//                            case "R170":
//                                shuMuValue = tBasData.getDataValue();
//                                break;
//                            case "R171":
//                                guanGaiValue = tBasData.getDataValue();
//                                break;
//                            case "R172":
//                                shiDiValue = tBasData.getDataValue();
//                                break;
//                            case "R173":
//                                caoYuanPercentage = tBasData.getDataValue();
//                                break;
//                            case "R174":
//                                zhiBeiPercentage = tBasData.getDataValue();
//                                break;
//                            case "R175":
//                                luoLouPercentage = tBasData.getDataValue();
//                                break;
//                            case "R176":
//                                renGongPercentage = tBasData.getDataValue();
//                                break;
//                            case "R177":
//                                neiLuShuiPercentage = tBasData.getDataValue();
//                                break;
//                            case "R178":
//                                nongTianPercentage = tBasData.getDataValue();
//                                break;
//                            case "R179":
//                                shuMuPercentage = tBasData.getDataValue();
//                                break;
//                            case "R180":
//                                guanGaiPercentage = tBasData.getDataValue();
//                                break;
//                            case "R181":
//                                shiDiPercentage = tBasData.getDataValue();
//                                break;
//                        }
//                    }
//                    //获得历史数据
//                    List<TBasData> soilDataInfoStart = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(soilIndexCode, soilCountryCode, soilStartYear);
//                    for (TBasData tBasDataTwo : soilDataInfoStart) {
//                        switch (tBasDataTwo.getIndexCode()) {
//                            case "R164":
//                                caoYuanTotalTrend = getIndexTrend(tBasDataTwo, caoYuanValue);
//                                break;
//                            case "R165":
//                                zhiBeiTotalTrend = getIndexTrend(tBasDataTwo, zhiBeiValue);
//                                break;
//                            case "R166":
//                                luoLouTotalTrend = getIndexTrend(tBasDataTwo, luoLouValue);
//                                break;
//                            case "R167":
//                                renGongTotalTrend = getIndexTrend(tBasDataTwo, renGongValue);
//                                break;
//                            case "R168":
//                                neiLuShuiTotalTrend = getIndexTrend(tBasDataTwo, neiLuShuiValue);
//                                break;
//                            case "R169":
//                                nongTianTotalTrend = getIndexTrend(tBasDataTwo, nongTianValue);
//                                break;
//                            case "R170":
//                                shuMuTotalTrend = getIndexTrend(tBasDataTwo, shuMuValue);
//                                break;
//                            case "R171":
//                                guanGaiTotalTrend = getIndexTrend(tBasDataTwo, guanGaiValue);
//                                break;
//                            case "R172":
//                                shiDiTotalTrend = getIndexTrend(tBasDataTwo, shiDiValue);
//                                break;
//                        }
//                    }
//                    //保留小数部分
//                    caoYuanValue = RetainedDecimal(caoYuanValue, 2, 1.0);
//                    zhiBeiValue = RetainedDecimal(zhiBeiValue, 2, 1.0);
//                    luoLouValue = RetainedDecimal(luoLouValue, 2, 1.0);
//                    renGongValue = RetainedDecimal(renGongValue, 2, 1.0);
//                    neiLuShuiValue = RetainedDecimal(neiLuShuiValue, 2, 1.0);
//                    nongTianValue = RetainedDecimal(nongTianValue, 2, 1.0);
//                    shuMuValue = RetainedDecimal(shuMuValue, 2, 1.0);
//                    guanGaiValue = RetainedDecimal(guanGaiValue, 2, 1.0);
//                    shiDiValue = RetainedDecimal(shiDiValue, 2, 1.0);
//                    //百分比
//                    caoYuanPercentage = RetainedDecimal(caoYuanPercentage, 2, 1.0) + "%";
//                    zhiBeiPercentage = RetainedDecimal(zhiBeiPercentage, 2, 1.0) + "%";
//                    luoLouPercentage = RetainedDecimal(luoLouPercentage, 2, 1.0) + "%";
//                    renGongPercentage = RetainedDecimal(renGongPercentage, 2, 1.0) + "%";
//                    neiLuShuiPercentage = RetainedDecimal(neiLuShuiPercentage, 2, 1.0) + "%";
//                    nongTianPercentage = RetainedDecimal(nongTianPercentage, 2, 1.0) + "%";
//                    shuMuPercentage = RetainedDecimal(shuMuPercentage, 2, 1.0) + "%";
//                    guanGaiPercentage = RetainedDecimal(guanGaiPercentage, 2, 1.0) + "%";
//                    shiDiPercentage = RetainedDecimal(shiDiPercentage, 2, 1.0) + "%";
//                    //替换图片占位符
//                    dataMap.put("yearSoil", soilEndYear);
//                    reportDesc = reportDesc.replace("${year}", soilEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${CaoYuanValue}", caoYuanValue);    //草原数量
//                    reportDesc = reportDesc.replace("${CaoYuanPercentage}", caoYuanPercentage);
//                    reportDesc = reportDesc.replace("${ZhiBeiValue}", zhiBeiValue);   //稀疏植被数量
//                    reportDesc = reportDesc.replace("${ZhiBeiPercentage}", zhiBeiPercentage);
//                    reportDesc = reportDesc.replace("${LuoLouValue}", luoLouValue);    //裸露区数量
//                    reportDesc = reportDesc.replace("${LuoLouPercentage}", luoLouPercentage);
//                    reportDesc = reportDesc.replace("${RenGongValue}", renGongValue);    //人工地表数量
//                    reportDesc = reportDesc.replace("${RenGongPercentage}", renGongPercentage);
//                    reportDesc = reportDesc.replace("${NeiLuShuiValue}", neiLuShuiValue);    //内陆水域数量
//                    reportDesc = reportDesc.replace("${NeiLuShuiPercentage}", neiLuShuiPercentage);
//                    reportDesc = reportDesc.replace("${NongTianValue}", nongTianValue);    //农田数量
//                    reportDesc = reportDesc.replace("${NongTianPercentage}", nongTianPercentage);
//                    reportDesc = reportDesc.replace("${ShuMuValue}", shuMuValue);    //树木数量
//                    reportDesc = reportDesc.replace("${ShuMuPercentage}", shuMuPercentage);
//                    reportDesc = reportDesc.replace("${GuanGaiValue}", guanGaiValue);    //灌丛数量
//                    reportDesc = reportDesc.replace("${GuanGaiPercentage}", guanGaiPercentage);
//                    reportDesc = reportDesc.replace("${ShiDiValue}", shiDiValue);    //湿地数量
//                    reportDesc = reportDesc.replace("${ShiDiPercentage}", shiDiPercentage);
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${CaoYuanTotalTrend}", caoYuanTotalTrend);    //草原趋势
//                    reportDesc = reportDesc.replace("${ZhiBeiTotalTrend}", zhiBeiTotalTrend);   //植被趋势
//                    reportDesc = reportDesc.replace("${LuoLouTotalTrend}", luoLouTotalTrend);   //裸露区趋势
//                    reportDesc = reportDesc.replace("${RenGongTotalTrend}", renGongTotalTrend);   //人工地表趋势
//                    reportDesc = reportDesc.replace("${NeiLuShuiTotalTrend}", neiLuShuiTotalTrend);   //内陆水趋势
//                    reportDesc = reportDesc.replace("${NongTianTotalTrend}", nongTianTotalTrend);   //农田辻
//                    reportDesc = reportDesc.replace("${ShuMuTotalTrend}", shuMuTotalTrend);   //树木趋势
//                    reportDesc = reportDesc.replace("${GuanGaiTotalTrend}", guanGaiTotalTrend);   //灌丛趋势
//                    reportDesc = reportDesc.replace("${ShiDiTotalTrend}", shiDiTotalTrend);   //湿地趋势
//                }
//                break;
//            case "DangerSpeciesDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("DangerSpecies");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                String puRuValue = "0";
//                String puRuPercentage = "";
//                String ruanTiValue = "0";
//                String ruanTiPercentage = "";
//                String liangXiValue = "0";
//                String liangXiPercentage = "";
//                String paXingValue = "0";
//                String paXingPercentage = "";
//                String niaoLeiValue = "0";
//                String niaoLeiPercentage = "";
//                String yuLeiValue = "0";
//                String yuLeiPercentage = "";
//                String zhiWuValue = "0";
//                String zhiWuPercentage = "";
//                String qiTaValue = "0";
//                String qiTaPercentage = "";
//                String puRuTotalTrend = "平稳";
//                String ruanTiTotalTrend = "平稳";
//                String liangXiTotalTrend = "平稳";
//                String paXingTotalTrend = "平稳";
//                String niaoLeiTotalTrend = "平稳";
//                String yuLeiTotalTrend = "平稳";
//                String zhiWuTotalTrend = "平稳";
//                String qiTaTotalTrend = "平稳";
//                Double zongShuValue = 0.0; //物种总数
//                //实际数据最大时间
//                String dangerStartYear = listData.get(0).get("DATA_YEAR").toString();
//                //实际数据最小时间
//                String dangerEndYear = listData.get(listData.size() - 1).get("DATA_YEAR").toString();
//
//                //进行计算
//                List<String> dangerIndexCode = new ArrayList<String>();
//                dangerIndexCode.add("R149");
//                dangerIndexCode.add("R150");
//                dangerIndexCode.add("R146");
//                dangerIndexCode.add("R151");
//                dangerIndexCode.add("R147");
//                dangerIndexCode.add("R148");
//                dangerIndexCode.add("R152");
//                dangerIndexCode.add("R153");
//                List<String> dangerCountryCode = new ArrayList<String>();
//                dangerCountryCode.add(countryCode);
//                List<TBasData> dangerDataInfo = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(dangerIndexCode, dangerCountryCode, dangerEndYear);
//                if (dangerDataInfo != null && dangerDataInfo.size() > 0) {
//                    for (TBasData tBasData : dangerDataInfo) {
//                        switch (tBasData.getIndexCode()) {
//                            case "R149":
//                                puRuValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(puRuValue);
//                                break;
//                            case "R150":
//                                ruanTiValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(ruanTiValue);
//                                break;
//                            case "R146":
//                                liangXiValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(liangXiValue);
//                                break;
//                            case "R151":
//                                paXingValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(paXingValue);
//                                break;
//                            case "R147":
//                                niaoLeiValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(niaoLeiValue);
//                                break;
//                            case "R148":
//                                yuLeiValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(yuLeiValue);
//                                break;
//                            case "R152":
//                                zhiWuValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(zhiWuValue);
//                                break;
//                            case "R153":
//                                qiTaValue = tBasData.getDataValue();
//                                zongShuValue += Double.parseDouble(qiTaValue);
//                                break;
//                        }
//                    }
//
//                    //占比进行计算
//                    puRuPercentage = getDataPercentage(puRuValue, String.valueOf(zongShuValue)) + "%";
//                    ruanTiPercentage = getDataPercentage(ruanTiValue, String.valueOf(zongShuValue)) + "%";
//                    liangXiPercentage = getDataPercentage(liangXiValue, String.valueOf(zongShuValue)) + "%";
//                    paXingPercentage = getDataPercentage(paXingValue, String.valueOf(zongShuValue)) + "%";
//                    niaoLeiPercentage = getDataPercentage(niaoLeiValue, String.valueOf(zongShuValue)) + "%";
//                    yuLeiPercentage = getDataPercentage(yuLeiValue, String.valueOf(zongShuValue)) + "%";
//                    zhiWuPercentage = getDataPercentage(zhiWuValue, String.valueOf(zongShuValue)) + "%";
//                    qiTaPercentage = getDataPercentage(qiTaValue, String.valueOf(zongShuValue)) + "%";
//
//                    //获得历史数据
//                    List<TBasData> dangerDataInfoStart = this.serviceFactory.getDataService().getDataInfoByCountryIndexDate(dangerIndexCode, dangerCountryCode, dangerStartYear);
//                    for (TBasData tBasDataTwo : dangerDataInfoStart) {
//                        switch (tBasDataTwo.getIndexCode()) {
//                            case "R149":
//                                puRuTotalTrend = getIndexTrend(tBasDataTwo, puRuValue);
//                                break;
//                            case "R150":
//                                ruanTiTotalTrend = getIndexTrend(tBasDataTwo, ruanTiValue);
//                                break;
//                            case "R146":
//                                liangXiTotalTrend = getIndexTrend(tBasDataTwo, liangXiValue);
//                                break;
//                            case "R151":
//                                paXingTotalTrend = getIndexTrend(tBasDataTwo, paXingValue);
//                                break;
//                            case "R147":
//                                niaoLeiTotalTrend = getIndexTrend(tBasDataTwo, niaoLeiValue);
//                                break;
//                            case "R148":
//                                yuLeiTotalTrend = getIndexTrend(tBasDataTwo, yuLeiValue);
//                                break;
//                            case "R152":
//                                zhiWuTotalTrend = getIndexTrend(tBasDataTwo, zhiWuValue);
//                                break;
//                            case "R153":
//                                qiTaTotalTrend = getIndexTrend(tBasDataTwo, qiTaValue);
//                                break;
//                        }
//                    }
//                    //保留小数部分
//                    puRuValue = RetainedDecimal(puRuValue, 2, 1.0);
//                    ruanTiValue = RetainedDecimal(ruanTiValue, 2, 1.0);
//                    liangXiValue = RetainedDecimal(liangXiValue, 2, 1.0);
//                    paXingValue = RetainedDecimal(paXingValue, 2, 1.0);
//                    niaoLeiValue = RetainedDecimal(niaoLeiValue, 2, 1.0);
//                    yuLeiValue = RetainedDecimal(yuLeiValue, 2, 1.0);
//                    zhiWuValue = RetainedDecimal(zhiWuValue, 2, 1.0);
//                    qiTaValue = RetainedDecimal(qiTaValue, 2, 1.0);
//
//                    //替换图片占位符
//                    dataMap.put("yearDanger", dangerEndYear);
//                    reportDesc = reportDesc.replace("${year}", dangerEndYear);
//                    reportDesc = reportDesc.replace("${countryName}", countryName);
//                    reportDesc = reportDesc.replace("${PuRuValue}", puRuValue);    //哺乳动物
//                    reportDesc = reportDesc.replace("${PuRuPercentage}", puRuPercentage);
//                    reportDesc = reportDesc.replace("${RuanTiValue}", ruanTiValue);   //软体动物
//                    reportDesc = reportDesc.replace("${RuanTiPercentage}", ruanTiPercentage);
//                    reportDesc = reportDesc.replace("${LiangXiValue}", liangXiValue);    //两栖动物
//                    reportDesc = reportDesc.replace("${LiangXiPercentage}", liangXiPercentage);
//                    reportDesc = reportDesc.replace("${PaXingValue}", paXingValue);    //爬行动物
//                    reportDesc = reportDesc.replace("${PaXingPercentage}", paXingPercentage);
//                    reportDesc = reportDesc.replace("${NiaoLeiValue}", niaoLeiValue);   //鸟类
//                    reportDesc = reportDesc.replace("${NiaoLeiPercentage}", niaoLeiPercentage);
//                    reportDesc = reportDesc.replace("${YuLeiValue}", yuLeiValue);    //鱼类
//                    reportDesc = reportDesc.replace("${YuLeiPercentage}", yuLeiPercentage);
//                    reportDesc = reportDesc.replace("${ZhiWuValue}", zhiWuValue);    //植物
//                    reportDesc = reportDesc.replace("${ZhiWuPercentage}", zhiWuPercentage);
//                    reportDesc = reportDesc.replace("${QiTaValue}", qiTaValue);   //其他
//                    reportDesc = reportDesc.replace("${QiTaPercentage}", qiTaPercentage);
//                    reportDesc = reportDesc.replace("${startYear}", startYear);
//                    reportDesc = reportDesc.replace("${endYear}", endYear);
//                    reportDesc = reportDesc.replace("${PuRuTotalTrend}", puRuTotalTrend);    //哺乳趋势
//                    reportDesc = reportDesc.replace("${RuanTiTotalTrend}", ruanTiTotalTrend);   //软体趋势
//                    reportDesc = reportDesc.replace("${LiangXiTotalTrend}", liangXiTotalTrend);   //两栖动物趋势
//                    reportDesc = reportDesc.replace("${PaXingTotalTrend}", paXingTotalTrend);   //爬行趋势
//                    reportDesc = reportDesc.replace("${NiaoLeiTotalTrend}", niaoLeiTotalTrend);   //鸟类趋势
//                    reportDesc = reportDesc.replace("${YuLeiTotalTrend}", yuLeiTotalTrend);   //鱼类
//                    reportDesc = reportDesc.replace("${ZhiWuTotalTrend}", zhiWuTotalTrend);   //植物趋势
//                    reportDesc = reportDesc.replace("${QiTaTotalTrend}", qiTaTotalTrend);   //其他
//                }
//                break;
//            case "WaterProjectsDesc":
//                reportMap = serviceFactory.getDataService().getReprotModelByIndexCode("WaterProjects");
//                reportDesc = (String) reportMap.get("reportDesc");
//                //设置参数
//                int waterProjectsTotal = 0;
//                int waterProjectsOneTotal = 0;
//                int waterProjectsTwoTotal = 0;
//                int waterProjectsThrTotal = 0;
//                int waterProjectsFouTotal = 0;
//                int waterProjectsNewTotal = 0;
//
//                //开始计算
//                List<String> projectCountryCode = new ArrayList<String>();
//                projectCountryCode.add(countryCode);
//                List<Map<String, Object>> damInfo = this.serviceFactory.getGlobalDamService().getDamInfoByCountryYear(endYear, projectCountryCode);
//
//                for (Map<String, Object> map : damInfo) {
//                    try {
//                        double damCap = Double.parseDouble(map.get("DAM_CAPACITY").toString());  //百万立方米
//                        double damCapNew = damCap / 100;  //亿立方米
//                        if (damCapNew < 10) {
//                            waterProjectsOneTotal++;
//                        } else if (damCapNew < 50) {
//                            waterProjectsTwoTotal++;
//                        } else if (damCapNew < 100) {
//                            waterProjectsThrTotal++;
//                        } else if (damCapNew >= 100) {
//                            waterProjectsFouTotal++;
//                        }
//                        waterProjectsTotal++;
//
//                        String strYearNew = map.get("DAM_YEAR").toString();
//                        if (Integer.parseInt(strYearNew) >= Integer.parseInt(startYear) && Integer.parseInt(strYearNew) < Integer.parseInt(endYear)) {
//                            waterProjectsNewTotal++;
//                        }
//
//                    } catch (Exception e) {
//                        continue;
//                    }
//                }
//                //替换图片占位符
//                dataMap.put("yearProject", endYear);
//                reportDesc = reportDesc.replace("${year}", endYear);
//                reportDesc = reportDesc.replace("${countryName}", countryName);
//                reportDesc = reportDesc.replace("${WaterProjectsTotal}", String.valueOf(waterProjectsTotal));    //总数量
//                reportDesc = reportDesc.replace("${WaterProjectsOneTotal}", String.valueOf(waterProjectsOneTotal));   //大于0
//                reportDesc = reportDesc.replace("${WaterProjectsTwoTotal}", String.valueOf(waterProjectsTwoTotal));   //大于10
//                reportDesc = reportDesc.replace("${WaterProjectsThrTotal}", String.valueOf(waterProjectsThrTotal));    //大于50
//                reportDesc = reportDesc.replace("${WaterProjectsFouTotal}", String.valueOf(waterProjectsFouTotal));    //大于100
//                reportDesc = reportDesc.replace("${startYear}", startYear);
//                reportDesc = reportDesc.replace("${endYear}", endYear);
//                reportDesc = reportDesc.replace("${WaterProjectsNewTotal}", String.valueOf(waterProjectsNewTotal));    //大于创建时间
//                break;
//
//        }
//        return reportDesc;
//    }
//
//    /***
//     * 判断趋势方法
//     * @author:zhaobc
//     * @param tBasData
//     * @param value
//     * @return
//     * @creatTime:2018-11-9 - 上午10:59:19
//     */
//    public String getIndexTrend(TBasData tBasData, String value) {
//        String startDataOne = tBasData.getDataValue();
//        String valueTotalTrend = "";
//        if (value.length() > 0 && startDataOne.length() > 0) {
//            double douStartDataOne = Double.parseDouble(startDataOne);
//            double douEndDataOne = Double.parseDouble(value);
//            if (douEndDataOne - douStartDataOne > 0) {
//                valueTotalTrend = "上涨";
//            } else if (douEndDataOne - douStartDataOne < 0) {
//                valueTotalTrend = "下降";
//            } else {
//                valueTotalTrend = "平稳";
//            }
//        }
//        return valueTotalTrend;
//    }
//
//
//    /***
//     * 计算百分比  保留两位小数
//     * @author:zhaobc
//     * @param tBasData
//     * @param value
//     * @return
//     * @creatTime:2018-11-9 - 上午10:59:19
//     */
//    public double getDataPercentage(String num, String totalNum) {
//        double value = 0;
//        if (num.length() > 0 && totalNum.length() > 0) {
//            double douNum = Double.parseDouble(num);
//            double douTotalNum = Double.parseDouble(totalNum);
//            value = douNum / douTotalNum * 100;
//            BigDecimal valueBig = new BigDecimal(value);
//            value = valueBig.setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue();
//        }
//        return value;
//    }
//
//    /**
//     * 填充柱状图的数据_T
//     *
//     * @param year
//     * @param listCountryCode
//     * @param indexCode
//     * @return
//     * @author:zhaobc
//     * @creatTime:2018-11-9 - 下午4:17:26
//     */
//    private BarChart getBarChartData(List<Map<String, Object>> dataList, List<String> indexCode, String indexType, String year) {
//        BarChart data = new BarChart();
//        //图例显示的数据
//        List<String> navdata = new ArrayList<String>();
//        //X轴显示的数据
//        List<String> xAxisdata = new ArrayList<String>();
//        List<OData> opinionData = new ArrayList<OData>();
//
//        Map<String, String> indexMap = new HashMap<String, String>();
//        indexMap.put("R164", "草原");
//        indexMap.put("R165", "稀疏植被");
//        indexMap.put("R166", "裸露区");
//        indexMap.put("R167", "人工表面");
//        indexMap.put("R168", "内陆水域");
//        indexMap.put("R169", "农田");
//        indexMap.put("R170", "树木覆盖");
//        indexMap.put("R171", "灌丛");
//        indexMap.put("R172", "湿地");
//        indexMap.put("R149", "哺乳动物");
//        indexMap.put("R150", "软体动物");
//        indexMap.put("R146", "两栖动物");
//        indexMap.put("R151", "爬行动物");
//        indexMap.put("R147", "鸟类");
//        indexMap.put("R148", "鱼类");
//        indexMap.put("R152", "植物");
//        indexMap.put("R153", "其他");
//
//        //大坝特殊值
//        double waterProjectsOneTotal = 0;
//        double waterProjectsTwoTotal = 0;
//        double waterProjectsThrTotal = 0;
//        double waterProjectsFouTotal = 0;
//        //判断模式是否为通用模式  添加数据不同
//        boolean isCommon = true;
//        Map<String, Double> projectMap = new HashMap<String, Double>();
//        //柱形图数据（单条）
//        List<Double> odata = new ArrayList<Double>();
//
//        for (Map<String, Object> dataInfo : dataList) {
//            try {
//                //获得年份值
//                String yearValue = year;
//                double value = 0.00;  //数据
//                String codeName = ""; //图例
//                //切换单位
//                switch (indexType) {
//                    case "weather":
//                        //添加图例
//                        yearValue = dataInfo.get("YEAR").toString() + "年";
//                        if (!navdata.contains(yearValue)) {
//                            navdata.add(yearValue);
//                        }
//                        value = Double.parseDouble(dataInfo.get("VALUE").toString());
//                        codeName = dataInfo.get("MONTH").toString();
//                        xAxisdata.add(codeName);
//                        //保留小数部分
//                        value = RetainedDecimalDouble(value, 2, 1.0);
//                        odata.add(value);
//                        break;
//                    case "data":
//                        //添加图例
//                        yearValue = dataInfo.get("dataYear").toString() + "年";
//                        if (!navdata.contains(yearValue)) {
//                            navdata.add(yearValue);
//                        }
//                        value = Double.parseDouble(dataInfo.get("dataValue").toString());
//                        codeName = dataInfo.get("countryName").toString();
//                        xAxisdata.add(codeName);
//                        //保留小数部分
//                        String indexCodeData = dataInfo.get("INDEX_CODE").toString();
//                        switch (indexCodeData) {
//                            case "R007":
//                                //人转亿人
//                                value = RetainedDecimalDouble(value, 2, 100000000.0);
//                                break;
//                            case "R013":
//                                //美元转千亿元
//                                value = RetainedDecimalDouble(value, 2, 100000000000.0);
//                                break;
//                            case "R090":
//                                //人均可再生淡水
//                                value = RetainedDecimalDouble(value, 2, 1.0);
//                                break;
//                        }
//                        odata.add(value);
//                        break;
//                    case "dataTwo":
//                        //添加图例
//                        yearValue = dataInfo.get("DATA_YEAR").toString() + "年";
//                        if (!navdata.contains(yearValue)) {
//                            navdata.add(yearValue);
//                        }
//                        //修改通用模式
//                        isCommon = false;
//                        List<Double> odataNew = new ArrayList<Double>();
//                        for (String strIndex : indexCode) {
//                            value = Double.parseDouble(dataInfo.get(strIndex).toString());
//                            xAxisdata.add(indexMap.get(strIndex).toString());
//                            //保留小数部分
//                            value = RetainedDecimalDouble(value, 2, 1.0);
//                            odataNew.add(value);
//                        }
//                        OData odataThr = new OData(odataNew);
//                        opinionData.add(odataThr);
//                        break;
//                    case "AQI":
//                        //添加图例
//                        yearValue = dataInfo.get("MONITORTIME").toString().substring(0, 4) + "年";
//                        if (!navdata.contains(yearValue)) {
//                            navdata.add(yearValue);
//                        }
//                        value = Double.parseDouble(dataInfo.get("AQI").toString());
//                        codeName = dataInfo.get("MONITORTIME").toString();
//                        //获得月份
//                        codeName = codeName.substring(codeName.indexOf("-") + 1);
//                        xAxisdata.add(codeName);
//                        //保留小数部分
//                        value = RetainedDecimalDouble(value, 2, 1.0);
//                        odata.add(value);
//                        break;
//                    case "gam":
//                        //添加图例
//                        if (!navdata.contains(yearValue)) {
//                            navdata.add(yearValue);
//                        }
//                        //修改通用模式
//                        isCommon = false;
//                        value = Double.parseDouble(dataInfo.get("DAM_CAPACITY").toString());
//                        double damCapNew = value / 100;  //亿立方米
//                        if (damCapNew < 10) {
//                            waterProjectsOneTotal++;
//                            odata.add(0, waterProjectsOneTotal);
//                        } else if (damCapNew < 50) {
//                            waterProjectsTwoTotal++;
//                            odata.add(1, waterProjectsTwoTotal);
//                        } else if (damCapNew < 100) {
//                            waterProjectsThrTotal++;
//                            odata.add(2, waterProjectsThrTotal);
//                        } else if (damCapNew >= 100) {
//                            waterProjectsFouTotal++;
//                            odata.add(3, waterProjectsFouTotal);
//                        }
//                        if (dataList.get(dataList.size() - 1).get("DAM_CAPACITY").toString().equals(dataInfo.get("DAM_CAPACITY").toString())) {
//                            projectMap.put("<10亿立方米", waterProjectsOneTotal);
//                            projectMap.put("10-50亿立方米", waterProjectsTwoTotal);
//                            projectMap.put("50-100亿立方米", waterProjectsThrTotal);
//                            projectMap.put(">100亿立方米", waterProjectsFouTotal);
//                            List<Double> odataFivNew = new ArrayList<Double>();
//                            for (String key : projectMap.keySet()) {
//                                xAxisdata.add(key);
//                                odataFivNew.add(projectMap.get(key));
//                            }
//                            OData odataFiv = new OData(odataFivNew);
//                            opinionData.add(odataFiv);
//                        }
//                        break;
//                }
//            } catch (Exception e) {
//                continue;
//            }
//        }
//        //判断类型
//        if (isCommon) {
//            OData odataS = new OData(odata);
//            opinionData.add(odataS);
//        }
//        data.setNavdata(navdata);
//        data.setxAxisdata(xAxisdata);
//        data.setOpinionData(opinionData);
//        return data;
//    }
//
//
//    /**
//     * 填充折线图的数据_T
//     *
//     * @return
//     */
//    private ChartData getLineChartData(List<Map<String, Object>> dataList, List<String> indexCode, String indexType) {
//        Map<String, String> mapIndexCode = new HashMap<String, String>();
//        mapIndexCode.put("R164", "草原");
//        mapIndexCode.put("R165", "稀疏植被");
//        mapIndexCode.put("R166", "裸露区");
//        mapIndexCode.put("R167", "人工表面");
//        mapIndexCode.put("R168", "内陆水域");
//        mapIndexCode.put("R169", "农田");
//        mapIndexCode.put("R170", "树木覆盖");
//        mapIndexCode.put("R171", "灌丛");
//        mapIndexCode.put("R172", "湿地");
//        mapIndexCode.put("R149", "哺乳动物");
//        mapIndexCode.put("R150", "软体动物");
//        mapIndexCode.put("R146", "两栖动物");
//        mapIndexCode.put("R151", "爬行动物");
//        mapIndexCode.put("R147", "鸟类");
//        mapIndexCode.put("R148", "鱼类");
//        mapIndexCode.put("R152", "植物");
//        mapIndexCode.put("R153", "其他");
//        //判断模式是否为通用模式  添加数据不同
//        boolean isCommon = true;
//        ChartData data = new ChartData();
//        //图例显示的数据
//        List<String> navdata = new ArrayList<String>();
//        //X轴显示的数据
//        List<String> xAxisdata = new ArrayList<String>();
//        //折线图的数据（多条）
//        List<List<Double>> opinionData = new ArrayList<List<Double>>();
//        List<Double> odata = new ArrayList<Double>();
//        for (int i = 0; i < dataList.size(); i = i + 2) {
//            try {
//                int year = 0;
//                Double value = 0.00;
//                //判断指标项
//                switch (indexType) {
//                    case "weather": // 气温模块
//                        year = Integer.parseInt(dataList.get(i).get("year").toString());
//                        value = Double.parseDouble(dataList.get(i).get("VALUE").toString());
//                        if (!navdata.contains(dataList.get(i).get("NAME_CN").toString())) {
//                            navdata.add(dataList.get(i).get("NAME_CN").toString());
//                        }
//                        //保留小数部分
//                        value = RetainedDecimalDouble(value, 2, 1.0);
//                        odata.add(value);
//                        break;
//
//                    case "data":
//                        year = Integer.parseInt(dataList.get(i).get("dataYear").toString());
//                        value = Double.parseDouble(dataList.get(i).get("dataValue").toString());
//                        if (!navdata.contains(dataList.get(i).get("countryName").toString())) {
//                            navdata.add(dataList.get(i).get("countryName").toString());
//                        }
//                        //保留小数部分
//                        String indexCodeData = dataList.get(i).get("INDEX_CODE").toString();
//                        switch (indexCodeData) {
//                            case "R007":
//                                //人转亿人
//                                value = RetainedDecimalDouble(value, 2, 100000000.0);
//                                break;
//                            case "R013":
//                                //美元转千亿元
//                                value = RetainedDecimalDouble(value, 2, 100000000000.0);
//                                break;
//                            case "R090":
//                                //人均可再生淡水
//                                value = RetainedDecimalDouble(value, 2, 1.0);
//                                break;
//                        }
//                        odata.add(value);
//                        break;
//
//                    case "dataTwo":
//                        //为了只执行一次
//                        if (isCommon == false) {
//                            break;
//                        }
//                        isCommon = false;
//                        for (String strIndex : indexCode) {
//                            //图例
//                            if (!navdata.contains(mapIndexCode.get(strIndex))) {
//                                navdata.add(mapIndexCode.get(strIndex));
//                            }
//                            List<Double> odataTwoNew = new ArrayList<Double>();
//                            for (Map<String, Object> dataInfoTwo : dataList) {
//                                String strYear = dataInfoTwo.get("DATA_YEAR").toString();
//                                //判断X轴的值
//                                if (!xAxisdata.contains(strYear)) {
//                                    xAxisdata.add(strYear);
//                                }
//                                value = Double.parseDouble(dataInfoTwo.get(strIndex)
//                                        .toString());
//                                //保留小数部分
//                                value = RetainedDecimalDouble(value, 2, 1.0);
//                                odataTwoNew.add(value);
//                            }
//                            OData odataTwo = new OData(odataTwoNew);
//                            opinionData.add(odataTwo);
//                        }
//
//                        break;
//                    case "AQI":
//                        year = Integer.parseInt(dataList.get(i).get("MONITORTIME").toString());
//                        value = Double
//                                .parseDouble(dataList.get(i).get("AQI").toString());
//                        if (!navdata.contains(dataList.get(i).get("CITY_NAME_CN").toString())) {
//                            navdata.add(dataList.get(i).get("CITY_NAME_CN").toString());
//                        }
//                        // 保留小数
//                        //保留小数部分
//                        value = RetainedDecimalDouble(value, 2, 1.0);
//                        odata.add(value);
//                        break;
//                    case "gam":
//
//                        break;
//                }
//                if (isCommon) {
//                    xAxisdata.add(String.valueOf(year));
//                }
//            } catch (Exception e) {
//                continue;
//            }
//
//        }
//        if (isCommon) {
//            OData OdataTwo = new OData(odata);
//            opinionData.add(OdataTwo);
//        }
//        data.setLegend(navdata);
//        data.setxAxisdata(xAxisdata);
//        data.setOpinionData(opinionData);
//        return data;
//    }
//
//
//    /**
//     * 表格数据换算
//     *
//     * @param dataMap     为占位符替换值 表头部分
//     * @param listDataMap //原始数据
//     * @param tableType   表类型
//     * @param countryName 国家名称
//     * @param startYear
//     * @param endYear
//     * @return
//     * @author:zhaobc
//     * @creatTime:2018-11-12 - 下午6:40:07
//     */
//    public List<Map<String, Object>> getTableConvert(List<Map<String, Object>> listDataMap, Map<String, String> mapTableTitle, String tableType, String countryName, String startYear, String endYear) {
//        //创建数字索引
//        List<String> listNumberIndex = new ArrayList<String>();
//        listNumberIndex.add("One");
//        listNumberIndex.add("Two");
//        listNumberIndex.add("Thr");
//        listNumberIndex.add("Fou");
//        listNumberIndex.add("Fiv");
//        listNumberIndex.add("Six");
//        listNumberIndex.add("Sev");
//        listNumberIndex.add("Eig");
//        listNumberIndex.add("Nin");
//        listNumberIndex.add("Ten");
//
//        Map<String, String> mapNumberIndex = new HashMap<String, String>();
//        mapNumberIndex.put("yearOne", "One");
//        mapNumberIndex.put("yearTwo", "Two");
//        mapNumberIndex.put("yearThr", "Thr");
//        mapNumberIndex.put("yearFou", "Fou");
//        mapNumberIndex.put("yearFiv", "Fiv");
//        mapNumberIndex.put("yearSix", "Six");
//        mapNumberIndex.put("yearSev", "Sev");
//        mapNumberIndex.put("yearEig", "Eig");
//        mapNumberIndex.put("yearNin", "Nin");
//        mapNumberIndex.put("yearTen", "Ten");
//
//        //表头索引  土地利用表头
//        List<String> listSoilTitle = new ArrayList<String>();
//        listSoilTitle.add("soilOne");
//        listSoilTitle.add("soilTwo");
//        listSoilTitle.add("soilThr");
//        listSoilTitle.add("soilFou");
//        listSoilTitle.add("soilFiv");
//        listSoilTitle.add("soilSix");
//        listSoilTitle.add("soilSev");
//        listSoilTitle.add("soilEig");
//        listSoilTitle.add("soilNin");
//
//        //表头索引 受威胁物种表头
//        List<String> listDangerTitle = new ArrayList<String>();
//        listDangerTitle.add("dangeOne");
//        listDangerTitle.add("dangeTwo");
//        listDangerTitle.add("dangeThr");
//        listDangerTitle.add("dangeFou");
//        listDangerTitle.add("dangeFiv");
//        listDangerTitle.add("dangeSix");
//        listDangerTitle.add("dangeSev");
//        listDangerTitle.add("dangeEig");
//
//        //月份索引
//        Map<String, Object> mapMonthIndex = new HashMap<String, Object>();
//        mapMonthIndex.put("Jap", "0");
//        mapMonthIndex.put("Feb", "0");
//        mapMonthIndex.put("Mar", "0");
//        mapMonthIndex.put("Apr", "0");
//        mapMonthIndex.put("May", "0");
//        mapMonthIndex.put("Jun", "0");
//        mapMonthIndex.put("Jul", "0");
//        mapMonthIndex.put("Aug", "0");
//        mapMonthIndex.put("Sept", "0");
//        mapMonthIndex.put("Oct", "0");
//        mapMonthIndex.put("Nov", "0");
//        mapMonthIndex.put("Dec", "0");
//
//        //土地利用索引
//        Map<String, String> soilUseMap = new HashMap<String, String>();
//        soilUseMap.put("R164", "草原（KM2）");
//        soilUseMap.put("R165", "稀疏植被（KM2）");
//        soilUseMap.put("R166", "裸露区（KM2）");
//        soilUseMap.put("R167", "人工表面（KM2）");
//        soilUseMap.put("R168", "内陆水域（KM2）");
//        soilUseMap.put("R169", "农田（KM2）");
//        soilUseMap.put("R170", "树木覆盖（KM2）");
//        soilUseMap.put("R171", "灌丛（KM2）");
//        soilUseMap.put("R172", "湿地（KM2）");
//
//        //威胁物种索引
//        Map<String, String> dangerSpeciesMap = new HashMap<String, String>();
//        dangerSpeciesMap.put("R149", "哺乳动物（个）");
//        dangerSpeciesMap.put("R150", "软体动物（个）");
//        dangerSpeciesMap.put("R146", "两栖动物（个）");
//        dangerSpeciesMap.put("R151", "爬行动物（个）");
//        dangerSpeciesMap.put("R147", "鸟类（个）");
//        dangerSpeciesMap.put("R148", "鱼类（个）");
//        dangerSpeciesMap.put("R152", "植物（个）");
//        dangerSpeciesMap.put("R153", "其他（个）");
//
//        List<Map<String, Object>> listDataTwo = new ArrayList<Map<String, Object>>();
//        switch (tableType) {
//            case "monthWeather":
//                //mapMonthIndex
//                for (Map<String, Object> mapTwo : listDataMap) {
//                    String month = mapTwo.get("MONTH").toString();
//                    String value = mapTwo.get("VALUE").toString();
//                    //保留小数部分
//                    value = RetainedDecimal(value, 2, 1.0);
//                    switch (month) {
//                        case "1":
//                            mapMonthIndex.put("Jap", value);
//                            break;
//                        case "2":
//                            mapMonthIndex.put("Feb", value);
//                            break;
//                        case "3":
//                            mapMonthIndex.put("Mar", value);
//                            break;
//                        case "4":
//                            mapMonthIndex.put("Apr", value);
//                            break;
//                        case "5":
//                            mapMonthIndex.put("May", value);
//                            break;
//                        case "6":
//                            mapMonthIndex.put("Jun", value);
//                            break;
//                        case "7":
//                            mapMonthIndex.put("Jul", value);
//                            break;
//                        case "8":
//                            mapMonthIndex.put("Aug", value);
//                            break;
//                        case "9":
//                            mapMonthIndex.put("Sept", value);
//                            break;
//                        case "10":
//                            mapMonthIndex.put("Oct", value);
//                            break;
//                        case "11":
//                            mapMonthIndex.put("Nov", value);
//                            break;
//                        case "12":
//                            mapMonthIndex.put("Dec", value);
//                            break;
//                    }
//                }
//                mapMonthIndex.put("Name", countryName);
//                listDataTwo.add(mapMonthIndex);
//                break;
//            case "monthAqi":
//                //mapMonthIndex
//                for (Map<String, Object> mapTwo : listDataMap) {
//                    String month = mapTwo.get("MONITORTIME").toString();
//                    String value = mapTwo.get("AQI").toString();
//                    String monthIndex = month.substring(month.length() - 2, month.length());
//                    //保留小数部分
//                    value = RetainedDecimal(value, 2, 1.0);
//                    switch (monthIndex) {
//                        case "01":
//                            mapMonthIndex.put("Jap", value);
//                            break;
//                        case "02":
//                            mapMonthIndex.put("Feb", value);
//                            break;
//                        case "03":
//                            mapMonthIndex.put("Mar", value);
//                            break;
//                        case "04":
//                            mapMonthIndex.put("Apr", value);
//                            break;
//                        case "05":
//                            mapMonthIndex.put("May", value);
//                            break;
//                        case "06":
//                            mapMonthIndex.put("Jun", value);
//                            break;
//                        case "07":
//                            mapMonthIndex.put("Jul", value);
//                            break;
//                        case "08":
//                            mapMonthIndex.put("Aug", value);
//                            break;
//                        case "09":
//                            mapMonthIndex.put("Sept", value);
//                            break;
//                        case "10":
//                            mapMonthIndex.put("Oct", value);
//                            break;
//                        case "11":
//                            mapMonthIndex.put("Nov", value);
//                            break;
//                        case "12":
//                            mapMonthIndex.put("Dec", value);
//                            break;
//                    }
//                }
//                mapMonthIndex.put("Name", countryName);
//                listDataTwo.add(mapMonthIndex);
//                break;
//            case "monthSoilUse": //土地利用 月度
//                Map<String, Object> mapSoilUseData = new HashMap<String, Object>();
//                int soilUse = 0;
//                for (String key : soilUseMap.keySet()) {
//                    String strKey = listNumberIndex.get(soilUse);
//                    if (strKey != null && strKey.length() > 0) {
//                        String value = listDataMap.get(0).get(key).toString();
//                        //保留小数部分
//                        value = RetainedDecimal(value, 2, 1.0);
//                        mapSoilUseData.put(strKey, value);
//                        // 表头替换
//                        String strKeyTitle = listSoilTitle.get(soilUse);
//                        dataMap.put(strKeyTitle, soilUseMap.get(key));
//                    }
//                    soilUse++;
//                }
//                mapSoilUseData.put("Name", countryName);
//                listDataTwo.add(mapSoilUseData);
//                break;
//            case "monthDangerSpecies": //威胁物种 月度
//                Map<String, Object> mapDangerSpeciesData = new HashMap<String, Object>();
//                int dangerSpecies = 0;
//                for (String key : dangerSpeciesMap.keySet()) {
//                    String strKey = listNumberIndex.get(dangerSpecies);
//                    if (strKey != null && strKey.length() > 0) {
//                        String value = listDataMap.get(0).get(key).toString();
//                        //保留小数部分
//                        value = RetainedDecimal(value, 2, 1.0);
//                        mapDangerSpeciesData.put(strKey, value);
//                        // 表头替换
//                        String strKeyTitle = listDangerTitle.get(dangerSpecies);
//                        dataMap.put(strKeyTitle, dangerSpeciesMap.get(key));
//                    }
//                    dangerSpecies++;
//                }
//                mapDangerSpeciesData.put("Name", countryName);
//                listDataTwo.add(mapDangerSpeciesData);
//                break;
//            case "nunberWeather": // 天气转换 数字
//                Map<String, Object> mapNumData = new HashMap<String, Object>();
//                //循环表头
//                for (Map.Entry<String, String> mapTitle : mapTableTitle.entrySet()) {
//                    mapNumData.put(mapNumberIndex.get(mapTitle.getKey()), "-");
//                    //判断表头信息
//                    for (Map<String, Object> map : listDataMap) {
//                        String year = map.get("year").toString();
//                        String yearNew = mapTitle.getValue();
//                        if (yearNew.equals(year)) {
//                            String value = map.get("VALUE").toString();
//                            //保留小数部分
//                            value = RetainedDecimal(value, 2, 1.0);
//                            String strKey = mapNumberIndex.get(mapTitle.getKey());
//                            mapNumData.put(strKey, value);
//                        }
//                    }
//                }
//                mapNumData.put("Name", countryName);
//                listDataTwo.add(mapNumData);
//                break;
//            case "nunberData": // 通用转换 数字
//                Map<String, Object> mapData = new HashMap<String, Object>();
//                for (Map.Entry<String, String> mapTitle : mapTableTitle.entrySet()) {
//                    mapData.put(mapNumberIndex.get(mapTitle.getKey()), "-");
//                    //判断表头信息
//                    for (Map<String, Object> map : listDataMap) {
//                        String year = map.get("dataYear").toString();
//                        String yearNew = mapTitle.getValue();
//                        if (yearNew.equals(year)) {
//                            String value = map.get("dataValue").toString();
//                            //保留小数部分
//                            String indexCode = map.get("INDEX_CODE").toString();
//                            switch (indexCode) {
//                                case "R007":
//                                    //人转亿人
//                                    value = RetainedDecimal(value, 2, 100000000.0);
//                                    break;
//                                case "R013":
//                                    //美元转千亿元
//                                    value = RetainedDecimal(value, 2, 100000000000.0);
//                                    break;
//                                case "R090":
//                                    //人均可再生淡水
//                                    value = RetainedDecimal(value, 2, 1.0);
//                                    break;
//                            }
//                            String strKey = mapNumberIndex.get(mapTitle.getKey());
//                            mapData.put(strKey, value);
//                        }
//                    }
//                }
//                mapData.put("Name", countryName);
//                listDataTwo.add(mapData);
//                break;
//            case "nunberAQI": // AQI转换 数字
//                Map<String, Object> mapNumAqiData = new HashMap<String, Object>();
//                for (Map.Entry<String, String> mapTitle : mapTableTitle.entrySet()) {
//                    mapNumAqiData.put(mapNumberIndex.get(mapTitle.getKey()), "-");
//                    //判断表头信息
//                    for (Map<String, Object> map : listDataMap) {
//                        String year = map.get("MONITORTIME").toString();
//                        String yearNew = mapTitle.getValue();
//                        if (yearNew.equals(year)) {
//                            String value = map.get("AQI").toString();
//                            //保留小数部分
//                            value = RetainedDecimal(value, 2, 1.0);
//                            String strKey = mapNumberIndex.get(mapTitle.getKey());
//                            mapNumAqiData.put(strKey, value);
//                        }
//                    }
//                }
//                mapNumAqiData.put("Name", countryName);
//                listDataTwo.add(mapNumAqiData);
//                break;
//            case "nunberSoilUse": // 土地利用 数字
//                for (int i = 0; i < listDataMap.size(); i++) {
//                    //年份
//                    int soilUseNum = 0;
//                    Map<String, Object> mapNumSoilUseData = new HashMap<String, Object>();
//                    String strKeyOne = listNumberIndex.get(soilUseNum);
//                    mapNumSoilUseData.put(strKeyOne, listDataMap.get(i).get("DATA_YEAR"));
//                    soilUseNum++;
//                    for (String key : soilUseMap.keySet()) {
//                        String strKey = listNumberIndex.get(soilUseNum);
//                        if (strKey != null && strKey.length() > 0) {
//                            String value = listDataMap.get(i).get(key).toString();
//                            //保留小数部分
//                            value = RetainedDecimal(value, 2, 1.0);
//                            mapNumSoilUseData.put(strKey, value);
//                            // 表头替换 替换一次
//                            if (i == 0) {
//                                String strKeyTitle = listSoilTitle.get(soilUseNum - 1);
//                                dataMap.put(strKeyTitle, soilUseMap.get(key));
//                            }
//                        }
//                        soilUseNum++;
//                    }
//                    mapNumSoilUseData.put("Name", countryName);
//                    listDataTwo.add(mapNumSoilUseData);
//                }
//                break;
//            case "nunberDangerSpecies": //受威胁物种 数字
//                for (int i = 0; i < listDataMap.size(); i++) {
//                    int dangerSpeciesDataNum = 0;
//                    Map<String, Object> mapNumDangerSpeciesData = new HashMap<String, Object>();
//                    String strKeyOne = listNumberIndex.get(dangerSpeciesDataNum);
//                    mapNumDangerSpeciesData.put(strKeyOne, listDataMap.get(i).get("DATA_YEAR"));
//                    dangerSpeciesDataNum++;
//                    for (String key : dangerSpeciesMap.keySet()) {
//                        String strKey = listNumberIndex.get(dangerSpeciesDataNum);
//                        if (strKey != null && strKey.length() > 0) {
//                            mapNumDangerSpeciesData.put(strKey, listDataMap.get(i).get(key));
//                            // 表头替换 替换一次
//                            if (i == 0) {
//                                String strKeyTitle = listDangerTitle.get(dangerSpeciesDataNum - 1);
//                                dataMap.put(strKeyTitle, dangerSpeciesMap.get(key));
//                            }
//                        }
//                        dangerSpeciesDataNum++;
//                    }
//                    mapNumDangerSpeciesData.put("Name", countryName);
//                    listDataTwo.add(mapNumDangerSpeciesData);
//                }
//                break;
//            case "nunberWaterProjects": //水利工程
//
//                for (int i = 0; i < listDataMap.size(); i++) {
//                    Map<String, Object> mapProjectsData = new HashMap<String, Object>();
//                    String one = listDataMap.get(i).get("DAM_YEAR").toString();
//                    if (Double.parseDouble(one) <= Double.parseDouble(endYear)) {
//                        String name = listDataMap.get(i).get("DAM_NAME").toString();
//                        mapProjectsData.put("Name", name);
//                        mapProjectsData.put("One", one);
//                        String two = listDataMap.get(i).get("DAM_CAPACITY").toString();
//                        mapProjectsData.put("Two", two);
//                        String thr = listDataMap.get(i).get("LAT").toString();
//                        mapProjectsData.put("Thr", thr);
//                        String fou = listDataMap.get(i).get("LON").toString();
//                        mapProjectsData.put("Fou", fou);
//                        listDataTwo.add(mapProjectsData);
//                    }
//
//                }
//
//                break;
//        }
//
//
//        return listDataTwo;
//    }
//
//
//    /***
//     * 保留小数
//     * @author:zhaobc
//     * @param strValue 保留的数据
//     * @param number 小数位数
//     * @param divisor 单位换算位数
//     * @return
//     * @creatTime:2018-11-19 - 上午11:55:56
//     */
//    public String RetainedDecimal(String strValue, int number, Double divisor) {
//        double douTemperature = 0.0;
//        if (strValue != null && strValue.length() > 0) {
//            douTemperature = (Double.parseDouble(strValue)) / divisor;
//            BigDecimal valueBig = new BigDecimal(douTemperature);
//            douTemperature = valueBig.setScale(number, BigDecimal.ROUND_HALF_DOWN).doubleValue();
//            if (douTemperature == 0) {
//                douTemperature = valueBig.setScale(number, BigDecimal.ROUND_UP).doubleValue();
//            }
//        }
//        return String.valueOf(douTemperature);
//    }
//
//
//    /***
//     * 保留小数
//     * @author:zhaobc
//     * @param strValue 保留的数据
//     *  * @param number 小数位数
//     * @return
//     * @creatTime:2018-11-19 - 上午11:55:56
//     */
//    public Double RetainedDecimalDouble(Double douValue, int number, Double divisor) {
//        douValue = douValue / divisor;
//        BigDecimal valueBig = new BigDecimal(douValue);
//        douValue = valueBig.setScale(number, BigDecimal.ROUND_HALF_DOWN).doubleValue();
//        if (douValue == 0) {
//            douValue = valueBig.setScale(number, BigDecimal.ROUND_UP).doubleValue();
//        }
//        return douValue;
//    }
//
}
