/**
 * 
 */
package edu.ycp.cs320.rts.shared;

import java.io.Serializable;

/**
 * @author dan
 *
 * Apr 2, 2014
 */
@SuppressWarnings("serial")
public class MoveRequest extends Request implements Serializable{
	private int unitId;
	private Point newWaypoint;
	
	/**
	 * 
	 * @param user
	 * @param target
	 * @param newPoint
	 */
	public MoveRequest(int user, int target, Point newPoint){
		super(user);
		unitId = target;
		newWaypoint = newPoint;
	}
	
	public MoveRequest(){
		super();
		unitId = 0;
		newWaypoint = new Point();
	}
	/**
	 * @return the unitId
	 */
	public int getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(int unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the newWaypoint
	 */
	public Point getNewWaypoint() {
		return newWaypoint;
	}
	/**
	 * @param newWaypoint the newWaypoint to set
	 */
	public void setNewWaypoint(Point newWaypoint) {
		this.newWaypoint = newWaypoint;
	}
	
	
	

}
