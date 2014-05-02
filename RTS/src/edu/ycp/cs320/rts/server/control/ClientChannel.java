package edu.ycp.cs320.rts.server.control;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import edu.ycp.cs320.rts.shared.GameState;

/**
 * Communication channel allowing a client to connect
 * to a shared {@link GameState} managed by a
 * {@link GameStateManager}.
 */
public class ClientChannel {
	// Client will not wait more than this number of ms for an
	// updated game state from the server.
	public static final int MAX_WAIT_MS = 1000;
	
	private LinkedBlockingQueue<GameState> proposedQueue;
	private LinkedBlockingQueue<GameState> updateQueue;
	
	/**
	 * Constructor.
	 */
	public ClientChannel() {
		// Queues for communication with GameStateManager.
		// Each has a capacity of 1.
		this.proposedQueue = new LinkedBlockingQueue<GameState>(1);
		this.updateQueue = new LinkedBlockingQueue<GameState>(1);
	}
	
	/**
	 * Update given local {@link GameState} by reconciling it with
	 * other local GameStates to produce a new global {@link GameState}
	 * (which should become the client's new local GameState).
	 * 
	 * @param gameState the client's local {@link GameState} containing proposed updates
	 * @return a new global {@link GameState}, or null if the server didn't
	 *         post one within the maximum allowed interval
	 * @throws InterruptedException
	 */
	public GameState update(GameState gameState) {
		if (!proposedQueue.offer(gameState)) {
			System.out.println("Client offering proposed game state, but previous one is still in queue?");
		}
		try {
			GameState updated = updateQueue.poll(MAX_WAIT_MS, TimeUnit.MILLISECONDS);
			if (updated == null) {
				System.out.println("Updated game state did not appear within " + MAX_WAIT_MS + " ms?");
			}
			return updated;
		} catch (InterruptedException e) {
			throw new IllegalStateException("Client interrupted unexpectedly", e);
		}
	}

	/**
	 * Take a proposed {@link GameState}.
	 * This is called by the {@link GameStateManager}'s worker thread.
	 * 
	 * @return a {@link GameState} proposed by the client, or null
	 *         if the client has not proposed one
	 */
	public GameState takeProposed() {
		return proposedQueue.poll();
	}

	/**
	 * Post an updated global {@link GameState}, making it available to the client.
	 * This is called by the {@link GameStateManager}'s worker thread.
	 * 
	 * @param gameState the updated global {@link GameState}
	 */
	public void postUpdate(GameState gameState) {
		if (!updateQueue.offer(gameState)) {
			//System.out.println("Offering updated game state, but client hasn't retrieved the previous one?");
		}
	}

}
