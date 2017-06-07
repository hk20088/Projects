package com.newspace.aps.model;

/**
 * @description 渠道所支持的支付方式，及支付方式优先级
 * @author huqili
 * @date 2016年9月16日
 *
 */
public class ChanPayType {

	private String PayCode; // 支付方式代码，例COIN代表A豆
	private Integer Priority; // 优先级，例如 0，1 数字越小优先级越高
	private String NextStep; // 下一步是否继续扣费
	
	public static final String NEXTSTEP_CONTINUE = "C";
	public static final String NEXTSTEP_NOT = "N";

	public String getPayCode() {
		return PayCode;
	}

	public void setPayCode(String payCode) {
		PayCode = payCode;
	}

	public Integer getPriority() {
		return Priority;
	}

	public void setPriority(Integer priority) {
		Priority = priority;
	}

	public String getNextStep() {
		return NextStep;
	}

	public void setNextStep(String nextStep) {
		NextStep = nextStep;
	}

}
