package com.hq.onlinefast.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("user")
public class User extends Model<User> implements Serializable {
    private static final long serialVersionUID = 6401942840459021558L;
    private Long id;
    private String name;
    private Integer age;
    private String email;
}
