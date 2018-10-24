package com.logflow.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class MysqlSinkTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public MysqlSinkTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(MysqlSinkTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	public void testLogSubstring() {
		List<String> logArr = new ArrayList<String>();
		String log1 = "2018-10-09 10:30:42.747 DEBUG 26677 --- [io-18190-exec-4] c.s.o.t.h.m.H.queryHiddenDanger          : <==      Total: 7";
		logArr.add(log1);
		String log2 = "2018-10-08 15:51:04.831 DEBUG 25204 --- [io-18160-exec-6] o.s.web.servlet.DispatcherServlet        : Successfully completed request";
		logArr.add(log2);
		String log3 = "2018-10-08 11:31:13.919 TRACE 23797 --- [io-18130-exec-5] c.s.o.h.b.m.O.selectAll                  : <==        Row: , 0, 6810, null, null, 20094292, null, 20094292, 1, null, 发展策划部, null, 20094251, 1, 0, null, , 0, 发展策划部";
		logArr.add(log3);
		String log4 = "2018-10-09 14:16:58.461  INFO 28920 --- [io-18080-exec-9] com.sgit.ois.file.fastdfs.FastDFSUtil    : upload_file time used:41 ms";
		logArr.add(log4);
		String log5 = "2018-10-08 14:59:03.955 DEBUG 25204 --- [io-18160-exec-5] c.s.o.m.c.m.F.queryAllContract_COUNT     : ==>  Preparing: SELECT count(0) FROM (SELECT p.id AS id, p.contract_code AS contractCode, p.contract_name AS contractName, b.id AS businessOpportunityId, b.name AS businessOpportunityName, c.id AS customerId, c.name AS customerName, c.customer_type AS customerType, p.create_user AS createUser, p.contract_type AS contractType, p.contract_amount AS contractAmount, p.marketing_status AS marketingStatus, p.duty_status AS dutyStatus, p.marketer AS marketer, p.project_start_time AS projectStartTime, p.project_end_time AS projectEndTime, p.tax_rate AS taxRate, pac.packet_number AS packetNumber, pac.segment_name AS segmentName, bid.id AS biddingInformationId, bid.bidding_name AS biddingName, CONCAT_WS('-', pac.packet_number, pac.segment_name) AS tenderName, pac.id AS biddingPackageId, p.expected_contract_time AS expectedContractTime, p.formal_status AS formalStatus, p.implementation_department AS implementationDepartment, p.region, p.province, p.receipt_contract_time AS ReceiptContractTime, p.sales_order_number AS salesOrderNumber, p.contract_performance_time AS contractPerformanceTime, p.is_bid_winning_notice AS isBidWinningNotice, p.bid_notice_time AS bidNoticeTime, p.purchase_order_number AS purchaseOrderNumber, p.warranty_period AS warrantyPeriod, p.contract_year AS contractYear, p.contract_place AS contractPlace, p.project_type AS projectType, p.bid_customer_manager AS bidCustomerManager, p.contract_sign_person AS contractSignPerson, p.remarks, p.currency FROM OIS_MK.t_mk_prefabricatedcontract p LEFT JOIN OIS_MK.t_mk_business_oppor b ON p.businessopportunity_id = b.id LEFT JOIN OIS_MK.mk_customer c ON p.customer_id = c.id LEFT JOIN OIS_MK.T_MK_BIDDINGINFORMATION bid ON bid.id = p.biddinginformation_id LEFT JOIN OIS_MK.T_Mk_BIDDINGPACKAGE pac ON pac.id = p.biddingpackage_id WHERE is_formal = 1 AND (p.tenant_id = ? or p.tenant_id = '0')) table_count ";
		logArr.add(log5);
		String log6 = "2018-10-08 14:58:35.155 DEBUG 25204 --- [io-18160-exec-8] m.m.a.RequestResponseBodyMethodProcessor : Written [{\"successful\":true,\"data\":[{\"billNumber\":\"QZKH-20180928103614530\",\"customerCode\":null,\"industry\":\"02\",\"industryValue\":\"南方电网\",\"name\":\"自行车自产自销\",\"id\":\"fe1aefb3bd624da3b1556ac5fd83e0cf\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"1\",\"customerStateValue\":\"审批中\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"北京市\",\"provinceValue\":\"null\",\"city\":\"北京市市辖区\",\"cityValue\":null,\"region\":\"华北\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"0\",\"isInSystemValue\":\"系统内-集团内\"},{\"billNumber\":\"QZKH-20180928100016242\",\"customerCode\":\"\",\"industry\":\"03\",\"industryValue\":\"其他电网\",\"name\":\"测试CBH009\",\"id\":\"56552305ae234799a5e0cf265eed03e2\",\"customerClassification\":\"04\",\"customerClassificationValue\":\"社会团体\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"2\",\"customerStateValue\":\"待转正\",\"customerLevel\":\"02\",\"customerLevelValue\":\"重要客户\",\"organization\":null,\"vatNumber\":\"\",\"organizationCode\":\"\",\"scialCreditCode\":\"\",\"registrationNumber\":\"\",\"idNumber\":\"\",\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"天津市\",\"provinceValue\":\"null\",\"city\":\"天津市县\",\"cityValue\":null,\"region\":\"华北\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":\"\",\"remarks\":\"\",\"overseasCode\":\"\",\"bankAccount\":\"\",\"bankCode\":\"\",\"normalable\":null,\"normalableValue\":null,\"legalPerson\":\"\",\"companyWebsite\":\"\",\"fax\":\"\",\"fixPhone\":\"010-32123\",\"commercialCode\":\"\",\"isInSystem\":\"2\",\"isInSystemValue\":\"系统外-集团外\"},{\"billNumber\":\"QZKH-20180928095904406\",\"customerCode\":null,\"industry\":\"01\",\"industryValue\":\"国家电网\",\"name\":\"测试CBH008\",\"id\":\"887e7182e5294bddabc6304327402223\",\"customerClassification\":\"04\",\"customerClassificationValue\":\"社会团体\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"1\",\"customerStateValue\":\"审批中\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"天津市\",\"provinceValue\":\"null\",\"city\":\"天津市县\",\"cityValue\":null,\"region\":\"华北\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"0\",\"isInSystemValue\":\"系统内-集团内\"},{\"billNumber\":\"QZKH-20180928084743493\",\"customerCode\":null,\"industry\":\"08\",\"industryValue\":\"光伏发电\",\"name\":\"测试CBH007\",\"id\":\"1ac8d5d15b584a80b8611f5acfe03cc4\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"1\",\"customerStateValue\":\"审批中\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"湖南省\",\"provinceValue\":\"null\",\"city\":\"湖南省衡阳市\",\"cityValue\":null,\"region\":\"华中\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"},{\"billNumber\":\"QZKH-20180928083940689\",\"customerCode\":\"\",\"industry\":\"02\",\"industryValue\":\"南方电网\",\"name\":\"测试CBH006\",\"id\":\"d05645c358904a0f80479e18586adc05\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"2\",\"customerStateValue\":\"待转正\",\"customerLevel\":\"02\",\"customerLevelValue\":\"重要客户\",\"organization\":null,\"vatNumber\":\"\",\"organizationCode\":\"\",\"scialCreditCode\":\"\",\"registrationNumber\":\"\",\"idNumber\":\"\",\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"北京市\",\"provinceValue\":\"null\",\"city\":\"北京市市辖区\",\"cityValue\":null,\"region\":\"华北\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":\"\",\"remarks\":\"\",\"overseasCode\":\"\",\"bankAccount\":\"\",\"bankCode\":\"\",\"normalable\":null,\"normalableValue\":null,\"legalPerson\":\"\",\"companyWebsite\":\"\",\"fax\":\"\",\"fixPhone\":\"99999998\",\"commercialCode\":\"\",\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"},{\"billNumber\":\"QZKH-20180927160729103\",\"customerCode\":null,\"industry\":\"03\",\"industryValue\":\"其他电网\",\"name\":\"测试005\",\"id\":\"477ebcbc05e34a5a853809c2c2576be1\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"02\",\"customerTypeValue\":\"非公司制组织机构\",\"customerState\":\"0\",\"customerStateValue\":\"待提交\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"上海市\",\"provinceValue\":\"null\",\"city\":\"上海市市辖区\",\"cityValue\":null,\"region\":\"华东\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"},{\"billNumber\":\"QZKH-20180927153636469\",\"customerCode\":null,\"industry\":\"02\",\"industryValue\":\"南方电网\",\"name\":\"测试CBH003\",\"id\":\"096e464bb13a4648aaa63ff3b79f73e3\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"02\",\"customerTypeValue\":\"非公司制组织机构\",\"customerState\":\"0\",\"customerStateValue\":\"待提交\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"江苏省\",\"provinceValue\":\"null\",\"city\":\"江苏省无锡市\",\"cityValue\":null,\"region\":\"华东\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"},{\"billNumber\":\"QZKH-20180927152519227\",\"customerCode\":null,\"industry\":\"02\",\"industryValue\":\"南方电网\",\"name\":\"测试CBH002\",\"id\":\"9dccc904e084439e9056efb13ca8b500\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"1\",\"customerStateValue\":\"审批中\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"山西省\",\"provinceValue\":\"null\",\"city\":\"山西省大同市\",\"cityValue\":null,\"region\":\"华北\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"},{\"billNumber\":\"QZKH-20180927145113199\",\"customerCode\":null,\"industry\":\"01\",\"industryValue\":\"国家电网\",\"name\":\"测试CBH001\",\"id\":\"b6e6d14619684b4a9843193ec9b1a304\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"02\",\"customerTypeValue\":\"非公司制组织机构\",\"customerState\":\"0\",\"customerStateValue\":\"待提交\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"江苏省\",\"provinceValue\":\"null\",\"city\":\"江苏省无锡市\",\"cityValue\":null,\"region\":\"华东\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"0\",\"isInSystemValue\":\"系统内-集团内\"},{\"billNumber\":\"QZKH-20180927143630726\",\"customerCode\":null,\"industry\":\"01\",\"industryValue\":\"国家电网\",\"name\":\"HOIHLK\",\"id\":\"718add605b7d48c4b7b71dca6361621e\",\"customerClassification\":\"01\",\"customerClassificationValue\":\"政府部门\",\"customerType\":\"01\",\"customerTypeValue\":\"公司制法人单位\",\"customerState\":\"0\",\"customerStateValue\":\"待提交\",\"customerLevel\":null,\"customerLevelValue\":\"\",\"organization\":null,\"vatNumber\":null,\"organizationCode\":null,\"scialCreditCode\":null,\"registrationNumber\":null,\"idNumber\":null,\"nation\":\"中国\",\"nationValue\":\"null\",\"province\":\"安徽省\",\"provinceValue\":\"null\",\"city\":\"安徽省芜湖市\",\"cityValue\":null,\"region\":\"华东\",\"regionValue\":\"null\",\"positionType\":1,\"detailAddress\":null,\"remarks\":null,\"overseasCode\":null,\"bankAccount\":null,\"bankCode\":null,\"normalable\":null,\"normalableValue\":null,\"legalPerson\":null,\"companyWebsite\":null,\"fax\":null,\"fixPhone\":null,\"commercialCode\":null,\"isInSystem\":\"1\",\"isInSystemValue\":\"系统内-集团外\"}],\"page\":{\"pageNum\":1,\"pageSize\":10,\"total\":19,\"pages\":2,\"draw\":1},\"resultHint\":\"{\\\"message\\\":\\\"分页数据查询成功\\\"}\",\"errorPage\":\"\",\"draw\":1,\"recordsTotal\":19,\"recordsFiltered\":19,\"type\":\"info\"}] as \"application/json\" using [org.springframework.http.converter.json.MappingJackson2HttpMessageConverter@283bb8b7]\r\n";
		logArr.add(log6);
		String log7 = "2018-10-10 11:51:42.042 ERROR 123 --- [main] mk_logs.logs.Application :Info log [1].";
		logArr.add(log7);
		String log8 = "2018-10-09 10:30:42.747   DEBUG 26677 --- [          main] c.s.o.t.h.m.H.queryHiddenDanger          : <==      Total: 7";
		logArr.add(log8);
		try {
			for (String string : logArr) {
				proess(string);
				System.out.println();
			} 
		}catch (Exception ex) {
			ex.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
		
	}
	
	public void testHeader() {
		//  headers:{file=/root/logs/error.log, lineNumber=282}
		try {
			String pro = "/root/logs/error.log";
			System.out.println(pro.substring(pro.lastIndexOf("/") + 1, pro.lastIndexOf(".")));
		}catch (Exception ex) {
			ex.printStackTrace();
			assertTrue(false);
		}
		assertTrue(true);
	}
	
	public void proess(String log) throws Exception{
		try {
			long startTime = System.currentTimeMillis();
			String after = log.substring(0, log.indexOf("["));
			List<String> logs = new LinkedList<String>(Arrays.asList(after.split(" ")));
			ListIterator<String> iterator = logs.listIterator();
			while(iterator.hasNext()) {
				String str = iterator.next();
				if (str.trim().length() == 0) {
					iterator.remove();
				} else {
					iterator.set(str.trim());
				}
			}
			long endTime = System.currentTimeMillis();
			String date = logs.get(0);
			System.out.println(date);
			String time = logs.get(1);
			System.out.println(time);
			String type = logs.get(2);
			System.out.println(type);
			String thread = log.substring(log.indexOf("[") + 1, log.indexOf("]")).trim();
			System.out.println(thread);
			log = log.substring(log.indexOf("]") + 1);
			String pack = log.substring(0, log.indexOf(":")).trim();
			System.out.println(pack);
			String content = log.substring(log.indexOf(":") + 1, log.length()).trim();
			System.out.println(content);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
