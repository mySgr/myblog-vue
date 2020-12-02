package com.nfit.yaoliusan.myblog.web;

import com.nfit.yaoliusan.myblog.dao.CommentDAO;
import com.nfit.yaoliusan.myblog.vo.ResultVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/comment/number")
public class CommentNumberServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String postId = req.getParameter("postId");

        try {
            int number = new CommentDAO().getCommentCount(Integer.parseInt(postId));
            resp.getWriter().print(ResultVO.success(number).toJSON());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
