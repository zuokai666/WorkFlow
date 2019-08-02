package com.accounting.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.accounting.bean.BizResponse;
import com.accounting.service.AccountingServiceImpl;

@RestController
@RequestMapping("/repay")
public class RepayController {
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value="/repayAction",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BizResponse repayAction(
			@RequestParam("loanId") int loanId,
			@RequestParam("repaymode") String repaymode){
		Integer accountId = (Integer) request.getSession().getAttribute("accoundId");
		if(accountId == null){
			BizResponse.Builder builder = new BizResponse.Builder();
			return builder.fail().tip("账户未登录，还款失败").build();
		}
		AccountingServiceImpl accountingService = new AccountingServiceImpl();
		Map<String, Object> map = new HashMap<>();
		map.put("loanId", loanId);
		map.put("repaymode", repaymode);
		return accountingService.repay(map);
	}
}