package com.nfit.yaoliusan.myblog.dao;

import com.nfit.yaoliusan.myblog.bean.Post;
import com.nfit.yaoliusan.myblog.utils.DBHelper;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class PostDAO {

    /**
     * 获取所有文章信息
     *
     * @return 文章的集合
     * @throws Exception
     */
    public List<Post> getPosts() throws Exception {

        Connection conn = DBHelper.getConnection();
        QueryRunner queryRunner = new QueryRunner();

        //sql语句
        String sql = "select id,title,content,author,cover,created from post";

        try {
            List<Post> posts = (List<Post>) queryRunner.query(conn, sql, new BeanListHandler(Post.class));
            return posts;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 根据文章编号找出对应的文章
     *
     * @param id
     * @return Post
     * @throws Exception
     */
    public Post getPost(int id) throws Exception {
        Connection conn = DBHelper.getConnection();

        String sql = "select id,title,content,author,cover,likes,created from post where id=?";
        Post post = new QueryRunner().query(conn, sql, new BeanHandler<>(Post.class), id);
        return post;
    }

    /**
     * 实现点赞
     *
     * @param id
     * @return
     * @throws Exception
     */
    public int like(int id) throws Exception {
        Connection conn = DBHelper.getConnection();

        try {
            String sql = "update post set likes = likes + 1 where id = ?";
            return new QueryRunner().update(conn, sql, id);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    /**
     * 添加博客方法
     *
     * @param post
     * @return 返回添加的对象
     * @throws Exception
     */
    public Post addPost(Post post) throws Exception {

        //获取连接对象
        Connection conn = DBHelper.getConnection();

        //创建SQL执行工具,使用可插拔的策略执行SQL查询并处理结果集
        QueryRunner qRunner = new QueryRunner();

        //sql语句
        String sql = "insert into post(title,content,author,cover)" + " values(?, ?, ?, ?)";
        // SQL所需的参数值
        Object[] params = {
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getCover()
        };

        try {
            //执行SQL插入
            BigDecimal id = qRunner.insert(conn, sql, new ScalarHandler<>(), params); //返回主键
            post.setId(id.intValue());
            return post;
        } finally {
            //关闭数据库连接
            DbUtils.closeQuietly(conn);
        }

//        int n = qRunner.update(conn, sql, objects);
//
//        if (n > 0){
//            //sql查询语句, @@identity得到上一次插入记录时自动产生的ID
//            String sqlPost = "select * from post where id = (select @@identity)";
//             post = qRunner.query(conn, sqlPost, new BeanHandler<>(Post.class));
//
//        }

    }

    /**
     * 删除
     * @param id
     * @return
     * @throws Exception
     */
    public int delPost(int id) throws Exception {
        //获取连接
        Connection conn = DBHelper.getConnection();
        //sql语句
        try {
            String sql = "delete from post where id=?";
            return new QueryRunner().update(conn, sql, id);
        }finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
