/**
 * 
 */
package edu.ycp.cs320.rts.server;

import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.Point;
import edu.ycp.cs320.rts.shared.Structure;

/**
 * @author dan
 *
 * Apr 7, 2014
 */
public class MemorySharedGamestate implements ISharedGamestate{
	
	private GameState sharedgamestate;
	public MemorySharedGamestate(){
		sharedgamestate = new GameState();
		
	}
	
	public GameState getSharedgamestate() {
		return sharedgamestate;
	}
	
	public void setSharedgamestate(GameState sharedgamestate) {
		this.sharedgamestate = sharedgamestate;
	}
	
	public void addRequests(GameState usergamestate) {
		sharedgamestate.addBuildRequests(usergamestate.getBuildRequests());
		sharedgamestate.addMoveRequests(usergamestate.getMoveRequests());
		sharedgamestate.addAttackRequests(usergamestate.getAttackRequests());
	}
	
}
