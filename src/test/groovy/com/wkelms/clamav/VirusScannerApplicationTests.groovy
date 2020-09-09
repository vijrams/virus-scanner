package com.wkelms.clamav


import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner.class)
class VirusScannerApplicationTests {

	@Test
	void firstTest() {
		ClamavClient client = new ClamavClient("localhost", 3310)
		File f = new File("c:/temp/eicar_com.zip")
		//File f = new File("c:/temp/passport.zip")
		println "***********"+  client.scan(new FileInputStream(f)).foundViruses
	}

}
