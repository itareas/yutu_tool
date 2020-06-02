package com.yutu.utils.file;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Author: zhaobc
 * @Date: 2020-02-18 18:19
 * @Description:文件压缩操作类
 */
public class FileZipUtils {
    private Logger logger = Logger.getLogger(FileZipUtils.class);
    /**
     * 压缩文件
     *
     * @param sourceFilePath 源文件路径
     * @param zipFilePath    压缩后文件存储路径
     * @param zipFilename    压缩文件名
     */
    public static boolean compressToZip(String sourceFilePath, String zipFilePath, String zipFilename) {
        Boolean bool=true;
        File sourceFile = new File(sourceFilePath);
        File zipPath = new File(zipFilePath);
        if (!zipPath.exists()) {
            zipPath.mkdirs();
        }
        File zipFile = new File(zipPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            bool= writeZip(sourceFile, "", zos);
            //文件压缩完成后，删除被压缩文件
        } catch (Exception e) {
            bool=false;
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
        return bool;
    }

    /**
     * 遍历所有文件，压缩
     *
     * @param file       源文件目录
     * @param parentPath 压缩文件目录
     * @param zos        文件流
     */
    public static boolean writeZip(File file, String parentPath, ZipOutputStream zos) {
        Boolean bool=true;
        if (file.isDirectory()) {
            //目录
            parentPath += file.getName() + File.separator;
            File[] files = file.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            //文件
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
                //指定zip文件夹
                ZipEntry zipEntry = new ZipEntry(parentPath + file.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                bool = false;
                e.printStackTrace();
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
        return bool;
    }
    /**
     * 删除文件夹
     *
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //删除空文件夹
        return dir.delete();
    }
}
