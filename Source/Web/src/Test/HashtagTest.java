package Test;

import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.Hashtag;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class HashtagTest {
	private final LocalServiceTestHelper helper1 =
			new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

			@BeforeClass
			public void setUp() {
			helper1.setUp();
			}

			@AfterClass
			public void tearDown() {
			helper1.tearDown();
			}

	Hashtag hash = new Hashtag();
	
  @Test
  /* data base problem*/
  public void AddHashtag() {
	  
	  Assert.assertEquals(hash.AddHashtag("fff","5"),true); // hash name post id
  }

  @Test
  public void Viewhashtags() {
    //throw new RuntimeException("Test not implemented");
  }
}
