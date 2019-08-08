package com.accounting.component.repay;

import java.util.Map;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.accounting.util.Constant;

public class AdaptiveRepayProcedure {
	
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(AdaptiveRepayProcedure.class);
	
	public void run(Session session,Map<String, Object> map){
		String repaymode = (String) map.get("repaymode");
		if(repaymode.equals(Constant.repaymode_dqhk)){
			DQHKProcedure dqhkProcedure = new DQHKProcedure();
			dqhkProcedure.run(session, map);
		}else if(repaymode.equals(Constant.repaymode_tqjqCur)){
			TQJQCurrentProcedure tqjqCurrentProcedure = new TQJQCurrentProcedure();
			tqjqCurrentProcedure.run(session, map);
		}else if(repaymode.equals(Constant.repaymode_tqjqAll)){
			TQJQAllProcedure tqjqAllProcedure = new TQJQAllProcedure();
			tqjqAllProcedure.run(session, map);
		}else if(repaymode.equals(Constant.repaymode_yqhk)){
			YQHKProcedure yqhkProcedure = new YQHKProcedure();
			yqhkProcedure.run(session, map);
		}else {
			throw new UnsupportedOperationException("不支持其它还款方式");
		}
	}
}