package edu.ycp.cs320.rts.server.control;

import java.util.ArrayList;

import edu.ycp.cs320.rts.shared.AttackRequest;
import edu.ycp.cs320.rts.shared.BuildRequest;
import edu.ycp.cs320.rts.shared.Combatant;
import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.Interactable;
import edu.ycp.cs320.rts.shared.MoveRequest;
import edu.ycp.cs320.rts.shared.Point;
import edu.ycp.cs320.rts.shared.Structure;
import edu.ycp.cs320.rts.shared.Unit;

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
		
		for (int i=0; i<slaveGamestates.size(); i++) {
			
			ArrayList<MoveRequest> moveRequests = slaveGamestates.get(i).getMoveRequests();
			for (MoveRequest mr: moveRequests) {
				int id = mr.getUnitId();
				Point newWaypoint = mr.getNewWaypoint();
				for (GameObject o: masterGamestate.getGameobjects()) {
					// Check if unitId exists on masterGamestate, if so add the way-point to it in master
					if (o.getId() == id) {
						((Unit) o).addWaypoint(newWaypoint);	// Make changes directly
					}	
				}
			}
			
			ArrayList<BuildRequest> buildRequests = slaveGamestates.get(i).getBuildRequests();
			for (BuildRequest br: buildRequests) {
				boolean validPos = true;
				// Check if build location is valid in masterGamestate - no gameobject or resource at location
				for (GameObject o: masterGamestate.getGameobjects()) {
					if (o.getPosition().getX() == br.getBuildpoint().getX() && o.getPosition().getY() == br.getBuildpoint().getY()) {
						validPos = false;	// If a gameObject is encountered at build location, request can not be fulfilled
					}
				}
				if (validPos) {
					
					int Userid = br.getUserId();
					Point buildPoint = br.getBuildpoint();
					int size = masterGamestate.getGameobjects().size();		// Get a new unique id
					int id = size;
					Combatant combatantToBuild = new Combatant(id, Userid, new Point(30, 30), buildPoint, 1,1,1,1,1,1);
					combatantToBuild.setImageName("combatantSprite.png");
					masterGamestate.getGameobjects().add(combatantToBuild);	// Make changes directly
					

				}
			}
			
			ArrayList<AttackRequest> attackRequests = slaveGamestates.get(i).getAttackRequests();
			for (AttackRequest ar: attackRequests) {
				// Check if attacking unit and attacked unit both exist in masterGamestate
				// if so, update the master by completing the attack
				boolean a1 = false;
				boolean a2 = false;
				Combatant sourceUnit = new Combatant();
				Interactable target = new Interactable();
				for (GameObject o: masterGamestate.getGameobjects()) {
					if (o.getId() == ar.getSourceUnit()) {
						a1 = true;
						sourceUnit = ((Combatant) o);
					}
					if (o.getId() == ar.getTargetUnit()) {
						a2 = true;
						target = ((Interactable) o);
					}
				}
				if (a1 && a2) {
					long currenttime = System.currentTimeMillis();
					sourceUnit.attack(target, currenttime);	// Issue the attack
					for (GameObject o: masterGamestate.getGameobjects()) {
						if (o.getId() == ar.getTargetUnit()) {	// Update the target unit in the masterGamestate
							o = target;
						}
					}
				}
			}
		}
		
		
		return masterGamestate;
	}

}
