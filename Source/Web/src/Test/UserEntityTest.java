package Test;
import org.testng.*;
//import org.testng.Assert;
import org.testng.annotations.*;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;*/

import com.FCI.SWE.Models.UserEntity;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import java.util.*;

public class UserEntityTest {
	 UserEntity user = new UserEntity();
	 UserEntity user2 ;
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

			
  			
  @Test
  public void acceptRequset() {
  //throw new RuntimeException("Test not implemented");
  Assert.assertEquals(user.acceptRequset("2", "3"),null);
  }


  @Test
  public void saveUser() {
    //throw new RuntimeException("Test not implemented");
	  user2 = new UserEntity("khaled", "khaled","123","2");
	  boolean check = user2.saveUser();
      Assert.assertEquals(check, true);
  }
 
  
  @Test
  public void getUser() {
   // throw new RuntimeException("Test not implemented");
	 //user2 = new UserEntity("khaled", "khaled","123");
	  boolean flage=false;
	  if(user.Login("khaled", "123") == user2){
		  flage =true;
	  }
	  Assert.assertEquals(flage,true);
	 
	  
  }

  @Test
  public void saveRequset() {
    //throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(user.saveRequset("khaled", "hosam" , "3", "4", ""),0);
  }

  @Test
  public void searchforuser() {
    //throw new RuntimeException("Test not implemented");
	 boolean flage=false;
	 ArrayList<UserEntity> arr = new ArrayList();
	 user2 = new UserEntity("hosam", "hosam","123","3");
	 System.out.println( user.searchforuser("khaled").get(0));
	 arr.add(user2);
	  if( arr  == user.searchforuser("khaled") ){
		  
		  flage =true;
	  }
	  Assert.assertEquals(flage,false);
	  
  }

  @Test
  public void viewRequset() {
    //throw new RuntimeException("Test not implemented");
	  boolean flage=false;

	  ArrayList<UserEntity> arr = new ArrayList();
	  user2.viewRequset("2");
	  arr.add(user2);
if( arr  == user.searchforuser("khaled") ){
		  
		  flage =true;
	  }
Assert.assertEquals(flage,false);
  }
}
