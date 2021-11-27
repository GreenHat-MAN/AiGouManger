package com.bigdata.store.quartz;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: duYang
 * @Date: 2021/7/21 9:32
 * @Version: 1.0
 */

/**
 * 支付宝付款接口，以下数据都是沙箱环境下。
 */
public class AliPayProvider {
    private static final String APP_ID = "2021000117690994";
    private static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCCziM8FdGX/Ja2Vshpe3zkdPKD1PyDaBBmSPQ8hs2fapv9W3cpAHY5TSp5Sp8YvHjnwi24asZXH5dJmyJxaLu0hdaf2/ZGdu59eThwichbJ2KhNWs+QreBfHeP2W56zR2N0PGR1LkJ5ExGtP2rP5W/RUCJHb1W29T2etDSj5QmboLrmfiYR0LiFzwkig5woNg5Vz0rIs4Vt0JQSB9qEMC/ThEW6+Lj2vZcmwawJHf4cHAr6DgRacsb2MB9Tt68CRo/JrzXa9mrla2/A44IxpIuz665NhzbT8wUHTjvE6vtvBkPKQCMhu0P1chkUfMYbr4A27MKJ5pcLbHbfezbbIxbAgMBAAECggEADaPVRMS5sHGkfasbBvTbly8iwuCI3GMTorbk0RL1ijQhxA+vo1Tq/xqu3SsM4BV3PiOfhOWNfTRMIF7O/qh/OlnigZT8X2izgd4MJqAXhnna7RiW8E0guZ3zEcsInEGbXVh2eFCIYyGtWooxEZR11xYvnpy9S7tsv68a63oRZthkKT1QzHLG44EV0QTANAugSvOLh7ah/WUBubpH3dvka7KSVbP9PxTsffrXPlYd/F78OKhKqUHrJ2q4IADAb5OcQkdZTqIdIsUbZmdhAfelz7gHaqOYYhd92O7QPfRWhBeCZJFBUwLQQwMYGAXMfdGcAqyF+Xei39z4aC+XOC7S0QKBgQDlDXjVVhl2HImbHmeiYI85lm8ArvogA/GTpiEURvfqvlL7q2YC3lfi/mDfUYoaz7SH+7amMm4sxZs8jgiGt52g/mt3cXC5kVhhsU/WhhcND/3OhVgJ3B/YtsOHyBPu456oIODveleC2a4kIXfLb9qqAQbsrhIRBOSKRKsQY846dwKBgQCSMa8vB5uU8jTMxeg9IiF9bOVyOuXc5KIg47DCwnW5xkgZh8FmiXBcmwMKx/a6RAsTIWpPNaSfHGg8lZAyse6DmI1JOE0+760SFYoVaL0p5GN6D/FwqG9o5cEJdCT0sEkZ3xYGRcyyvXvPTEvJ36P3q01Lyq4PUNk2+rEFFQXSPQKBgHMq6u6ZdAPnJWlRSFpkop8Z/IYjdLBUS0sp5MgFRvJI8ACKlagrk5UWwi6HGSGR/fvBnHzELpFkakFwSOkzuZYRVoegCkXDxuSX+sSqdzyiJcOJWFLc00Zr3rSO7TbYTBXNMkw2/3lTajPQgCNSAmgoTAsFGyBjjWXYUQrUpATRAoGAK7w9Yl5uRvLmpNQYFCHfzvwC8Fd6aMjlT92OBdXTDk6pRyn7y8cWHD4rfNAxmau9fG2ktimgJBGFhYpg5ymf5zJ+Igd75HYjWqOi+aCp5pxyvhOC8WcSXVlOlJtwattKIzlAB4ReJqLVJUD38166WN+gcioiRGa1dOMujgyp/ykCgYEAo9HYzQGGSIQCV2VqIV12nCLSp7QVDFcXRtmoUC25TIPTzfBnqjVs5xPM42LJ1XEFrEhRlU9F1Dk6Ietwi0NF8bOD+zmbt/wmA9eUDHEe0YOxiW2G1r2us1wMg6ZYS61jSiuX2u5gsoQmmXF4DYKxNi1m5cOLE16b1HO3UymqwSc=";
    private static final String FORMAT = "JSON";
    private static final String CHARSET = "utf-8";
    private static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiuKzqkBk8WjJma5TXlxkDtl0UehKf/A3eNLgbgNaMju51a5dSmvnvvYByQlTzVukrkoUlvxRZwGibFptU1fP4sRPsLTNLxhw1FiCbqyQmxQK+8RZbQQzHwuW8ujUzmZ5/ah4+tLVBGcY8HdS8FvKkWpzTI0Ue8gngx5tGvMBKTCSKSj/cgTgX/2C64gHA0CL6Cz9o0ac55MfR20DR7ijjIKo9ap/rlSgqzT3BvfWKC9YNZPMkMoKvziSodNxAt623MSkZ8G/fr/3xI8rdk0KmVObtwMpN0IDK1StrEVvWjjISJkDBKuHFvTnqpvb5Jb+s1fjMXHultso1YHsRhWBzQIDAQAB";
    private static final String SIGN_TYPE = "RSA2";

    private static AlipayClient alipayClient;

    static {
        alipayClient = new DefaultAlipayClient("https://openapi.alipaydev.com/gateway.do", APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }

    //传入订单编号,价格,标题 (String content省略掉)
    public static String generateAliPay(String id, double price, String title) {
        //创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl("http://localhost:8080/store_v1/ReturnURL");//同步
        //alipayRequest.setNotifyUrl("http://domain.com/CallBack/notifyURL.html");//异步，必须存在外网
        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + id + "\"," +                //订单号（支付宝要求商户订单号必须唯一）
                " \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +    //销售产品码，与支付宝签约的产品码名称，目前仅支持FAST_INSTANT_TRADE_PAY。
                " \"total_amount\":" + price + "," +                 //价格
                " \"subject\":\"" + title + "\"," +                  //订单标题
                " \"body\":\"" + title + "\"," +                     //订单描述
                " \"timeout_express\":\"30m\"," +                    //订单描述
                " \"extend_params\":{" +                             //业务扩展参数
                " \"sys_service_provider_id\":\"" + APP_ID + "\"" +  //系统商编号，该参数作为系统商返佣数据提取的依据，请填写系统商签约协议的PID
                " }" +
                " }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    public static boolean query(String id) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", id);
        request.setBizContent(jsonObject.toJSONString());
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (response.isSuccess() && response.getTradeStatus().equals("TRADE_SUCCESS")) {
                return true;
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean signVerification(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        try {
            return AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET, SIGN_TYPE);//调用SDK验证签名
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return false;
    }
}
