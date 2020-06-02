package com.yutu.utils.file.poi;

 import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 import freemarker.template.Configuration;
 import freemarker.template.Template;

import sun.misc.BASE64Encoder;

 
 public class WordUtils {
     //配置信息,代码本身写的还是很可读的,就不过多注解了  
     private static Configuration configuration = null;
     //这里注意的是利用WordUtils的类加载器动态获得模板文件的位置  
     private static final String templateFolder = WordUtils.class.getClassLoader().getResource("../../").getPath() + "WEB-INF/templetes/";  
     //private static final String templateFolder = "D:/我的项目/lm/lm/web/src/main/webapp/WEB-INF/templates";  
     static {  
         configuration = new Configuration();  
         configuration.setDefaultEncoding("utf-8");  
         try {  
             configuration.setDirectoryForTemplateLoading(new File(templateFolder));  
         } catch (IOException e) {  
             e.printStackTrace();  
         }  
    }  
   
     private WordUtils() {  
         throw new AssertionError();  
     }  
   
     public static String exportWordReport(HttpServletRequest request, HttpServletResponse response, 
    		 	Map map,String title,String ftlFile) throws IOException {  
         Template freemarkerTemplate = configuration.getTemplate(ftlFile);  
         File file = null;  
         String fileName = "";
         InputStream fin = null; 
         OutputStream os = null;
         ServletOutputStream out = null;  
         try {  
             // 调用工具类的createDoc方法生成Word文档  
             file = createDoc(map,freemarkerTemplate);  
             //得到文件以后先将文件保存在一个地方
             String path = WordUtils.class.getClassLoader().getResource("../../").getPath() + "resources/uploadWord";
             byte[] bs = new byte[1024];
             int len;
             File tempFile = new File(path);
             SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
             Date date = new Date();
             String reportdate = sdf.format(date);
             fileName = title + reportdate + ".doc";
             os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
             fin = new FileInputStream(file);  
             while((len = fin.read(bs)) != -1){
            	 os.write(bs, 0, len);
             }
             
//         	response.setContentType("application/octet-stream");
//         	response.setHeader("Content-Disposition", "attachment;filename="
//					+ new String(fileName.getBytes("GBK"), "ISO-8859-1"));
//			response.setCharacterEncoding("utf-8");//这句很重要，不加word里都是乱码
//             response.setCharacterEncoding("utf-8");  
//             response.setContentType("application/msword");  
//             // 设置浏览器以下载的方式处理该文件名  
//             String fileName = title+new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date()) + ".doc";  
//             response.setHeader("Content-Disposition", "attachment;filename="  
//                     .concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));  
//   
//             out = response.getOutputStream();  
//             byte[] buffer = new byte[1024];  // 缓冲区  
//             int bytesToRead = -1;  
//             // 通过循环将读入的Word文件的内容输出到浏览器中  
//             while((bytesToRead = fin.read(buffer)) != -1) {  
//                 out.write(buffer, 0, bytesToRead);  
//             }  
         }
         catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
        finally {  
             if(fin != null) fin.close();  
             if(out != null) out.close();  
             if(os != null) os.close();
             if(file != null) file.delete(); // 删除临时文件  
         }  
         return fileName;
     }  
   
     private static File createDoc(Map<?, ?> dataMap, Template template) {  
         String name =  "report.doc";  
         File f = new File(name);  
         Template t = template;  
        try {  
             // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开  
             Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");  
             t.process(dataMap, w);  
             w.close();  
         } catch (Exception ex) {  
             ex.printStackTrace();  
             throw new RuntimeException(ex);  
         }  
         return f;  
     }  
     
     
     /**
      * 得到图片
      * @return
      */
     public static String getImageInput(InputStream in) {
         byte[] data = null;
         try {
             data = new byte[in.available()];
             in.read(data);
             in.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
         BASE64Encoder encoder = new BASE64Encoder();//这里会报错
         return encoder.encode(data);
     }
     
     
     /**
      * 得到图片
      * @return
      */
     public static String getImageStr(String imgUrl) {
//         String imgFile = "D:/ydyl/pic/pic2.jpg";//需要在D盘下指定的目录下放一张图片
         InputStream in = null;
         byte[] data = null;
         try {
             in = new FileInputStream(imgUrl);
             data = new byte[in.available()];
             in.read(data);
             in.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
         BASE64Encoder encoder = new BASE64Encoder();//这里会报错
         return encoder.encode(data);
     }
 	
 	//获得图片的base64码
 	  @SuppressWarnings("deprecation")
 	  public static String getImageBase(HttpServletRequest request,String src) {
 	      if(src==null||src==""){
 	          return "";
 	      }
 	      File file = new File(request.getRealPath("/")+src.replace(request.getContextPath(), ""));
 	      if(!file.exists()) {
 	          return "";
 	     }
 	     InputStream in = null;
 	     byte[] data = null;  
 	     try {
 	         in = new FileInputStream(file);
 	     } catch (FileNotFoundException e1) {
 	         e1.printStackTrace();
 	     }
 	     try {  
 	         data = new byte[in.available()];  
 	         in.read(data);  
 	         in.close();  
 	     } catch (IOException e) {  
 	       e.printStackTrace();  
 	     } 
 	     BASE64Encoder encoder = new BASE64Encoder();
 	     return encoder.encode(data);
 	 }
 }
