package com.hq.onlinefast.groovy;

import com.yomahub.liteflow.script.ScriptBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@ScriptBean("demo")
public class DemoBean1 {


    public String getDemoStr1(){
        return "hello";
    }

}
