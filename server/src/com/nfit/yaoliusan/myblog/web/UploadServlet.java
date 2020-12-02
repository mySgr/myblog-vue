package com.nfit.yaoliusan.myblog.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/upload.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("img");

        try {
            if (part.getSize() < 1024 * 1024 * 2 &&
                    part.getContentType().equals("image/jpeg") ||
                    part.getContentType().equals("image/png")) {  //上传文件小于2MB则执行 并文件类型对应
                //保存时名字, 上传的时间加上传文件时的文件名做为保存名
                String savedName =
                        new SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date())
                                + "-"
                                + part.getSubmittedFileName();
                //保存的地址：
                //1. getServletContext().getRealPath("/") 获取文件在电脑中的绝对路径， “/”指代项目根目录
                //2. new File(savedDir).mkdir() 当文件路径不存在时创建文件路径
                String savedDir = getServletContext().getRealPath("/img/");
                new File(savedDir).mkdir();

                //使用 part.write('路径') 来保存图片到服务器的硬盘
                part.write(savedDir + savedName);

                //重定向到upload_success.jsp页面,path把文件名传过去
               resp.sendRedirect(req.getContextPath() + "/jsp/upload_success.jsp?path=" + savedName);


               System.out.println(savedDir);
                System.out.println(part.getSubmittedFileName()); //获取上传文件名
                System.out.println(part.getSize());      //获取上传文件的大小
                System.out.println(part.getContentType());   //获取文件MIME类型
            } else {
                req.setAttribute("error", part.getSubmittedFileName() + " > 2MB or not supporter file type");
                req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e.getLocalizedMessage());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }

    }
}
