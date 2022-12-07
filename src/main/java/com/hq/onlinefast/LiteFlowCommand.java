package com.hq.onlinefast;

import cn.hutool.json.JSONUtil;
import com.hq.onlinefast.bean.User;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.DefaultContext;
import com.yomahub.liteflow.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class LiteFlowCommand implements CommandLineRunner {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private FlowExecutor flowExecutor;

    @Override
    public void run(String... args) throws Exception {
        LiteflowResponse response = flowExecutor.execute2Resp("chain1", "arg");
        DefaultContext context = response.getFirstContextBean();
        List<User> userList  = context.getData("userList");
        log.info(JSONUtil.parseArray(userList).toString());
////        log.info(JsonUtil.toJsonString(context.getData("student")));
//        if (response.isSuccess()){
//            log.info("执行成功");
//        }else{
//            log.info("执行失败");
//        }

    }
}
