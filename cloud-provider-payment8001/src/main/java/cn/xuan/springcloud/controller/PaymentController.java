package cn.xuan.springcloud.controller;

import cn.xuan.springcloud.entities.CommentResult;
import cn.xuan.springcloud.entities.Payment;
import cn.xuan.springcloud.PaymentService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping("/payment/create")
    public CommentResult create(@RequestBody Payment payment){

        int result = paymentService.create(payment);
        log.info("*****插入结果："+result);

        if (result>0){
            return new CommentResult(200,"插入数据库成功,serverPort"+serverPort,result);
        }else {
            return new CommentResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping("payment/get/{id}")
    public CommentResult<Payment> getPaymentById(@PathVariable("id") Long id){

        Payment payment = paymentService.getPaymentById(id);

        if(payment != null)
        {
            return new CommentResult(200,"查询成功,serverPort"+serverPort,payment);
        }else{
            return new CommentResult(444,"没有对应记录,查询ID: "+id,null);
        }
    }

    @GetMapping("/payment/discovery")
    public Object discoverAll(){
        List<String> services = discoveryClient.getServices();
        for(String service : services){
            log.info("*****service"+service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info(instance.getServiceId()+instance.getHost()+instance.getPort()+instance.getPort()+instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/lb")
    public String getPaymentLB()
    {
        return serverPort;
    }

}
