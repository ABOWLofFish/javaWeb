DROP DATABASE reviewsystem;
CREATE DATABASE reviewsystem;
USE reviewsystem;

-- id为8位数字，不得含有字母或特殊字符，作为主码不可重复
-- 6-16位密码包含字母和数字
-- 电话号码为11位数字
-- 邮箱包含'@'符号,'@'符号后需要出现多个由'.'分割的词

-- 管理员表
CREATE TABLE manager(
	id CHAR(8) CONSTRAINT m_id_check CHECK(REGEXP_LIKE(id,'\\d{8}')),
	name VARCHAR(20) NOT NULL,
	password VARCHAR(25) CONSTRAINT m_password_check CHECK(REGEXP_LIKE(password,'^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$')),
	phone CHAR(11) CONSTRAINT m_phone_check CHECK(REGEXP_LIKE(phone,'[0-9]{11}')),
	email VARCHAR(256) CONSTRAINT m_email_check CHECK(REGEXP_LIKE(email,'.*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+')),
    CONSTRAINT m_id_primary_key PRIMARY KEY(id)
);
INSERT INTO manager VALUES('22921234','张管理员','Zhang123','12345678901','zhangsan@san.com');
INSERT INTO manager VALUES('22921235','李管理员','lisi123123','12345678902','lisi@san.com');
INSERT INTO manager VALUES('22921236','王管理员','wang123123','12345678902','wang@wang.com');
INSERT INTO manager VALUES('22921237','孙管理员','sun123123','12345678902','sun@sun.com');

-- 学生表
CREATE TABLE student(
	id CHAR(8) CONSTRAINT s_id_check CHECK(REGEXP_LIKE(id,'\\d{8}')),
	name VARCHAR(20) NOT NULL,
	password VARCHAR(25) CONSTRAINT s_password_check CHECK(REGEXP_LIKE(password,'^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$')),
	phone CHAR(11) CONSTRAINT s_phone_check CHECK(REGEXP_LIKE(phone,'[0-9]{11}')),
	email VARCHAR(256) CONSTRAINT s_email_check CHECK(REGEXP_LIKE(email,'.*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+')),
    CONSTRAINT s_id_primary_key PRIMARY KEY(id)
);
INSERT INTO student VALUES('22921234','张三','Zhang123','12345678901','zhangsan@san.com');
INSERT INTO student VALUES('22921235','李四','blabla123123','12345678902','blabla@san.com');
INSERT INTO student VALUES('22921236','王五','blabla123123','12345678902','blabla@san.com');
INSERT INTO student VALUES('22921237','赵六','blabla123123','12345678902','blabla@san.com');

-- 导师表
CREATE TABLE teacher(
	id CHAR(8) CONSTRAINT t_id_check CHECK(REGEXP_LIKE(id,'\\d{8}')),
	name VARCHAR(20) NOT NULL,
	password VARCHAR(25) CONSTRAINT t_password_check CHECK(REGEXP_LIKE(password,'^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$')),
	phone CHAR(11) CONSTRAINT t_phone_check CHECK(REGEXP_LIKE(phone,'[0-9]{11}')),
	email VARCHAR(256) CONSTRAINT t_email_check CHECK(REGEXP_LIKE(email,'.*@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+)+')),
    CONSTRAINT t_id_primary_key PRIMARY KEY(id)
);
INSERT INTO teacher VALUES('22921234','张老师','Zhang123','12345678901','zhangsan@san.com');
INSERT INTO teacher VALUES('22921235','李老师','lisi123123','12345678902','lisi@san.com');

-- 活动id为6个字符，作为主码不可重复
-- 活动名为20个可变字符，唯一约束
-- 结束日期需要在开始日期之后

-- 活动表
CREATE TABLE project(
    id CHAR(8) PRIMARY KEY,
    project_name VARCHAR(20) UNIQUE,
    start_date CHAR(20),
    end_date CHAR(20),
    description TEXT(512),
    CHECK(end_date >= start_date)
);
INSERT INTO project VALUES('00000000','活动零','2022-6-26','2022-6-27','这是活动描述');
INSERT INTO project VALUES('00000001','活动一','2022-6-26','2022-6-28','这是活动描述');
INSERT INTO project VALUES('00000002','活动二','2022-6-26','2022-6-28','这是活动描述');
INSERT INTO project VALUES('00000003','活动三','2022-6-26','2022-6-28','这是活动描述');
INSERT INTO project VALUES('00000004','活动四','2022-6-26','2022-6-28','这是活动描述');
INSERT INTO project VALUES('00000005','活动五','2022-6-26','2022-6-28','这是活动描述');

-- 团队id由9个字符组成，作为主码不可重复
-- 团队名称由20个可变长度字符组成，唯一约束
-- 报告为TEXT类型，长度为2048个文本字符

-- 活动队伍表
CREATE TABLE team(
    project_id CHAR(8),
    team_id CHAR(11),
    team_name VARCHAR(20) UNIQUE,
    captain_id CHAR(8) NOT NULL,
    member1_id CHAR(8) NOT NULL,
    member2_id CHAR(8) NOT NULL,
    teacher_id CHAR(8) NOT NULL,
    report_content TEXT(2048),
    PRIMARY KEY(project_id,team_id),
    FOREIGN KEY(project_id) REFERENCES project(id)
        ON UPDATE CASCADE -- 更新活动id时，级联更新活动队伍表活动id
        ON DELETE CASCADE, -- 删除活动id时，级联删除活动队伍记录
    FOREIGN KEY(captain_id) REFERENCES student(id)
        ON UPDATE CASCADE -- 更新队长id时，级联更新活动队伍表队长id
        ON DELETE CASCADE -- 删除队长id时，级联删除活动队伍记录
);
INSERT INTO team VALUES('00000000','00000000000','零号队伍','22921234','22921235','22921236','22921234','这是我们队伍的提交报告。');
INSERT INTO team VALUES('00000000','00000000001','一号队伍','22921235','22921234','22921236','22921234','这是我们队伍的提交报告。');
INSERT INTO team VALUES('00000001','00000000000','零号队伍，什么，名字被用了','22921234','22921235','22921237','22921234','哈哈哈，我们不写报告');
INSERT INTO team VALUES('00000001','00000000001','一号队伍，什么，名字被用了','22921234','22921236','22921237','22921234','哈哈哈，我们开摆啦');
INSERT INTO team VALUES('00000002','00000000001','一号队伍，什么，名字又被用了','22921234','22921235','22921236','22921234','哈哈哈，我们的报告什么都没有');
INSERT INTO team VALUES('00000002','00000000002','二号队伍','22921234','22921236','22921236','22921234','哈哈哈，我们也不知道为什么要哈哈哈');


-- DELETE FROM student WHERE id='22921234'; -- 删除队长学号后，相应活动团队也删除
-- INSERT INTO team VALUES('000000','000000002','二号队伍','22921236','张老师','这是我们队伍的提交报告。'); -- ERROR:外码约束不符

-- 评审表
CREATE TABLE review(
    project_id CHAR(8) PRIMARY KEY,
    review_name VARCHAR(20) UNIQUE,
    start_date CHAR(20),
    end_date CHAR(20),
    description TEXT(512),
    CHECK(end_date >= start_date)
);
INSERT INTO review VALUES('00000000','评审零','2022-6-26','2022-6-28','评审的描述');
INSERT INTO review VALUES('00000001','评审一','2022-6-26','2022-6-28','评审的描述');
INSERT INTO review VALUES('00000002','评审二','2022-6-26','2022-6-28','评审的描述');

-- 评审意见为TEXT类型，由256个文本字符组成
-- 分数在0~100之间

-- 评审信息表
CREATE TABLE review_info(
    project_id CHAR(8),
    team_id CHAR(11),
    teacher_id CHAR(8),
    review_content  TEXT(256),
    grade NUMERIC(5,2) CHECK(grade between 0 and 100),
    FOREIGN KEY(project_id) REFERENCES review(project_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY(project_id,team_id) REFERENCES team(project_id,team_id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY(teacher_id) REFERENCES teacher(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    PRIMARY KEY(project_id,team_id,teacher_id)
);
INSERT INTO review_info VALUES('00000000','00000000000','22921234','你们做的很好！',100);
INSERT INTO review_info VALUES('00000000','00000000001','22921234','你们做的很烂！',1);
INSERT INTO review_info VALUES('00000001','00000000000','22921234','我是老六',59);
INSERT INTO review_info VALUES('00000001','00000000001','22921234','你们做的很好，但是我不会打分，随便点了！',1);
INSERT INTO review_info VALUES('00000002','00000000001','22921234','你们做的很烂，一不小心点了满分！',100);

select team.team_id,team.team_name,project.project_name,team.captain_id,
team.member1_id,team.member2_id, team.teacher_id,team.report_content,review_info.grade
from team,project,review_info
where team.project_id='00000000' and project.id='00000000'
and review_info.project_id='00000000' and review_info.team_id=team.team_id;

select project_id,report_content,teacher.name as teacher_name
from team,teacher
where project_id='00000000' and team_id='00000000000' and teacher.id=team.teacher_id;