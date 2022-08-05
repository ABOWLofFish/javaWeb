/*2. 实体层，数据库表的映射实体类，存放POJO对象*/
/**
 * @Description: 用户数据
 * @author： 李文博
 * @date: 2022/6/27 21:13 update by 庄婉如 增加了 identity和confirm_password
 */
/**
 * @Description: 转储用户信息
 * @author： 李文博
 * @date: 2022/6/28 latest update by 庄婉如
 */
package javaWeb.test.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.logging.log4j.message.StringFormattedMessage;

import javax.persistence.Column;
import javax.persistence.Transient;

public class UserBean {
    @Column(name = "id")
    @JsonProperty(value = "user_id")
    protected String user_id; // 账号

    @Column(name = "name")
    @JsonProperty(value = "user_name")
    protected String user_name; // 用户名

    @Column(name = "password")
    @JsonProperty(value = "password")
    protected String password; // 密码

    @Column(name = "phone")
    @JsonProperty(value = "telephone")
    protected String telephone; // 电话

    @Column(name = "email")
    @JsonProperty(value = "e_mail")
    protected String email; //邮箱

    @Transient
    @JsonProperty(value = "identity")
    private String identity;//身份

    public UserBean() {
        user_id = null;
        user_name = null;
        password = null;
        telephone = null;
        email = null;
    }
    public UserBean(String id,String password,String identity) {
        this.user_id = id;
        this.password = password;
        this.identity = identity;
    }
    public UserBean(String id,String name, String password, String tel, String email) {
        this.user_id = id;
        this.user_name = name;
        this.password = password;
        this.telephone = tel;
        this.email = email;
    }
    public UserBean(String id,String name, String tel, String email) {
        this.user_name = name;
        this.user_id = id;
        this.telephone = tel;
        this.email = email;
    }


    @Override
    public String toString(){
        return String.format("user-id:%s\nidentity:%s\n",this.user_id,this.password);
    }
    public String getId() {
        return user_id;
    }

    public void setId(String id) {
        this.user_id = id;
    }

    public String getName() {
        return user_name;
    }

    public void setName(String name) {
        this.user_name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return telephone;
    }

    public void setTel(String tel) {
        this.telephone = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}