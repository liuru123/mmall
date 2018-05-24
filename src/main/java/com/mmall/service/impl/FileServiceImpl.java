package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service("iFileService")
public class FileServiceImpl implements IFileService {


    private  Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    public String upload(MultipartFile file,String path){
        //获取文件原始名称
        String filename = file.getOriginalFilename();

        //获取扩展名abc.jpg--->jpg
        String fileExtensionName = filename.substring(filename.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString()+"."+fileExtensionName;
        logger.info("开始上传文件，上传的文件名是：{}，上传的路径是：{}，新文件名是：{}",filename,path,uploadFileName);

        //设置上传文件保存的地址
        File fileDir = new File(path);

        //如果上传文件的保存地址不存在则首先创建一个目录
        if(!fileDir.exists()){
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }

        //这个文件应该就是需要上传的文件
        File targetFile = new File(path,uploadFileName);

        try {
            file.transferTo(targetFile);
            //上传文件成功
            //将targetFile 文件上传至ftp服务器
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            //已经上传到ftp服务器上
            //上传完成后将upload文件下的文件删除
            targetFile.delete();

        } catch (IOException e) {
            logger.error("上传文件异常",e);
            return null;
        }

        return targetFile.getName();
    }
}
