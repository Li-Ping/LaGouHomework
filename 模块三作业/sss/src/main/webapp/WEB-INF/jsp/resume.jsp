<%@ page language="java" isELIgnored="false" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>resume</title>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <style>

        .main {
            padding: 20px;
            display: none;
        }

        .btn-area {
            margin: 20px 0;
        }
    </style>
</head>
<body>
<div id="resume">
    <div class="main" ref="mainPanel" v-loading="loading">
        <div class="btn-area" ref="btnArea">
            <el-button
                    type="primary"
                    size="medium"
                    icon="el-icon-plus"
                    @click="addResume"
            >
                增加
            </el-button>
        </div>
        <el-table
                :data="tableData"
                border
                style="width: 100%">
            <el-table-column
                    prop="id"
                    label="序号"
                    width="80">
            </el-table-column>
            <el-table-column
                    prop="name"
                    label="姓名"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="地址">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="电话">
            </el-table-column>
            <el-table-column label="操作">
                <template slot-scope="scope">
                    <el-button
                            type="success"
                            size="small"
                            @click="updateResume(scope.row)"
                    >
                        修改
                    </el-button>
                    <el-button
                            type="danger"
                            size="small"
                            @click="deleteResume(scope.row)"
                    >
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-dialog
                :title="form.id ? '修改信息' : '新增信息'"
                :visible.sync="dialogVisible"
                width="30%"
                :before-close="handleClose"
        >
            <el-form :model="form">
                <el-form-item label="姓名" :label-width="formLabelWidth">
                    <el-input v-model="form.name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="地址" :label-width="formLabelWidth">
                    <el-input v-model="form.address" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="电话" :label-width="formLabelWidth">
                    <el-input v-model="form.phone" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirmSave">确 定</el-button>
            </span>
        </el-dialog>
        <el-dialog
                title="提示"
                :visible.sync="deleteDialogVisible"
                width="30%"
                :before-close="handleDeleteClose"
        >
            <span>确定要删除此项记录吗？</span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="deleteDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirmDelete">确 定</el-button>
            </span>
        </el-dialog>
    </div>

</div>
<script src="https://cdn.bootcss.com/jquery/3.4.1/jquery.js"></script>
<script src="https://unpkg.com/vue/dist/vue.js"></script>
<!-- import JavaScript -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<script>
    new Vue({
        el: '#resume',
        data: function() {
            return {
                tableData: [],
                formLabelWidth: '100',
                dialogVisible: false,
                deleteDialogVisible: false,
                loading: true,
                form: {
                    id: '',
                    name: '',
                    address: '',
                    phone: ''
                }
            }
        },
        methods: {
            fetchData() {
                this.loading = true;
                $.ajax({
                    url: '/resume/query',
                    method: 'get',
                    data: {},
                    complete: (response) => {
                        console.log(response);
                        this.tableData = response.responseJSON;
                        this.loading = false;
                    }
                });
            },
            deleteResume(row) {
                this.deleteDialogVisible = true;
                this.form.id = row.id;
            },
            addResume() {
                this.form.id = null;
                this.form.name = '';
                this.form.address = '';
                this.form.phone = '';
                this.dialogVisible = true;
            },
            updateResume(row) {
                this.form.id = row.id;
                this.form.name = row.name;
                this.form.address = row.address;
                this.form.phone = row.phone;
                this.dialogVisible = true;
            },
            handleClose() {
                this.dialogVisible = false;
            },
            handleDeleteClose() {
                this.deleteDialogVisible = false;
            },
            confirmSave() {
                $.ajax({
                    url: '/resume/update',
                    method: 'post',
                    data: this.form,
                    dataType: 'json',
                    complete: (response) => {
                        console.log(response);
                        if (response.status === 200) {
                            this.fetchData();
                            this.$message({
                                message: '操作成功！',
                                type: 'success'
                            });
                            this.dialogVisible = false;
                        }
                    }
                })
            },
            confirmDelete() {
                $.ajax({
                    url: '/resume/delete',
                    method: 'get',
                    data: {
                        id: this.form.id
                    },
                    complete: (response) => {
                        console.log(response);
                        if (response.status === 200) {
                            this.fetchData();
                            this.$message({
                                message: '操作成功！',
                                type: 'success'
                            });
                            this.deleteDialogVisible = false;
                        }
                    }
                });
            }
        },
        created() {
            this.fetchData();
        },
        mounted() {
            console.log(this.$refs.mainPanel);
            this.$refs.mainPanel.style.display = 'block';
        }
    })
</script>
</body>
</html>