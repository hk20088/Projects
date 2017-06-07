package com.newspace.aps.payChannel;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ByteArrayEntity;

import com.newspace.common.utils.HttpClientUtils;

public class Test {

	
	public static void main(String[] args) throws ConnectTimeoutException, SocketTimeoutException, Exception {
		//贵州广电异步通知接口
		String url = "http://10.1.1.62:8080/APS/guizhouNotify.do";
		String body = "version=1&clientcode=SZATT001&clientpwd=6cb2d0b736f3841658074f9b854e8c54&citycode=5201&servicecode=&requestid=SZATT00120160504974b9659&requestContent=%7B%22bankaccno%22%3A%22%22%2C%22orderNo%22%3A%22100000271322%22%2C%22paycode%22%3A%22040400%22%2C%22payreqid%22%3A%22201605040001896383%22%2C%22payway%22%3A%2233%22%2C%22status%22%3A%222%22%7D";
		
		String str = HttpClientUtils.post(url, body, "application/xml", "utf-8", 5000, 30000);
		
		System.out.println(str);
		
	/*	BigDecimal payMent = new BigDecimal(new DecimalFormat("#.00").format(1 / 100.0));  //金额，保留两位小数
		System.out.println(payMent);
		
		String str = "transdata={\"exOrderNo\":\"000713560552BYORDFLG000733538341\",\"payOrderNo\":\"10012017011236990156\",\"appId\":\"20150827111129824119\",\"amount\":200,\"payType\":\"COIN\",\"transTime\":\"2017-01-12 18:22:25\",\"counts\":1,\"payPoint\":\"1990\",\"result\":0}&sign=mncIQk3Mslj7mi5FwConnection closed by foreign host.DRgKvZPZkhHXExbw681QwJ7u6XmCUPzdZREo1u5Oh5rtWwhw/3orVBZdoKfBmULS+d/8+D0wrRFet5AOOo23iTHdwqHnsLn8kLqM45Yjq+XU6IAXItOPl6IE=";
		System.out.println(new ByteArrayEntity(str.getBytes()));
	*/
	}
	
}
