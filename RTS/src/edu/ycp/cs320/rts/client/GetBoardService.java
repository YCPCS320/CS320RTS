/**
 * 
 */
package edu.ycp.cs320.rts.client;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import edu.ycp.cs320.rts.shared.*;

/**
 * @author Dan
 *
 * Mar 26, 2014
 */
@RemoteServiceRelativePath("board")
public interface GetBoardService extends RemoteService {
	public GameState exchangeGameState(GameState state);
	
	Integer login(String username, String password);
	Boolean newuser(String username, String password, String email);
	
}
