package edu.ycp.cs320.rts.server;



import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import edu.ycp.cs320.rts.client.GetBoardService;
import edu.ycp.cs320.rts.server.control.AddUser;
import edu.ycp.cs320.rts.server.control.ClientChannel;
import edu.ycp.cs320.rts.server.control.GameStateManager;
import edu.ycp.cs320.rts.server.control.GetGamestate;
import edu.ycp.cs320.rts.server.control.VerifyLogin;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.UserData;
import edu.ycp.cs320.server.persist.BCrypt;
@SuppressWarnings("serial")
public class GetBoardServiceImpl extends RemoteServiceServlet implements GetBoardService {
	
	private static GameStateManager manage;
	
	
	 public void init(ServletConfig config) throws ServletException {
		 super.init(config);
		 
		 
		 GameState state = new GetGamestate().getGameState();
		 manage = new GameStateManager(state);
		 System.out.println("Created GameStateManager");
		 
		manage.start();
		 
	 }
	 
	public GameState exchangeGameState(GameState state){
		
		ClientChannel channel;
		channel = (ClientChannel) getThreadLocalRequest().getSession().getAttribute("clientChannel");
		if (channel == null) {
			channel = manage.connect();
			getThreadLocalRequest().setAttribute("clientChannel", channel);
				
		}
		
		String username = (String)getThreadLocalRequest().getSession().getAttribute("username");
		if(username == null){
			//this should return null when login features are complete 
			System.out.println("There is a user trying to exchange gamestates without being logged in");
		}		
		else{
			//System.out.printf("%s \n \n \n",  username);

		}
		if(state != null){
			
		}
		else{
			System.out.println("The game state is null");
		}
		
		
		state = channel.update(state);
		if (state == null) {
			System.out.println("Client channel update returned null?");
		}
		
		return state;
	}

	public Integer login(String username, String password) {
		VerifyLogin logi = new VerifyLogin();
		String user = logi.verifyLogin(username, password);
		if(user == null){
			return 0;
			
		}
		getThreadLocalRequest().getSession().setAttribute("username", user);
		return manage.getnumclients()+1;
	}

	public Boolean newuser(String username, String password, String email) {
		AddUser createuser = new AddUser();
		UserData user = new UserData(username, email, BCrypt.hashpw(password, BCrypt.gensalt()));
		return createuser.addUser(user);
	}
}
