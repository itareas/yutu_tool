package com.yutu.utils.chart;

import java.util.List;

public class BarChart {
	
	//图例
		private List<String> Navdata;
		//x轴的数据
		private List<String> xAxisdata;
		//数据
		private List<OData> OpinionData;
		
		public List<String> getNavdata() {
			return Navdata;
		}
		public void setNavdata(List<String> navdata) {
			Navdata = navdata;
		}
		public List<String> getxAxisdata() {
			return xAxisdata;
		}
		public void setxAxisdata(List<String> xAxisdata) {
			this.xAxisdata = xAxisdata;
		}
		public List<OData> getOpinionData() {
			return OpinionData;
		}
		public void setOpinionData(List<OData> opinionData) {
			OpinionData = opinionData;
		}
		
		

}
