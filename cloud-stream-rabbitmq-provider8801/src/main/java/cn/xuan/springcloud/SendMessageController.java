package cn.xuan.springcloud;


import cn.xuan.springcloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider messageProvider;


    @GetMapping("/sendMessage")
    public String send(){
        return messageProvider.send();
    }
}
