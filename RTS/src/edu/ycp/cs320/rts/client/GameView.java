package edu.ycp.cs320.rts.client;

import java.util.ArrayList;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.CanvasElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Image;

import edu.ycp.cs320.rts.shared.GameObject;
import edu.ycp.cs320.rts.shared.Point;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

/**
 * View for RTS model class.
 */
public class GameView extends Composite {
	private static int WIDTH = 640;
	private static int HEIGHT = 480;
	private Canvas buffer;
	private Context2d bufCtx;
	private Canvas canvas;
	private Context2d ctx;
	private Timer timer;
	private Image sprite;
	private Image combatantSprite;
	private Image unitSprite;
	private Image structureSprite;
	private Image turretSprite;
	private ArrayList<GameObject> list;
	private Point mouseStart;
	private UIController uIController; 
	private int ownerID;

	/**
	 * Constructor.
	 */
	public GameView() {
		
		
		uIController = new UIController();
		
		
		// A canvas needs to be in a FocusPanel if it will handle keyboard input.
		FocusPanel panel = new FocusPanel();
		panel.setSize(WIDTH + "px", HEIGHT + "px");

		// We use the "buffer" canvas as an off-screen drawing surface where
		// each frame is rendered.
		this.buffer = Canvas.createIfSupported();
		buffer.setSize(WIDTH + "px", HEIGHT + "px");
		buffer.setCoordinateSpaceWidth(WIDTH);
		buffer.setCoordinateSpaceHeight(HEIGHT);
		this.bufCtx = buffer.getContext2d();

		// The visible canvas: contents of buffer are copied here once
		// a frame has been rendered.
		this.canvas = Canvas.createIfSupported();
		canvas.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				handleMouseClick(event);
			}
		});
		canvas.setSize(WIDTH + "px", HEIGHT + "px");
		canvas.setCoordinateSpaceWidth(WIDTH);
		canvas.setCoordinateSpaceHeight(HEIGHT);
		this.ctx = canvas.getContext2d();
		panel.add(canvas);

		initWidget(panel);
		
		
		
		// Animation timer
		this.timer = new Timer() {
			@Override
			public void run() {
				if (list!=null) {
					paint(list);
				}
			}
		};
	}
	
	private void handleMouseClick(ClickEvent event) {
		mouseStart = new Point();
		mouseStart.setX(event.getX());
		mouseStart.setY(event.getY());
		NativeEvent nativeEvent = event.getNativeEvent();
		this.uIController.setGameList(list);
		if(nativeEvent.getButton()==NativeEvent.BUTTON_LEFT){
			this.uIController.determineSelect(mouseStart);		
		}
		if(nativeEvent.getButton()==NativeEvent.BUTTON_RIGHT){
			this.uIController.determineAction(mouseStart);
				
		}
		
		
		
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	public void setCombatantSprite(Image sprite) {
		this.combatantSprite = sprite;
	}
	public void setUnitSprite(Image sprite) {
		this.unitSprite = sprite;
	}
	public void setStructureSprite(Image sprite) {
		this.structureSprite = sprite;
	}
	public void setTurretSprite(Image sprite) {
		this.turretSprite = sprite;
	}

	public void setGameList(ArrayList<GameObject> list){
		this.list = list;
	}
	
	public void setOwnerID(int id){
		this.ownerID=id;
	}
	
	
	/**
	 * Start the animation timer.
	 */
	public void activate() {
		timer.scheduleRepeating(1000 / 30);
	}

	// Render one frame of animation.
	protected void paint(ArrayList<GameObject> list) {
	// Draw onto buffer

		// Draw background
		bufCtx.setFillStyle("white");
		bufCtx.fillRect(0, 0, WIDTH, HEIGHT);

		// Draw game objects
		for(int i=0; i<list.size();i++){
			if(list.get(i).getImageName().equals("combatantSprite.png")){
				setSprite(combatantSprite);
			}
			if(list.get(i).getImageName().equals("unitSprite.png")){
				setSprite(unitSprite);
			}
			if(list.get(i).getImageName().equals("structureSprite.png")){
				setSprite(structureSprite);
			}
			if(list.get(i).getImageName().equals("turretSprite.png")){
				setSprite(turretSprite);
			}
			
			if (sprite == null) {
				GWT.log("Sprite is null?");
			}
        
			bufCtx.drawImage(
				(ImageElement) sprite.getElement().cast(),
				list.get(i).getPosition().getX(),
				list.get(i).getPosition().getY());
		}
		// Copy buffer onto main canvas
		ctx.drawImage((CanvasElement) buffer.getElement().cast(), 0, 0);
	}
}
