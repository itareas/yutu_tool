package com.yutu.entity.chart;

import java.util.List;

public class ChartData {
	
	//图例
	private List<String> legend;
	//x轴的数据
	private List<String> xAxis;
	//数据
	private List<List<Double>> data;

	public List<String> getLegend() {
		return legend;
	}

	public void setLegend(List<String> legend) {
		this.legend = legend;
	}

	public List<String> getxAxis() {
		return xAxis;
	}

	public void setxAxis(List<String> xAxis) {
		this.xAxis = xAxis;
	}

	public List<List<Double>> getData() {
		return data;
	}

	public void setData(List<List<Double>> data) {
		this.data = data;
	}
}
