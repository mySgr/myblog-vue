<template>
    <div>
        <el-container>

            <el-header>
                <el-menu class="el-menu-demo" mode="horizontal">
                    <el-menu-item index="1">首页</el-menu-item>
                    <el-submenu index="2">
                        <template slot="title">分类</template>
                        <el-menu-item index="2-1">技术</el-menu-item>
                        <el-menu-item index="2-2">文学</el-menu-item>
                        <el-menu-item index="2-3">科学</el-menu-item>

                    </el-submenu>
                    <el-menu-item index="3" disabled>消息中心</el-menu-item>
                    <el-menu-item index="4"><a href="https://www.ele.me" target="_blank">关于我们</a></el-menu-item>
                    <div class="nav-head">
                        <el-button @click="dialogVisible = true">添加博客</el-button>
                        <el-button>用户管理</el-button>
                    </div>
                </el-menu>

            </el-header>
            <el-container>
                <el-main>
                    <div class="posts">
                        <div class="post" v-for="post in posts" :key="post.id">
                            <header>
                                <h5>{{post.title}}</h5>
                            </header>
                            <div>
                                <el-row :gutter="20">
                                    <el-col :span="16">
                                        <div class="grid-content bg-purple">{{post.content}}</div>
                                    </el-col>
                                    <el-col :span="8">
                                        <div class="grid-content bg-purple"><img src="../assets/logo.png" width="100">
                                        </div>
                                    </el-col>
                                </el-row>
                            </div>
                            <footer>
                                {{post.created}}
                            </footer>
                        </div>
                    </div>

                </el-main>
                <!--   侧边栏-->
                <el-aside width="200px">
                    <div>
                        热门标签
                    </div>
                    <div>
                        猜你喜欢
                    </div>
                </el-aside>
            </el-container>

        </el-container>

        <el-dialog class="" title="添加博客" :visible.sync="dialogVisible">
            <div class="dialog">
                <el-form :model="form">
                    <el-form-item label="author" :label-width="formLabelWidth">
                        <el-input v-model="form.author" autocomplete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="标题" :label-width="formLabelWidth">
                        <el-input v-model="form.title" placeholder="请输入标题"></el-input>
                    </el-form-item>
                    <el-form-item label="内容" :label-width="formLabelWidth">
                        <el-input v-model="form.content" placeholder="请输入内容"></el-input>
                    </el-form-item>
                    <div class="">
                        <input type="file" ref="eee" @change="filePick">
                    </div>
                </el-form>
                <div>
                    <img @click="doPick" class="preview-img" :src="previewSrc">
                    <p> 请点击选择图片 </p>
                </div>
            </div>

            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="addPost">确 定</el-button>
            </div>
        </el-dialog>

    </div>


</template>

<script>
    import axios from 'axios';

    export default {
        data() {
            return {
                dialogVisible: false,
                posts: [],
                form: {
                    author: '',
                    title: '',
                    contnet: '',
                    cover: ""
                },
                formLabelWidth: '70px',
                previewSrc: require("../assets/logo.png")
            }

        },
        methods: {
            initPosts() {
                axios({
                    url: '/api/post/list',
                }).then(resp => {
                    this.posts = resp.data.data;
                    console.log(this.posts)
                })
            },
            // 添加博客
            addPost() {
                var formData = new FormData();
                formData.append("author", this.form.author);
                formData.append("title", this.form.title);
                formData.append("content", this.form.content);
                formData.append("cover", this.form.cover);
                axios({
                    method: "post",
                    url: "/api/post/add",
                    data: formData
                }).then(resp => {
                    console.log(11)
                    this.initPosts();
                    this.dialogVisible = false

                })
            },
            doPick() {
                this.$refs.eee.click();
            },
            //图片预览
            filePick() {
                this.previewSrc = URL.createObjectURL(this.$refs.eee.files[0]);
                this.form.cover = this.$refs.eee.files[0]
            }
        },
        created() {
            this.initPosts();
        }
    }
</script>

<style scoped>
    .nav-head {
        float: right;
    }

    .dialog {
        display: flex;
        justify-content: space-between;
    }

    .file {
        display: none;
    }

    .post {
        padding: 10px;
        border-bottom: 1px solid #9999;
    }
</style>