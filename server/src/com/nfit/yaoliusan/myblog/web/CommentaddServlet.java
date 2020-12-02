package com.nfit.yaoliusan.myblog.web;


import com.nfit.yaoliusan.myblog.bean.Comment;
import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.dao.CommentDAO;
import com.nfit.yaoliusan.myblog.dao.PostDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment/add")
@MultipartConfig
public class CommentaddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求参数
        String postID = req.getParameter("postId");
        String author = req.getParameter("author");
        String content = req.getParameter("content");

        CommentDAO commentDAO = new CommentDAO();
        try {
            Post post = new PostDAO().getPost(Integer.parseInt(postID));
            Comment comment = commentDAO.addComment(new Comment(post,content,author));

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error",e.getLocalizedMessage());
        }

    }
}
