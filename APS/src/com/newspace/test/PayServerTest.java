package com.newspace.test;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newspace.aps.model.order.PayReqVo;
import com.newspace.aps.model.order.PayRespVo.PayRespData;
import com.newspace.aps.pay.PayHandler;
import com.newspace.aps.service.PayServer;
import com.newspace.aps.service.impl.PayServerImpl;
import com.newspace.common.utils.JsonUtils;
import com.newspace.common.utils.TwoTuple;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:WebRoot/WEB-INF/config/*-*.xml","file:config/*-config.xml"})
public class PayServerTest {

	
	@Resource
	private PayServerImpl payServer;
	

	
	@Test
	public void pay() throws TException {
		
		PayReqVo reqVo = new PayReqVo();
		reqVo.setClientPackageName("com.atet.tvmarket");
		reqVo.setUserId(33);
		reqVo.setExternalId("fdfddfd");
		reqVo.setKeyId("20140721092137104107");
		reqVo.setDeviceId(338);
		reqVo.setMerchId(133);
//		reqVo.setPayPoint(29);
		reqVo.setCount(1);
		reqVo.setAmount(150);
		reqVo.setExternalOrderNo("CPORDERmO");
		reqVo.setNotifyUrl("www.baidu.com");
		reqVo.setPrivateInfo("i am privateInfo");
		reqVo.setVersionCode("3");
//		reqVo.setExtraPayInfo("{\"flag\":\"PAY\",\"client\":\"PINGYAO\",\"strTxnTime\":\"20160826163825822\",\"strOrderId\":\"1490151976558\",\"messCode\":\"66666666\"}");
		reqVo.setExtraPayInfo("{\"client\":\"COIN\"}");
		reqVo.setOrderNo("10012017052588460003");
		
		reqVo.getData();
		reqVo.setSign("E1D0D1A774F36B6FA44557397B7EF634B445C81FEE787CAAA1A4C74FB452740E776F8A595D704F844372605F49AB9888A352AA79EFE53EE579BD3EB67C3B4C47");
		
		String json = JsonUtils.toJsonWithExpose(reqVo);
		
		System.out.println(json);
		// 设置传输通道 - 普通IO流通道
		TTransport transport = new TSocket("10.1.1.243", 7913);
		transport.open();
		
		//使用高密度二进制协议
		TProtocol protocol = new TCompactProtocol(transport);
//		TProtocol protocol = new TBinaryProtocol(transport);
		
		//创建Client
		PayServer.Client client = new PayServer.Client(protocol);
		
		long start = System.currentTimeMillis();
		for(int i=0; i<1; i++){
			String str = client.pay(json);
			System.out.println(str);
		}
		System.out.println("耗时：" + (System.currentTimeMillis() - start)+"ms");
		
		//关闭资源
		transport.close();
		
//		String str = payServer.pay(json);
//		System.out.println(str);
	}
	
	@Resource
	private PayHandler payHandler;
	@Test
	public void testGetMerchInfoSwitch()
	{
		PayReqVo reqVo = new PayReqVo();
		reqVo.setUserId(1);
		reqVo.setClientPackageName("com.atet.tvmarket");
		reqVo.setAmount(25);
		TwoTuple<Integer,PayRespData> balance = payHandler.getUserBalance(reqVo);
		System.out.println(balance.toString());
	}


}
