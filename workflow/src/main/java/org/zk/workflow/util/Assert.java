package org.zk.workflow.util;

import org.springframework.dao.IncorrectResultSizeDataAccessException;

public class Assert extends org.springframework.util.Assert{
	
	public static void oneData(int actualSize) {
		int expectedSize = 1;
		if (actualSize != expectedSize) {
			throw new IncorrectResultSizeDataAccessException(expectedSize, actualSize);
		}
	}
}