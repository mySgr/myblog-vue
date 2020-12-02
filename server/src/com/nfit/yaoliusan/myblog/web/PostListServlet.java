package com.nfit.yaoliusan.myblog.web;


import com.google.gson.Gson;
import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.dao.PostDAO;
import com.nfit.yaoliusan.myblog.vo.ResultVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/post/list")
public class PostListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Post> posts = new PostDAO().getPosts();
            resp.getWriter().print(ResultVO.success(posts).toJSON());
            System.out.println(ResultVO.success(posts).toJSON());
        } catch (Exception e) {
            resp.getWriter().print(ResultVO.err(-1, e.getLocalizedMessage()));
        }

    }
}
