package cn.xuan.springcloud.service;

import cn.xuan.springcloud.entities.CommentResult;
import cn.xuan.springcloud.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
    @Override
    public CommentResult<Payment> paymentSQL(Long id)
    {
        return new CommentResult<>(44444,"服务降级返回,---PaymentFallbackService",new Payment(id,"errorSerial"));
    }
}
