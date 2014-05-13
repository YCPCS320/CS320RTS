package edu.ycp.cs320.rts.shared;

public class GameStateUpdater {

	static private long lastruntime;
	private static final float adjustment = (10);
	private GameState state;

	public GameStateUpdater(GameState state){

		this.state = state;
		lastruntime = 0;
	}

	public void updateState(){
		if(lastruntime == 0){
			lastruntime = System.currentTimeMillis();
		}
		long deltat = System.currentTimeMillis() - lastruntime;
		if(deltat == 0){
			deltat =1;
		}
		float timescale = deltat * adjustment;

		for(GameObject obj: state.getGameobjects()){
			if(obj instanceof Attackable){
				Attackable objcast = ((Attackable) obj);
			}
			if(obj instanceof CanAttack){
				CanAttack objcast = ((CanAttack) obj);
				if(objcast.canAttack(System.currentTimeMillis())){
					
				}
			}
			if(obj instanceof Movable){
				Movable objcast = ((Unit) obj);
				Point waypoint = objcast.getNextWaypoint();
				if(waypoint != null){


					Point currpos = obj.getPosition();
					if(waypoint.distTo(currpos) <= 10){

						objcast.removeWaypoint();
						if(objcast.getNextWaypoint() != null){
							waypoint = objcast.getNextWaypoint();
						}


					}

					
					if(waypoint != null){
						Point diff = new Point(waypoint.getX() - currpos.getX(), waypoint.getY() - currpos.getY());


						//gets scaled movement
						float dx = (timescale*diff.getX());
						float dy =  (timescale*diff.getY());
						float totmove = Math.abs(dx)+Math.abs(dy);





						// moves with speed as a constant
						dx = (objcast.getSpeed()*(dx/totmove))*10;
						dy = (objcast.getSpeed()*(dy/totmove))*10;
						obj.getPosition().add(new Point((int)dx, (int)dy));
					}
				}

			}
			if(obj instanceof CanTransport){
				CanTransport objcast = ((CanTransport) obj);
			}
		}


		lastruntime = System.currentTimeMillis();
	}

}
