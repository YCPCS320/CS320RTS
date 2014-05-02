package edu.ycp.cs320.rts.client;

import java.util.ArrayList;
import java.util.TreeMap;

import com.google.gwt.core.shared.GWT;

import edu.ycp.cs320.rts.shared.Attackable;
import edu.ycp.cs320.rts.shared.CanAttack;
import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.Movable;
import edu.ycp.cs320.rts.shared.Point;

public class UIController {
	
	private GameState state = new GameState(new ArrayList<GameObject>(), new TreeMap<String, Integer>());
	private GameObject selectedObject;
	private GameObject targetObject;
	private int ownerID;
	
	public UIController(){
		
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
		GWT.log("Click yielded no action");
		return false;
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
					GWT.log("Object id:" + targetObject.getId() + " targeted by object id:" + selectedObject.getId() + ".");
				}
			}
			if(this.targetObject!=null){//if no target was made, target must be a point for move order	
					if(selectedObject instanceof Movable){
						//GIVE MOVE ORDER
						((Movable) selectedObject).addWaypoint(click);
						GWT.log("Move order received by object id:" + selectedObject.getId() + ".");
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
}
