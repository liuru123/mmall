package com.mmall.util;


import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 用来将文件上传至ftp服务器
 */
public class FTPUtil {

    private static final Logger logger = LoggerFactory.getLogger(FTPUtil.class);

    private static String ftpIp = PropertiesUtil.getProperties("ftp.server.ip");
    private static String ftpUser = PropertiesUtil.getProperties("ftp.user");
    private static String ftpPass = PropertiesUtil.getProperties("ftp.pass");

    private String ip;
    private int port;
    private String user;
    private String pass;
    private FTPClient ftpClient;

    public FTPUtil(String ip,int port,String user,String password){
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.pass = password;
    }

    public static boolean uploadFile(List<File>fileList) throws IOException {
        FTPUtil ftpUtil = new FTPUtil(ftpIp,21,ftpUser,ftpPass);
        logger.info("开始连接ftp服务器");
        boolean result  = ftpUtil.uploadFile("img",fileList);
        logger.info("开始连接ftp服务器，结束上传{}");
        return result;
    }

    private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
        boolean uploaded = true;
        FileInputStream fis = null;
        //连接ftp服务器
        if(connectFtpServer(this.ip,this.port,this.user,this.pass)){
            try {
                //改变文件目录
                ftpClient.changeWorkingDirectory(remotePath);
                //设置缓冲区
                ftpClient.setBufferSize(1024);
                //设置编码格式
                ftpClient.setControlEncoding("UTF-8");
                //设置文件类型
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                //开启本地的被动模式
                ftpClient.enterLocalPassiveMode();

                for(File fileItem:fileList){
                    fis = new FileInputStream(fileItem);
                    ftpClient.storeFile(fileItem.getName(),fis);
                }
            } catch (IOException e) {
                uploaded = false;
                logger.error("上传文件异常",e);
            }finally {
                fis.close();
                ftpClient.disconnect();
            }
        }
        return uploaded;
    }

    //连接ftp服务器
    private boolean connectFtpServer(String ip,int port, String user,String pass){
        boolean isSuccess = false;
       ftpClient = new FTPClient();
        try {
            ftpClient.connect(ip);
            isSuccess = ftpClient.login(user,pass);
        } catch (IOException e) {
            logger.error("连接ftp服务器异常",e);
        }
        return isSuccess;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public FTPClient getFtpClient() {
        return ftpClient;
    }

    public void setFtpClient(FTPClient ftpClient) {
        this.ftpClient = ftpClient;
    }
}
