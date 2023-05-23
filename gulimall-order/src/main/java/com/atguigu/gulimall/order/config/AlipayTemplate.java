package com.atguigu.gulimall.order.config;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.gulimall.order.vo.PayVo;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "alipay")
@Component
@Data
public class AlipayTemplate {
    //在支付宝创建的应用的id
    private   String app_id = "2021000122693198";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private  String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC4ebEywZyevRkZWSXlBoasukxh6Mck6B3440Wj4GdnBHT+pgIDVBRvK9fXfei+v6wB+kw/c/WHHZENG8ZYBEZs8dn1k6adWko+4Kj9TrQcBjDFky5nKK8Kh0hnB32SOkba/MqCoM/ZZLOEWNdn0jnpiJl0SvXmfkWBhy9JZ7Oytld8EwRD0p9DhRt5yw/Mnx2ibTX6ylvTsvVdDUq3kJzwnwYqRcSDhLZn0b5P05WPh5Pr7jbQgyNbsc++DJ4MSQ9ji+TlS2PshW8ZjgmkyxIRVVhapgUs18pQbUGtrkk2aPV2Ji8iXBQdNSvDwbCHlaapaoI9Avcowel0bS37l9r1AgMBAAECggEAVfwK1wCdkvPLBiCiZBzlmw7+2UgZwp8akU7Fv/ux1reiHKueFpUTLTo/UecTHA3VC9rxvUW8dK0YVgbTLWVYuiEEmc2nhIJ4RtSKj/8mPkW4Z+8RWu4AoKSr37IDGqxlKF9ZuQdhug+RCSgrxu2bJroP5L+n3DaRCS3Moadff/Vn9OB9yTJ8MhrOpTFcZ56rGwHmuxkOm3uxq916A5Iso678TDphkUn3OJjdrfmnaFBUnr8BlQOqSWFUbw8M6q8IRS3KUQsGXptMX3hEvBzD8d7hOkCxVss+9tktUzL/knAxNoPVUSNwZqTyxoP620OHsL5VW3JAZmc4IW5lTHysBQKBgQDzNSLJJsPvhiK+3uDrv764CvjX1MYvCBE/FyoLn1JnOKB/utAo7GTg0sA09IpSfHpG4C3gCGJ9J3QN1e8Aj9Kjn61bXxhxbIrIYYF70xCCuHvCjCMWXZ/xpC6KITemJh4zjstxtViyhuIe9w9jyn7YmNn0f6QIgG7Vyjh/S2Lu3wKBgQDCLbWR7hUNaZIOsyJgpmpklrNEDTHo9XtE6/JVGgBXz9kugU3iwrLZgg+MTKrxNrR1o/p2rYi3UzouNqD4xwqUqQru3xAyuRY5LdLYDQACR9sPMGv9idBoVMBHS/bT04bbbMWm2VNAYgKeWUYFLN2GP+oZSScnGm/eKp/lkKM0qwKBgQCBKUYJF+pU6aLIRWukKBfse/89+IUr8xfdrrqoo3tK0FsEmrz0Dt9gOuzS8tAHEI6L14Dliu5rUymiM2yTms4eEa4qaW18x01JL+dpUGVjVwB7CJuVxDEuTqhcuVlphrLhZMOM5udtII80LLdioucMttiBrcv6q+Kw7Y1lpS6sdwKBgQCYj9c/j6KExjRWTlItQ7MF0w2H4yIamXy/eu4MBQDN/WTyz+8EwdpJYJGctsP7/vIsF6j6w39OIhrtIki5DZF3mZUYdrPAVoFqbWFiFFy0bgZj2bSPIE9ZGXV0zu9gIKqtDboBTHDHffn2tcd423Y1O1RPh5U2eOP51t2IPWqtPwKBgQC6NKs5fqtgFvMCemvV3BHR82C4EhnoJBH/li2htye4h091D/ylR0cQslH9twM8TrtT4Y+x/hkKcOTbon2inKmF/EtV2aUiZnaIk7tJebjLqFhdfbwFhcQBAj7gbMtb7sPrPCQqAv9JfjlFci1pWLEmUU8iSM0DoARBwe95aIAvZg==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private  String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArNMO4qpHHqDh8VbSnihtdDqJu2CikTrbMjwBZ4wQ8fbhsvxRno8XNvDRBUq2MpuYIL5EWz2/2iQMHWQYanascMABi18mHWzIBdkAFtP9dYo46cYV+0qarCb+Ch/T2Xaok7R2d495l7QMhMs6ZOUxIMBlWwymC4GS44GpDbS87ylZ5eKz4twUKHUjFvDUZna0d8v6krLlwhrCkGCJJKa8vbhiZAjGK675E+9QkXZMI++WmqEgld4j2H4W6O5ufHRs/199I1joYnw9Lk8QUKocVdGyBcd2xgRjnXS6m/AXg1VnG/9X82DyjKN9DLFIM16fGbudG13EO33R4kZkJDoFqQIDAQAB";
    // 服务器[异步通知]页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    // 支付宝会悄悄的给我们发送一个请求，告诉我们支付成功的信息
    private  String notify_url=  "http://7424981c9r.goho.co/payed/notify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    //同步通知，支付成功，一般跳转到成功页
    private  String return_url = "http://member.gulimall.com/memberOrder.html";

    // 签名方式
    private  String sign_type = "RSA2";

    // 字符编码格式
    private  String charset = "utf-8";
    private String timeout = "30m";

    // 支付宝网关； https://openapi.alipaydev.com/gateway.do
    private  String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    public  String pay(PayVo vo) throws AlipayApiException {

        //AlipayClient alipayClient = new DefaultAlipayClient(AlipayTemplate.gatewayUrl, AlipayTemplate.app_id, AlipayTemplate.merchant_private_key, "json", AlipayTemplate.charset, AlipayTemplate.alipay_public_key, AlipayTemplate.sign_type);
        //1、根据支付宝的配置生成一个支付客户端
        AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl,
                app_id, merchant_private_key, "json",
                charset, alipay_public_key, sign_type);

        //2、创建一个支付请求 //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(return_url);
        alipayRequest.setNotifyUrl(notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = vo.getOut_trade_no();
        //付款金额，必填
        String total_amount = vo.getTotal_amount();
        //订单名称，必填
        String subject = vo.getSubject();
        //商品描述，可空
        String body = vo.getBody();

        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+timeout+"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");


        String result = alipayClient.pageExecute(alipayRequest).getBody();

        //会收到支付宝的响应，响应的是一个页面，只要浏览器显示这个页面，就会自动来到支付宝的收银台页面
        System.out.println("支付宝的响应："+result);

        return result;

    }
}
