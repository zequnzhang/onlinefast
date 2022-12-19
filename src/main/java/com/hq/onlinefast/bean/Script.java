package com.hq.onlinefast.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("script")
public class Script extends Model<Script> implements Serializable {
    private static final long serialVersionUID = 6401942840459021558L;
    private Long id;
    private String applicationName;
    private String scriptId;
    private String scriptName;
    private String scriptData;
    private String scriptType;
}
