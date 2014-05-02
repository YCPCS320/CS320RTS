package edu.ycp.cs320.rts.server.control;

import java.util.ArrayList;

import edu.ycp.cs320.rts.shared.GameState;

public class HandleGameRequests {
	
	/**
	 * This function is responsible for merging changes between Gamestates
	 * 
	 * The masterGamestate's GameObjects will be correct
	 * 
	 * The slaveGamestates will make contain requests that are checked for validity and then translated to GameObjects and then 
	 * placed into the masterGamestate.
	 * 
	 * The GameObjects of the slave gamestates will be ignored, only the requests will cause changes to the mastergamestate
	 * 
	 * 
	 * 
	 * @param masterGamestate the GameState that will not have any requests, and will store all of the GameObjects 
	 * @param slaveGamestates the GameStates 
	 * @return the masterGamestate with the changes requested by the slaveGamestates
	 */
	GameState handleGameRequests(GameState masterGamestate, ArrayList<GameState> slaveGamestates){
		
		return masterGamestate;
	}

}
