package Test;

import java.util.*;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.Page;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class pageTest {
	
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

  Page p = new Page();	
  @Test
  public void LikePost() throws ParseException {
   // throw new RuntimeException("Test not implemented");
	Assert.assertEquals(p.LikePost("2", "3"),false);  
  }
  
  @Test
  public void PageSearch() {
    //throw new RuntimeException("Test not implemented");
	//Assert.assertEquals(p.PageSearch("yla n2fl", "study"),"");  
  }

  @Test
  public void ViewPosts() {
    //throw new RuntimeException("Test not implemented");
	 ArrayList arr = new ArrayList();
	 
		Assert.assertEquals(p.ViewPosts("2"),arr);  
  }

  @Test
  public void gethashes() {
    //throw new RuntimeException("Test not implemented");
	  ArrayList list = new ArrayList();
	  list.add("@sw");
	  Assert.assertEquals(p.gethashes(" ay hry @sw loool :V "),list); 
  }

  @Test
  public void newpage() {
    //throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(p.newpage("hosam", "yla n2fl","study"),"done");  
  }

  @Test
  public void newpagepost() {
   // throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(p.newpagepost("2", "2", "khaled", "happy", "goooooooooooooool"),"page"); 
  }
}