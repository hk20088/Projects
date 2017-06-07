package com.newspace.aps.model.order;

import java.util.List;

import com.google.gson.annotations.Expose;

/**
 * @description 支付响应实体类
 * @author huqili
 *
 */
public class PayRespVo {

	@Expose
	private String Data; // 原始数据，json串

	@Expose
	private String Sign; // 加密串

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getSign() {
		return Sign;
	}

	public void setSign(String sign) {
		Sign = sign;
	}

	/**
	 * @description 响应实体类
	 * @author huqili
	 *
	 */
	public static class PayRespData {
		private String OrderNo;
		private Integer Code;
		private String Desc;
		private String Status;  //支付状态 SUCCESS-支付成功； WAIT-等待客户端处理
		private List<ResultBody> resultBody;

		public String getStatus() {
			return Status;
		}

		public void setStatus(String status) {
			Status = status;
		}

		public String getOrderNo() {
			return OrderNo;
		}

		public void setOrderNo(String orderNo) {
			OrderNo = orderNo;
		}

		public Integer getCode() {
			return Code;
		}

		public void setCode(Integer code) {
			Code = code;
		}

		public String getDesc() {
			return Desc;
		}

		public void setDesc(String desc) {
			Desc = desc;
		}

		public List<ResultBody> getResultBody() {
			return resultBody;
		}

		public void setResultBody(List<ResultBody> resultBody) {
			this.resultBody = resultBody;
		}

		/**
		 * 静态内部类，用来封装响应给客户端的具体内容
		 * @author huqili
		 *
		 */
		public static class ResultBody {
			
			/**
			 * 逻辑处理标识 
			 * ATET – 采用ATET A豆支付方式
			 *  CTC - Call TPP Client 调用第三方客户端 
			 *  SPQ - Scan Pay QR Code 二维码支付 
			 *  CTS - Call Third SDK 调用第三方SDK 
			 *  MI - Missing Information 信息不足
			 *  
			 *  以下 2016-12-2 新增 @author 唐盼
			 *  IOM - Infomation Of Merchandise 商品信息
			 *  EXI - External Infomation  附加信息
			 */
			private String PayType;
			private String PayParam; // 对应的具体详细参数信息

			public String getPayType() {
				return PayType;
			}

			public void setPayType(String payType) {
				PayType = payType;
			}

			public String getPayParam() {
				return PayParam;
			}

			public void setPayParam(String payParam) {
				PayParam = payParam;
			}

		}

	}
}
