package cn.xuan.springcloud.controller;

import cn.xuan.springcloud.entities.CommentResult;
import cn.xuan.springcloud.entities.Payment;
import cn.xuan.springcloud.lb.LoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

@RestController
@Slf4j
public class orderController {

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private LoadBalancer loadBalancer;

    @Resource
    private DiscoveryClient discoveryClient;

    public static final String PAYMENT_URL = "http://CLOUD-PAYMENT-SERVICE";

    @GetMapping("/consumer/payment/create")
    public CommentResult<Payment> create(Payment payment){

        return restTemplate.postForObject(PAYMENT_URL+"/payment/create",payment,CommentResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommentResult<Payment> getPayment(@PathVariable("id") Long id){

        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id,CommentResult.class);
    }

    @GetMapping(value = "/consumer/payment/lb")
    public String getPaymentLB(){

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");

        if (instances==null||instances.size()<=0){
            return null;
        }


        ServiceInstance instance = loadBalancer.instances(instances);

        URI uri = instance.getUri();

       return restTemplate.getForObject(uri+"/payment/lb",String.class);

    }
}

