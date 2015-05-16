package Test;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.FCI.SWE.Models.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

public class ChatTest {

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
			
	Chat chat = new Chat();
  @Test
  public void CreateChatGroup() {
    JSONArray senders = new JSONArray();
    senders.add("hosam");
    senders.add("khled");
    JSONArray ides = new JSONArray();
    ides.add("2");
    ides.add("3");
    
    Assert.assertEquals(chat.CreateChatGroup("testggg","hosam",senders.toJSONString(),ides.toJSONString())  , true);
  }

  @Test
  public void MsgChatGroup() {
   // throw new RuntimeException("Test not implemented");
	  Assert.assertEquals(chat.MsgChatGroup("2","khaled","let'd do it"),true);
  }
  
  @Test
  public void ViewChatGroup() throws ParseException {
   // throw new RuntimeException("Test not implemented");
	 // Assert.assertEquals(chat.ViewChatGroup("2"), true);
	  
  }

  @Test
  public void ViewMsgChatGroup() throws ParseException {
   // throw new RuntimeException("Test not implemented");
	  //Assert.assertEquals(chat.ViewMsgChatGroup("2"), true);
  }
}
