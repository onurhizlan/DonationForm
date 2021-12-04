package suites;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import base.Base;

public class DonationSuite extends Base {

	
	@Test
	@Parameters({ "environment", "amount", "name", "lastname", "email"})
	public void greenTest(String environment, String amount, String name, String lastname, String email) throws Exception{		
			
			driver.navigate().to(environment);
			dp.makeSuccessfulDonation(amount, name, lastname, email);
			
	
	}
	
	@Test
	@Parameters({ "environment2", "amount", "name", "lastname", "email"})
	public void redTest(String environment, String amount, String name, String lastname, String email) throws Exception{		
			
			driver.navigate().to(environment);
			dp.makeSuccessfulDonation(amount, name, lastname, email);
			
	
	}
	
}
