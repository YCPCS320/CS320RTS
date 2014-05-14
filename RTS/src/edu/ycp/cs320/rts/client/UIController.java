package edu.ycp.cs320.rts.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.Window;

import edu.ycp.cs320.rts.shared.AttackRequest;
import edu.ycp.cs320.rts.shared.Attackable;
import edu.ycp.cs320.rts.shared.BuildRequest;
import edu.ycp.cs320.rts.shared.CanAttack;
import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.Movable;
import edu.ycp.cs320.rts.shared.MoveRequest;
import edu.ycp.cs320.rts.shared.Point;

public class UIController {
	
	private GameState state = new GameState(new ArrayList<GameObject>(), new TreeMap<String, Integer>());
	public GameState getState() {
		return state;
	}

	public void setState(GameState state) {
		this.state = state;
	}
	private GameObject selectedObject;
	private GameObject targetObject;
	private int ownerID;
	private ArrayList<BuildRequest> pendingbuilds;
	private ArrayList<MoveRequest> pendingmoves;
	private ArrayList<AttackRequest> pendingattacks;
	
	public UIController(){
		
		ownerID = Random.nextInt();
		setPendingattacks(new ArrayList<AttackRequest>());
		setPendingbuilds(new ArrayList<BuildRequest>());
		setPendingmoves(new ArrayList<MoveRequest>());
	}
	
	public void setGameList(ArrayList<GameObject> list){
		this.state.setGameobjects(list);
	}
	
	/**
	 * Sets the selected object if the point was on a GameObject.
	 * 
	 * @param Takes a point to check with.
	 * 
	 * @return Returns true if an object was selected.
	 */
	public boolean determineSelect(Point click){
		GWT.log("Left Click");
		for(GameObject obj: state.getGameobjects()){
			if(obj.checkBounds(click)){
				this.selectedObject=obj;
				GWT.log("Object id:" + selectedObject.getId() + " selected.");
				return true;
			}
		}
		pendingbuilds.add(new BuildRequest(ownerID, click));
		GWT.log(pendingbuilds.size()+"");
		return false;
	}
	/**
	 * 
	 * @param p the point to move all of the user's moveable objects to
	 */
	public void moveallunitsto(Point p) {
		GWT.log("this is a double");
		for(GameObject obj: state.getGameobjects()){
			if(obj instanceof Movable){
				if(obj.getOwner() == ownerID){
					pendingmoves.add(new MoveRequest(ownerID, obj.getId(), p));
				}
				
			}
		}
	}
	/**
	 * Issues a command based on the click.
	 * 
	 * @param Takes a point to check with.
	 * 
	 * @return Returns 1 if an object issued an attack order, 0 if an object was issued a move order, and -1 if no order was issued..
	 */
	public int determineAction(Point click){
		GWT.log("Right Click");
		if(this.selectedObject!=null){//make sure an object is selected
			for(GameObject obj: state.getGameobjects()){
				if(targetObject.checkBounds(click)){
					this.targetObject=obj;
					Window.alert("Object id:" + targetObject.getId() + " targeted by object id:" + selectedObject.getId() + ".");
				}
			}
			if(this.targetObject!=null){//if no target was made, target must be a point for move order	
					if(selectedObject instanceof Movable){
						//GIVE MOVE ORDER
						((Movable) selectedObject).addWaypoint(click);
						Window.alert("Move order received by object id:" + selectedObject.getId() + ".");
						return 0;
					}
					else{
						GWT.log("Click yielded no action; object id:" + selectedObject.getId() + " (" + selectedObject.getClass().getName() + ") cannot receive move orders.");
						return-1;
					}	
			}
			if(this.selectedObject.getOwner()!=this.targetObject.getOwner()){//if objects are not of the same owner
				if(targetObject instanceof Attackable&&this.selectedObject instanceof CanAttack){
					//GIVE ATTACK ORDER
					GWT.log("Attack order received by object id:" + selectedObject.getId() + ".");
					return 1;
				}
				else{
					GWT.log("Click yielded no action; either selected object id:" + selectedObject.getId() + " (" + selectedObject.getClass().getName() + ") cannot receive attack orders or target object id:" + targetObject.getId() + " (" + targetObject.getClass().getName() + ") cannot be attacked");
					return-1;
				}	
			}
		}
		GWT.log("Click yielded no action");
		return-1;
	}
	public void setOwnerID(int id){
		this.ownerID=id;
	}

	/**
	 * @return the pendingbuilds
	 */
	public ArrayList<BuildRequest> getPendingbuilds() {
		return pendingbuilds;
	}

	/**
	 * @param pendingbuilds the pendingbuilds to set
	 */
	public void setPendingbuilds(ArrayList<BuildRequest> pendingbuilds) {
		this.pendingbuilds = pendingbuilds;
	}

	/**
	 * @return the pendingmoves
	 */
	public ArrayList<MoveRequest> getPendingmoves() {
		return pendingmoves;
	}

	/**
	 * @param pendingmoves the pendingmoves to set
	 */
	public void setPendingmoves(ArrayList<MoveRequest> pendingmoves) {
		this.pendingmoves = pendingmoves;
	}

	/**
	 * @return the pendingattacks
	 */
	public ArrayList<AttackRequest> getPendingattacks() {
		return pendingattacks;
	}

	/**
	 * @param pendingattacks the pendingattacks to set
	 */
	public void setPendingattacks(ArrayList<AttackRequest> pendingattacks) {
		this.pendingattacks = pendingattacks;
	}
}
