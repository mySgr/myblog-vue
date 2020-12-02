package com.nfit.yaoliusan.myblog.dao;

import com.nfit.yaoliusan.myblog.bean.Comment;
import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.utils.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

public class CommentDAO {
    /**
     * 获取所有评论
     *
     * @return comments
     * @throws Exception
     */
    public List<Comment> getCommenstAll() throws Exception {
        Connection conn = DBHelper.getConnection();  //获取连接

        QueryRunner runner = new QueryRunner();   //获取操作
        //sql语句
        String sql = "select　id，post_id,content,author,created from commition";
        List<Comment> comments = runner.query(conn, sql, new BeanListHandler<>(Comment.class));
        return comments;
    }

    /**
     * 博客对应的所有评论
     *
     * @param postID
     * @return 评论列表
     * @throws Exception
     */
    public List<Comment> getCommentsPostID(int postID) throws Exception {

        Connection conn = DBHelper.getConnection();  //获取连接
        QueryRunner runner = new QueryRunner();   //获取操作
        //sql语句
        String sql = "select　id,post_id,content,author,created from commition where post_id=? ";
        try {
            List<Comment> comments = runner.query(conn, sql, new BeanListHandler<>(Comment.class), postID);
            return comments;
        } finally {
            DbUtils.closeQuietly(conn);
        }

    }

    /**
     * 返回评论数
     * @param postId
     * @return
     * @throws Exception
     */
    public int getCommentCount(int postId) throws Exception {
        Connection conn = DBHelper.getConnection();
        String sql = "select count(*) as number from commition where post_id = ?";
        QueryRunner queryRunner = new QueryRunner();
        Object number = queryRunner.query(conn, sql, new ScalarHandler<Object>("number"), postId);
        return (int) number;

    }

    /**
     * 为博客添加评论
     *
     * @param comment
     * @return
     * @throws Exception
     */
    public Comment addComment(Comment comment) throws Exception {

        Connection conn = DBHelper.getConnection();
        QueryRunner runner = new QueryRunner();
        //sql
        String sql = " insert into commition(post_id,content,author) values(?, ?, ?)";
        Object[] params = {
                comment.getPost().getId(),
                comment.getContent(),
                comment.getAuthor()
        };
        try {
            BigDecimal id = runner.insert(conn, sql, new ScalarHandler<>(), params);
            comment.setId(id.intValue());
            return comment;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int delComment(int id) throws Exception {
        Connection conn = DBHelper.getConnection();
        try {
            QueryRunner runner = new QueryRunner();
            String sql = "delete  commition where id=?";
            return runner.update(conn, sql, id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
