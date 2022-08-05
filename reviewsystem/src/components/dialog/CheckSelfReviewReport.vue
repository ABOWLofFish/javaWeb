
<!-- template - 查看某导师对于某一个团队报告的组件 -->

<template>
    <div style="margin-left: 20px;margin-right: 20px;">

        <!-- descriptions - 展示团队信息 -->
        <el-descriptions title="团队信息">
            <el-descriptions-item label="团队号">{{ team_information.team_id }}</el-descriptions-item>
            <el-descriptions-item label="队长号">{{ team_information.captain_id }}</el-descriptions-item>
            <el-descriptions-item label="指导老师">
                {{ team_information.teacher_id }}
            </el-descriptions-item>
            <el-descriptions-item label="团队名">{{ team_information.team_name }}</el-descriptions-item>
            <el-descriptions-item label="队长">{{ team_information.captain_name }}</el-descriptions-item>
        </el-descriptions>

        <!-- div - 显示团队报告 -->
        <el-divider content-position="center">
            <el-icon>
                <reading />
            </el-icon>
            团队报告
        </el-divider>
        <el-form ref="team_info" label-width="0px">
            <el-form-item>
                <div style="margin-top:5px;padding:10px">
                    {{ report_content }}
                </div>
            </el-form-item>
        </el-form>

        <el-divider>
            <el-icon>
                <Stamp />
            </el-icon>
            评审内容
        </el-divider>

        <!-- descriptions - 显示评审内容 -->
        <el-descriptions column="1">
            <!-- 导师编号 -->
            <el-descriptions-item>
                <template #label>
                    <el-icon :style="iconStyle">
                        <user />
                    </el-icon>
                    导师姓名
                </template>
                {{ review_inform.review_teacher }}
            </el-descriptions-item>

            <!-- 导师打分 -->
            <el-descriptions-item>
                <template #label>
                    <el-icon :style="iconStyle">
                        <EditPen />
                    </el-icon>
                    导师打分
                </template>
                {{ review_inform.review_score }}
            </el-descriptions-item>

            <!-- 导师建议 -->
            <el-descriptions-item>
                <template #label>
                    <el-icon :style="iconStyle">
                        <tickets />
                    </el-icon>
                    导师建议
                </template>
                {{ review_inform.review_content }}
            </el-descriptions-item>
        </el-descriptions>
    </div>
</template>

<script>
export default {
    props: {
        team_information: Object,
        project_id: String
    },
    data() {
        return {
            report_content: '',
            review_inform: {
                review_score: 0,
                review_content: ''
            }
        }
    },
    created() {
        this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
        this.getData();
    },
    beforeUpdate() {
        this.list = JSON.parse(sessionStorage.getItem("userInfo"));//会话存储 获取该用户信息
        this.getData();
    },
    methods: {
        getData() {
            //获取自己的评审内容
            this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/" + this.team_information.team_id + "/myReview")
                .then(successResponse => {
                    if (successResponse.data.code === 200) {
                        console.log(this.team_information.captain_name)
                        console.log(successResponse.data)
                        this.setData(successResponse.data);
                    }
                    else if (successResponse.data.code === 500) {
                        console.log("FAIL CODE 500")
                    }
                })
                .catch(failResponse => {
                });
            //获取团队报告
            this.$http.get("/" + this.list.identity + this.list.user_id + "/" + this.project_id + "/" + this.team_information.team_id + "/report")
                .then(successResponse => {
                    if (successResponse.data.code === 200) {
                        this.setReport(successResponse.data);
                    }
                    else if (successResponse.data.code === 500) {
                        console.log("FAIL CODE 500")
                    }
                })
                .catch(failResponse => {
                })
        },
        //显示评审内容
        setData(item) {
            this.review_inform.review_teacher = this.list.user_name
            this.review_inform.review_content = item.data.review_content;
            this.review_inform.review_score = item.data.review_level;
        },
        //显示团队报告
        setReport(item) {
            this.report_content = item.data.report_content
        }
    }
};
</script>

