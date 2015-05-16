package Test;

import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.Post;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class PostTest {

	
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
			
	Post p=new Post();
  @Test
  public void LikePost() throws ParseException {
   // throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(p.LikePost("2", "3"),false);  
  }

  @Test
  public void ViewPosts() {
   // throw new RuntimeException("Test not implemented");
	  ArrayList arr = new ArrayList();
	  Assert.assertEquals(p.ViewPosts("2"),arr);  
  }

  @Test
  public void newpost() {
   // throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(p.newpost("2", "hosam", "happy", "new post ", ""),"post");  
  }
}
