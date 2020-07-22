package cn.xuan.springcloud.controller;

import cn.xuan.springcloud.entities.CommentResult;
import cn.xuan.springcloud.entities.Payment;
import cn.xuan.springcloud.myhandler.CustomerBlockHandler;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource",blockHandler = "handleException")
    public CommentResult byResource() {
        return  new CommentResult(200,"按照资源名称限流测试",new Payment(2020L,"serial001"));
    }

    public CommentResult handleException(BlockException exception) {
        return  new CommentResult(444,exception.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl")
    public CommentResult byUrl() {
        return new CommentResult(200,"按url限流测试OK",new Payment(2020L,"serial002"));
    }

    @GetMapping("/rateLimit/customerBlockHandler")
    @SentinelResource(value = "customerBlockHandler",
            blockHandlerClass = CustomerBlockHandler.class,
            blockHandler = "handlerException2")
    public CommentResult customerBlockHandler()
    {
        return new CommentResult(200,"按客戶自定义",new Payment(2020L,"serial003"));
    }


}
