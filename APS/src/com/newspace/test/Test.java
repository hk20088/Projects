package com.newspace.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.newspace.aps.model.order.PayReqVo;
import com.newspace.aps.service.PayServer;
import com.newspace.common.utils.JsonUtils;

public class Test {



	public static void main(String[] args) {
		ExecutorService service = Executors.newCachedThreadPool();
		for(int i=0; i<1; i++)
		{
			
			final int count = i;
			Runnable run = new Runnable() {
				
				@Override
				public void run() {
					
					try
					{
						System.out.println("第"+count+"次");	
						aa(String.valueOf(count));
					}
					catch (TException e) 
					{
					
						e.printStackTrace();
					}
				}
			};
			service.execute(run);
		}
		
		service.shutdown();
	}
	
	private static void aa(String s) throws TException
	{
		
		PayReqVo reqVo = new PayReqVo();
		reqVo.setClientPackageName("com.atet.market.tv");
		reqVo.setUserId(1);
		reqVo.setExternalId("");
		reqVo.setKeyId("20150929520927572766");
		reqVo.setDeviceId(80);
		reqVo.setMerchId(1);
//		reqVo.setPayPoint(0);
		reqVo.setCount(1);
		reqVo.setAmount(10000);
		reqVo.setExternalOrderNo("CPORDERmO");
		reqVo.setNotifyUrl("www.baidu.com");
		reqVo.setPrivateInfo("i am privateInfo");
		reqVo.setVersionCode("3");
		
		reqVo.getData();
		reqVo.setSign("E1D0D1A774F36B6FA44557397B7EF634B445C81FEE787CAAA1A4C74FB452740E776F8A595D704F844372605F49AB9888A352AA79EFE53EE579BD3EB67C3B4C47");
		
		String json = JsonUtils.toJsonWithExpose(reqVo);
		
		System.out.println(json);
		
		
		TTransport transport = new TSocket("10.1.1.62", 7913);
		transport.open();
		
		//使用高密度二进制协议
		TProtocol protocol = new TCompactProtocol(transport);
		
		//创建Client
//		CommonServer.Client client = new CommonServer.Client(protocol);
		PayServer.Client client = new PayServer.Client(protocol);
		
		long start = System.currentTimeMillis();
		
		String str = client.pay(json);
		System.out.println(str);
		System.out.println("第"+s+"次耗时：" + (System.currentTimeMillis() - start)+"ms");
		
		transport.close();
		
	}

}
