package com.nfit.yaoliusan.myblog.web;


import com.google.gson.Gson;
import com.nfit.yaoliusan.myblog.bean.Comment;
import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.dao.CommentDAO;
import com.nfit.yaoliusan.myblog.dao.PostDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/post")
public class PostServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        try {
            String vvv="efslsef"+"+'ef'+flsejfsdl"+'s';

            Post post = new PostDAO().getPost(Integer.parseInt(id));    //获取博客内容
           resp.getWriter().print(new Gson().toJson(post));
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", e.getLocalizedMessage());
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }
    }


}
