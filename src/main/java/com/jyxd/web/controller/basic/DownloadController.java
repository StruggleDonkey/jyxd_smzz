package com.jyxd.web.controller.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Controller
@RequestMapping(value = "/download")
public class DownloadController {
    private static Logger logger= LoggerFactory.getLogger(DownloadController.class);

    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/plugin")
    public String downloadFile(HttpServletRequest request,
                               HttpServletResponse response) throws UnsupportedEncodingException {
        String name=request.getParameter("filename");
        // 获取指定目录下的第t个文件
        File scFileDir = new File("C:\\plugin");
        File TrxFiles[] = scFileDir.listFiles();
        for(int t=0;t<TrxFiles.length;t++) {
            if (TrxFiles[t].getName().equals(name)) {
                String fileName = TrxFiles[t].getName(); //下载的文件名
                // 如果文件名不为空，则进行下载
                if (fileName != null) {
                    //设置文件路径
                    String realPath = "C://plugin/";
                    File file = new File(realPath, fileName);
                    // 如果文件名存在，则进行下载
                    if (file.exists()) {
                        // 配置文件下载
                        response.setHeader("content-type", "application/octet-stream");
                        response.setContentType("application/octet-stream");
                        // 下载文件能正常显示中文
                        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                        // 实现文件下载
                        byte[] buffer = new byte[1024];
                        FileInputStream fis = null;
                        BufferedInputStream bis = null;
                        try {
                            fis = new FileInputStream(file);
                            bis = new BufferedInputStream(fis);
                            OutputStream os = response.getOutputStream();
                            int i = bis.read(buffer);
                            while (i != -1) {
                                os.write(buffer, 0, i);
                                i = bis.read(buffer);
                            }

                            System.out.println("下载成功!");
                        }
                        catch (Exception e) {
                            System.out.println("下载失败!");
                        }
                        finally {
                            if (bis != null) {
                                try {
                                    bis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
