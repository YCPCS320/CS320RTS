package edu.ycp.cs320.rts.server.control;

import java.util.ArrayList;

import edu.ycp.cs320.rts.shared.AttackRequest;
import edu.ycp.cs320.rts.shared.BuildRequest;
import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.GameState;
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
						masterGamestate.addMoveRequest(mr);		// Add request to master
						//((Unit) o).addWaypoint(newWaypoint);	// Make changes directly
					}	
				}
			}
			
			ArrayList<BuildRequest> buildRequests = slaveGamestates.get(i).getBuildRequests();
			for (BuildRequest br: buildRequests) {
				Structure structureToBuild = new Structure();
				boolean validPos = true;
				// Check if build location is valid in masterGamestate - no gameobject or resource at location
				for (GameObject o: masterGamestate.getGameobjects()) {
					if (o.getPosition().getX() == br.getBuildpoint().getX() && o.getPosition().getY() == br.getBuildpoint().getY()) {
						validPos = false;	// If a gameObject is encountered at build location, request can not be fullfilled
					}
				}
				if (validPos) {
					masterGamestate.addBuildRequest(br);	// Add request to master
					//masterGamestate.getGameobjects().add(structureToBuild);	// Make changes directly
				}
			}
			
			ArrayList<AttackRequest> attackRequests = slaveGamestates.get(i).getAttackRequests();
			ArrayList<AttackRequest> validAttackReq = new ArrayList<AttackRequest>();
			for (AttackRequest ar: attackRequests) {
				// Check if attacking unit and attacked unit both exist in masterGamestate
				// if so, update the master by completing the attack
				boolean a1 = false;
				boolean a2 = false;
				for (GameObject o: masterGamestate.getGameobjects()) {
					if (o.getId() == ar.getSourceUnit()) {
						a1 = true;
					}
					if (o.getId() == ar.getTargetUnit()) {
						a2 = true;
					}
				}
				if (a1 && a2) {
					validAttackReq.add(ar);
				}
			}
			masterGamestate.addAttackRequests(validAttackReq); 	// Add requests to master
		}
		
		
		return masterGamestate;
	}

}
