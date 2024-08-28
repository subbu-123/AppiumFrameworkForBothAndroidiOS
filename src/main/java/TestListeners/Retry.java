package TestListeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	private int count = 1;
	private static final int retryLimit = 1;

	@Override
	public boolean retry(ITestResult result) {

		if(!result.isSuccess())
		{
			if(this.count<=retryLimit)
			{
				this.count++;
				return true;
			}
		}
		
		return false;
	}

	
	
}
