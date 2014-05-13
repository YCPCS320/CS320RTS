/**
 * 
 */
package edu.ycp.cs320.rts.client;


import com.google.gwt.user.client.rpc.AsyncCallback;





import edu.ycp.cs320.rts.shared.GameState;


/**
 * @author Dan
 *
 * Mar 26, 2014
 */
public interface GetBoardServiceAsync {

	void exchangeGameState(GameState state, AsyncCallback<GameState> callback);

	void login(String username, String password, AsyncCallback<Integer> callback);

	void newuser(String username, String password, String email,
			AsyncCallback<Boolean> callback);
	
}
