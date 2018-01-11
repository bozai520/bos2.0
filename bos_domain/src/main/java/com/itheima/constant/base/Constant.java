package com.itheima.constant.base;

public class Constant {
	public static final String BOS_MANAGEMENT_HOST = "http://localhost:9999";
	public static final String CRM_MANAGEMENT_HOST = "http://localhost:9998";
	public static final String BOS_FORE_HOST = "http://localhost:9997";
	public static final String BOS_EM_HOST = "http://localhost:9996";
	public static final String BOS_SMS_HOST = "http://localhost:9995";

	private static final String BOS_MANAGEMENT_CONTEXT = "/bos_management";
	private static final String CRM_MANAGEMENT_CONTEXT = "/crm_management";
	private static final String BOS_FORE_CONTEXT = "/bos_fore";
	private static final String BOS_EM_CONTEXT = "/bos_em";
	private static final String BOS_SMS_CONTEXT = "/bos_sms";

	public static final String BOS_MANAGEMENT_URL = BOS_MANAGEMENT_HOST + BOS_MANAGEMENT_CONTEXT;
	public static final String CRM_MANAGEMENT_URL = CRM_MANAGEMENT_HOST + CRM_MANAGEMENT_CONTEXT;
	public static final String BOS_FORE_URL = BOS_FORE_HOST + BOS_FORE_CONTEXT;
	public static final String BOS_EM_URL = BOS_EM_HOST + BOS_EM_CONTEXT;
	public static final String BOS_SMS_URL = BOS_SMS_HOST + BOS_SMS_CONTEXT;
}
