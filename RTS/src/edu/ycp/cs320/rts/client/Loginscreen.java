package edu.ycp.cs320.rts.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;



public class Loginscreen implements EntryPoint {

	@Override
	public void onModuleLoad() {
		
		final GetBoardServiceAsync boardservice = (GetBoardServiceAsync) GWT.create(GetBoardService.class);
		final AsyncCallback<Boolean> callbackcreate = new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("action failed");
				
			}

			@Override
			public void onSuccess(Boolean result) {
				
				if(result){
					
					Window.alert("User successfully created");
					
				}else{
					Window.alert("User Creation failed");
				}
				
			}
			
		};
		
		final AsyncCallback<Integer> callbacklogin = new AsyncCallback<Integer>() {

			@Override
			public void onFailure(Throwable caught) {
				GWT.log("action failed");
				
				
			}

			@Override
			public void onSuccess(Integer result) {
				if(result != 0){
					Window.alert("Login Successful");
					RootPanel.get().clear();
					RTS maingame = new RTS(result);
					maingame.updateGameState();
				}
				
			}
			
		};
		
		

		final TextBox usernamebox = new TextBox();
		usernamebox.setTitle("Username");
		usernamebox.setText("username");
		final PasswordTextBox passwordbox = new PasswordTextBox();
		passwordbox.setTitle("password");
		Button loginbtn = new Button();
		loginbtn.setText("Login");
		Button switchtocreate = new Button();
		switchtocreate.setText("No Account?");
		final VerticalPanel loginpanel = new VerticalPanel();
		final VerticalPanel createuserpanel = new VerticalPanel();
		
		final TextBox usertext = new TextBox();
		usertext.setText("username");
		usertext.setTitle("username");
		final PasswordTextBox pword1 = new PasswordTextBox();
		final PasswordTextBox pword2 = new PasswordTextBox();
		final TextBox email = new TextBox();
		email.setText("email");
		email.setTitle("email");
		
		Button createuserbtn = new Button();
		Button switchtologinbtn = new Button();
		switchtologinbtn.setText("Switch to login");
		createuserbtn.setText("Create Account");
		
		switchtologinbtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get().remove(createuserpanel);
				RootPanel.get().add(loginpanel);
				
			}
		});
		
		createuserbtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				
				Window.alert("Clicked");
				if(pword1.getText().toString().equals(pword2.getText().toString())){
					boardservice.newuser(usertext.getText().toString(), pword1.getText().toString(), email.getText().toString(), callbackcreate);
				}
				else{
					Window.alert("passwords do not match");
				}
				
				
			}
		});
		
		createuserpanel.add(usertext);
		createuserpanel.add(pword1);
		createuserpanel.add(pword2);
		createuserpanel.add(email);
		createuserpanel.add(createuserbtn);
		createuserpanel.add(switchtologinbtn);
		
		
		
		
		
		switchtocreate.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				RootPanel.get().remove(loginpanel);
				RootPanel.get().add(createuserpanel);
				
			}
		});
		
		loginbtn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				boardservice.login(usernamebox.getText().toString(), passwordbox.getText().toString(), callbacklogin);
				
			}
		});
		
		
		
		loginpanel.add(usernamebox);
		loginpanel.add(passwordbox);
		loginpanel.add(loginbtn);
		loginpanel.add(switchtocreate);
		
		RootPanel.get().add(loginpanel);
		

	}

}
