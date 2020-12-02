package com.nfit.yaoliusan.myblog.web;

import com.google.gson.Gson;
import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.dao.PostDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

@WebServlet("/post/add")
@MultipartConfig
public class PostAddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String author = req.getParameter("author");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Part cover = req.getPart("cover");
        System.out.println(cover);
        try {
            //文件的保存名
            String fileName = System.currentTimeMillis() + "-" + cover.getSubmittedFileName();
            System.out.println(fileName);
            //保存的地址
            cover.write(getServletContext().getRealPath("/img/") + fileName);

            Post post = new PostDAO().addPost(new Post(title, content, author, fileName));
            post=new PostDAO().getPost(post.getId());
            //把id传给客户端
            resp.getWriter().write(new Gson().toJson(post));

        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().print("-1");
        }

    }
}
