package com.hq.onlinefast.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hq.onlinefast.bean.Script;
import com.hq.onlinefast.bean.User;
import com.yomahub.liteflow.script.ScriptBean;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
@ScriptBean("ScriptMapper")
public interface ScriptMapper extends BaseMapper<Script> {

}
