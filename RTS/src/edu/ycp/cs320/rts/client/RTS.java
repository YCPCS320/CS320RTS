package edu.ycp.cs320.rts.client;

import java.util.ArrayList;
import java.util.TreeMap;

import edu.ycp.cs320.rts.shared.BuildRequest;
import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.GameState;
import edu.ycp.cs320.rts.shared.GameStateUpdater;
import edu.ycp.cs320.rts.shared.Point;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootLayoutPanel;

/**
 * 
 * 
 *
 *
 */
public class RTS {

	private Image combatantSprite;
	private Image unitSprite;
	private Image structureSprite;
	private Image turretSprite;
	private GameView view;
	private GameState state;
	private GameStateUpdater smoother;
	private int playerid;
	/**
	 * 
	 */
	public RTS(int playeridfromserv){
		playerid = playeridfromserv;
		// load sprites
		String combatantSpriteUrl = GWT.getModuleBaseForStaticFiles()
				+ "combatantSprite.png";
		String unitSpriteUrl = GWT.getModuleBaseForStaticFiles()
				+ "unitSprite.png";
		String structureSpriteUrl = GWT.getModuleBaseForStaticFiles()
				+ "structureSprite.png";
		String turretSpriteUrl = GWT.getModuleBaseForStaticFiles()
				+ "turretSprite.png";
		GWT.log("Combatant sprite: " + combatantSpriteUrl);
		GWT.log("Unit sprite: " + unitSpriteUrl);
		GWT.log("Structure sprite: " + structureSpriteUrl);
		GWT.log("Turret sprite: " + turretSprite);
		combatantSprite = new Image(combatantSpriteUrl);
		unitSprite = new Image(unitSpriteUrl);
		structureSprite = new Image(structureSpriteUrl);
		turretSprite = new Image(turretSpriteUrl);

		// Generate a new game view

		
		state = new GameState(new ArrayList<GameObject>(), new TreeMap<String, Integer>());
		smoother = new GameStateUpdater(state);
		view = new GameView();
		view.setState(state);
		view.setGameList(state.getGameobjects());

		// more stuff
		FlowPanel imagePanel = new FlowPanel();
		imagePanel.setSize("0px", "0px");
		RootLayoutPanel.get().add(imagePanel);

		// add the view
		RootLayoutPanel.get().add(view);

		view.setUnitSprite(unitSprite);
		view.setCombatantSprite(combatantSprite);
		view.setStructureSprite(structureSprite);
		view.setTurretSprite(turretSprite);

		//start getting game states
		updateGameState();


		GetBoardServiceAsync boardservice = (GetBoardServiceAsync) GWT.create(GetBoardService.class);

		AsyncCallback<GameState> callback = new AsyncCallback<GameState>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("failed "+ caught.getMessage());

			}

			@Override
			public void onSuccess(GameState result) {
				//GWT.log("Success");
				GameState newstate =(GameState) result;
				view.setGameList(newstate.getGameobjects());

			}
		};


		boardservice.exchangeGameState(state, callback);
		view.activate();
		
		 Timer t = new Timer() {
		      @Override
		      public void run() {
		    	  smoother.updateState();
		      }
		    };
		    
		    //t.scheduleRepeating(100);



	}

	/**
	 * 
	 */
	public void updateGameState(){
		GetBoardServiceAsync boardservice = (GetBoardServiceAsync) GWT.create(GetBoardService.class);
		//GWT.log("sent");

		AsyncCallback<GameState> callback = new AsyncCallback<GameState>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("failed "+ caught.getMessage());
				updateGameState();

			}

			@Override
			public void onSuccess(GameState result) {
				//GWT.log("Success");
				GameState newstate =(GameState) result;
				view.setGameList(newstate.getGameobjects());
				state = result;
				view.setState(state);
				//loads pending requests to gamestate before sending them over the network
				state.getBuildRequests().addAll(view.getuIController().getPendingbuilds());
				view.getuIController().getPendingbuilds().clear();
				state.getMoveRequests().addAll(view.getuIController().getPendingmoves());
				view.getuIController().getPendingmoves().clear();
				state.getAttackRequests().addAll(view.getuIController().getPendingattacks());
				view.getuIController().getPendingattacks().clear();
				updateGameState();
				for(GameObject g: state.getGameobjects()){
					/*
					GWT.log("x:" + g.getPosition().getX());
					GWT.log("y:" + g.getPosition().getY());
					*/
				}
				

			}
		};

		//state.addBuildRequest(new BuildRequest(playerid, new Point(200, 200)));
		boardservice.exchangeGameState(state, callback);
		
	   

	}

}
