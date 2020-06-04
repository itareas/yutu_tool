package com.yutu.utils.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PieLabelLinkStyle;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JfreeChartUtils {
	private static final String FILE_OUT_PUT_PATH = "D://";
	private static final Logger log = LoggerFactory.getLogger(JfreeChartUtils.class);
	private static String NO_DATA_MSG = "数据加载失败";
	private static Font FONT = new Font("楷体", Font.PLAIN, 20);
	private static Font FONT_BOLD = new Font("楷体",Font.BOLD+Font.PLAIN,20);
	public static Color[] CHART_COLORS = {
		new Color(31,129,188), new Color(92,92,97), new Color(144,237,125), new Color(255,188,117),
		new Color(153,158,255), new Color(255,117,153), new Color(253,236,109), new Color(128,133,232),
		new Color(158,90,102),new Color(255, 204, 102)
	};//颜色
	static {
		setChartTheme();
		}
	
	/**
     * 中文主题样式 解决乱码
     */
	public static void setChartTheme() {
		// 设置中文主题样式 解决乱码
		StandardChartTheme chartTheme = new StandardChartTheme("CN");
		// 设置标题字体，加粗
		chartTheme.setExtraLargeFont(FONT_BOLD);
		// 设置图例的字体
		chartTheme.setRegularFont(FONT);
		// 设置轴向的字体
		chartTheme.setLargeFont(FONT);
		
		chartTheme.setSmallFont(FONT);
		chartTheme.setTitlePaint(new Color(51, 51, 51));
		chartTheme.setSubtitlePaint(new Color(85, 85, 85));
		chartTheme.setLegendBackgroundPaint(Color.WHITE);// 设置标注
		chartTheme.setLegendItemPaint(Color.BLACK);//
		chartTheme.setChartBackgroundPaint(Color.WHITE);
		// 绘制颜色绘制颜色.轮廓供应商
		// paintSequence,outlinePaintSequence,strokeSequence,outlineStrokeSequence,shapeSequence
		Paint[] OUTLINE_PAINT_SEQUENCE = new Paint[] { Color.WHITE };
		// 绘制器颜色源
		DefaultDrawingSupplier drawingSupplier = new DefaultDrawingSupplier(CHART_COLORS, CHART_COLORS, OUTLINE_PAINT_SEQUENCE,
		DefaultDrawingSupplier.DEFAULT_STROKE_SEQUENCE, DefaultDrawingSupplier.DEFAULT_OUTLINE_STROKE_SEQUENCE,
		DefaultDrawingSupplier.DEFAULT_SHAPE_SEQUENCE);
		chartTheme.setDrawingSupplier(drawingSupplier);
		chartTheme.setPlotBackgroundPaint(Color.WHITE);// 绘制区域
		chartTheme.setPlotOutlinePaint(Color.WHITE);// 绘制区域外边框
		chartTheme.setLabelLinkPaint(new Color(8, 55, 114));// 链接标签颜色
		chartTheme.setLabelLinkStyle(PieLabelLinkStyle.CUBIC_CURVE);
		chartTheme.setAxisOffset(new RectangleInsets(5, 12, 5, 12));
		chartTheme.setDomainGridlinePaint(new Color(192, 208, 224));// X坐标轴垂直网格颜色
		chartTheme.setRangeGridlinePaint(new Color(192, 192, 192));// Y坐标轴水平网格颜色
		chartTheme.setBaselinePaint(Color.WHITE);
		chartTheme.setCrosshairPaint(Color.BLUE);// 不确定含义
		chartTheme.setAxisLabelPaint(new Color(51, 51, 51));// 坐标轴标题文字颜色
		chartTheme.setTickLabelPaint(new Color(67, 67, 72));// 刻度数字
		chartTheme.setBarPainter(new StandardBarPainter());// 设置柱状图渲染
		chartTheme.setXYBarPainter(new StandardXYBarPainter());// XYBar 渲染
		chartTheme.setItemLabelPaint(Color.black);
		chartTheme.setThermometerPaint(Color.white);// 温度计
		ChartFactory.setChartTheme(chartTheme);
	}
	
		/**
	     * 获取饼状图
	     * @param title  标题
	     * @param data  饼状体数据集
	     * @param is3D   是否3D生成
	     */
//		public static JFreeChart getPieChart(String title,PieChart data,boolean is3D){
//			DefaultPieDataset dataset = new DefaultPieDataset();
//			for(PieChartSeriesData data_ : data.getOpinionData()){
//				String name = data_.getName();
//				Integer value = data_.getValue();
//				dataset.setValue(name, value);
//				}
//			//创建 Jfreechart对象  
//			JFreeChart jfreechart = null;
//			if(is3D){
//				jfreechart = ChartFactory.createPieChart3D(title, dataset, true, true, false);
//			}else{
//				jfreechart = ChartFactory.createPieChart(title, dataset, true, true, false);
//			}
//			//设置文本抗锯齿
//			jfreechart.setTextAntiAlias(false);
//			PiePlot piePlot = (PiePlot) jfreechart.getPlot();
//			piePlot.setNoDataMessage(NO_DATA_MSG);
//			piePlot.setInsets(new RectangleInsets(0, 0, 0, 0));
//			piePlot.setCircular(true);// 圆形
//			
//			piePlot.setLabelGap(0.01);
//			piePlot.setInteriorGap(0.05D);
//			piePlot.setLegendItemShape(new Rectangle(10, 10));// 图例形状
//			//去掉背景色
//			piePlot.setLabelBackgroundPaint(null);
//			//去掉阴影
//			piePlot.setLabelShadowPaint(null);
//			//去掉边框
//			piePlot.setLabelOutlinePaint(null);
//			piePlot.setShadowPaint(null);
//			//设置Pie的边框是否可见  
//			piePlot.setSectionOutlinesVisible(false);
//			// 指定图片的透明度(0.0-1.0)  
//			piePlot.setForegroundAlpha(1.0f);
//			//设置边框的颜色  
//			piePlot.setBaseSectionOutlinePaint(Color.green); 
//			//设置边框的粗细,new BasicStroke(2.0f)  
//			piePlot.setBaseSectionOutlineStroke(new BasicStroke(1)); 
//			//设置空值,0值,负值是否显示出来,如果显示的话就是false  
//			piePlot.setIgnoreNullValues(true);
//			piePlot.setIgnoreZeroValues(true); 
//			//设置上面的样式,0表示KEY,1表示VALUE,2表示百分之几,DecimalFormat用来显示百分比的格式  
//			piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}:{2}",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
//			//设置下面方框的样式,0表示KEY,1表示VALUE,2表示百分之几,DecimalFormat用来显示百分比的格式  
//			piePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1},{2})",NumberFormat.getNumberInstance(),new DecimalFormat("0.00%")));
//			//爆炸模式,使Pie的一块分离出去,不支持3D  piePlot.setExplodePercent("正面", 0.10); 
//			return jfreechart;
//		}
	/**
     * 获取折线图
     * @param title  标题
     * @param data  折线图数据集
     * @param is3D   是否3D生成
     */
	public static JFreeChart getLineChart(String title,LineChart data,boolean is3D,String xName,String yName){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//图例数据
		List<String> navdata = data.getNavdata();
		//X轴数据
		List<String> xAxisdata = data.getxAxisdata();
		
		for(int i = 0;i < navdata.size();i++){
		String navName = navdata.get(i);
		
		List<Double> dataList = data.getOpinionData().get(i).getData();
		for(int j = 0; j< xAxisdata.size();j++){
			String date = xAxisdata.get(j);
			double count=0;
			try {
				count = dataList.get(j);
			} catch (Exception e) {
				 
			} 
			dataset.setValue(count, navName, String.valueOf(date));
			}
		}
			//创建 Jfreechart对象  
			JFreeChart jfreechart = null;
			if(is3D){
				jfreechart = ChartFactory.createLineChart3D(title, 
			    xName, //横坐标名称
			    yName, //纵坐标名称
				dataset,//数据
				PlotOrientation.VERTICAL, //垂直视图
				true, //include legend
				true, //tooltips
				false
				);
			}
			else{
				jfreechart = ChartFactory.createLineChart(title, 
				xName, //横坐标名称
				yName, //纵坐标名称
				dataset,//数据
				PlotOrientation.VERTICAL, //垂直视图
				true, //include legend
				true, //tooltips
				false
				);
			}

		//设置文本抗锯齿
		jfreechart.setTextAntiAlias(false);
		CategoryPlot plot = jfreechart.getCategoryPlot();
		plot.setNoDataMessage(NO_DATA_MSG);
		plot.setRangeGridlinesVisible(false); //是否显示格子线
		plot.setBackgroundAlpha(0.3f); //设置背景透明度
		// 设置X轴
		CategoryAxis domainAxis = plot.getDomainAxis();   
        domainAxis.setLowerMargin(0.01);// 左边距 边框距离
        domainAxis.setUpperMargin(0.06);// 右边距 边框距离,防止最后边的一个数据靠近了坐标轴。
        domainAxis.setMaximumCategoryLabelLines(10);
        //domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
        // 设置Y轴
		NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis(); 
	    rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());//Y轴显示整数
	    rangeAxis.setAutoRangeMinimumSize(1);   //最小跨度
	    rangeAxis.setUpperMargin(0.18);//上边距,防止最大的一个数据靠近了坐标轴。   
	    rangeAxis.setLowerBound(0);   //最小值显示0
	    rangeAxis.setAutoRange(true);   //不自动分配Y轴数据
	    rangeAxis.setTickMarkStroke(new BasicStroke(1.6f));     // 设置坐标标记大小
	    rangeAxis.setTickMarkPaint(Color.BLACK);     // 设置坐标标记颜色
 
	// 数据渲染部分 主要是对折线做操作
		LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//		renderer.setBaseItemLabelsVisible(false);
		renderer.setBaseShapesFilled(true);
//		renderer.setBaseItemLabelsVisible(true);
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		plot.setRenderer(renderer);
		return jfreechart;
	}
	/**
	 * 获取柱状图
     * @param title  标题
     * @param data   柱状图数据集
     * @param is3D   是否3D生成
     */
	public static JFreeChart getBarChart(String title,BarChart data,boolean is3D,String xName,String yName){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//图例
		List<String> navdata = data.getNavdata();
		//X轴数据
		List<String> xAxisdata = data.getxAxisdata();
		for(int i = 0;i < navdata.size();i++){
			String navName = navdata.get(i);
			List<Double> dataList = data.getOpinionData().get(i).getData();
			for(int j = 0; j< xAxisdata.size();j++){
				String date = xAxisdata.get(j);
				Double count = dataList.get(j);
				dataset.setValue(count, navName, String.valueOf(date));
			}
		}
		//创建 Jfreechart对象  
		JFreeChart jfreechart = null;
			if(is3D){
				jfreechart = ChartFactory.createBarChart3D(title,
					xName, //横坐标名称
					yName, //纵坐标名称
					dataset,//数据
					PlotOrientation.VERTICAL, //垂直视图
					true, //include legend
					true, //tooltips
					false);
			}
			else{
				jfreechart = ChartFactory.createBarChart(title,
					xName, //横坐标名称
					yName, //纵坐标名称
					dataset,//数据
					PlotOrientation.VERTICAL, //垂直视图
					true, //include legend
					true, //tooltips
					false
						);
			}
			//设置文本抗锯齿
			jfreechart.setTextAntiAlias(false);
			CategoryPlot plot = jfreechart.getCategoryPlot();
			plot.setNoDataMessage(NO_DATA_MSG);
			plot.setInsets(new RectangleInsets(10, 10, 5, 10));
			plot.setRangeGridlinesVisible(false); //是否显示格子线
			plot.setBackgroundAlpha(0.3f); //设置背景透明度
			//对y轴操作
			NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
			rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			//上边距
			rangeAxis.setUpperMargin(0.10);
			//字体倾斜度
			//rangeAxis.setLabelAngle(Math.PI / 2.0);
			BarRenderer renderer = (BarRenderer) plot.getRenderer();
			renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
			renderer.setMaximumBarWidth(0.075);// 设置柱子最大宽度
			renderer.setBaseItemLabelsVisible(true);
			//显示数字和数字显示的位置
			renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER));
			renderer.setItemLabelAnchorOffset(20D); 
			return jfreechart;
		}
	
	/**
     * 获取图片流
     * @param jfreechart 
     * @param weight
     * @param height
     * @return
     */
	public static InputStream getChartStream(JFreeChart jfreechart,int weight, int height){
		InputStream input = null;
		try {
			BufferedImage bufferedImage = jfreechart.createBufferedImage(weight, height);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", os); 
			input = new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("------------------------------------" + e.getMessage());
		}
		return input;
	}

}

