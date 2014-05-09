package edu.ycp.cs320.rts.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class BoardJSON extends HttpServlet {
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
			ISharedGamestate state = SharedGamestate.getInstance();
			
			JSON.getObjectMapper().writeValue(resp.getWriter(), state.getSharedgamestate());
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.setContentType("application/json");
			return;
		
	}

}
