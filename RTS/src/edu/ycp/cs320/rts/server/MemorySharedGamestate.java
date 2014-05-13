/**
 * 
 */
package edu.ycp.cs320.rts.server;

import edu.ycp.cs320.rts.shared.BuildRequest;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.Point;

/**
 * @author dan
 *
 * Apr 7, 2014
 */
public class MemorySharedGamestate implements ISharedGamestate{
	
	private GameState sharedgamestate;
	public MemorySharedGamestate(){
		sharedgamestate = new GameState();
/*		Combatant testcom = new Combatant();
		testcom.setPosition(new Point(10, 20));
		testcom.addWaypoint(new Point(50, 50));
		testcom.addWaypoint(new Point(5, 5));
		testcom.addWaypoint(new Point(200, 200));
		testcom.addWaypoint(new Point(0, 200));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		testcom.addWaypoint(new Point(500, 500));
		testcom.addWaypoint(new Point(500, 200));
		
		testcom.setImageName("combatantSprite.png");
		sharedgamestate.getGameobjects().add(testcom);*/
		
		sharedgamestate.addBuildRequest(new BuildRequest(2, new Point(50, 50)));
		
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
