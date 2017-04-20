package com.windhaven_consulting.breezy.controller.ui;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;

//@ViewScoped
@ManagedBean
public class UserLoginView {
    private String username;
    
    private String password;
    
    private static AtomicInteger id = new AtomicInteger();
 
    @PostConstruct
    public void postConstruc() {
    	System.out.println("constructing UserLoginView id = " + id.incrementAndGet());
    }
    
    public String getUsername() {
        return username;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
    public Integer getId() {
    	return id.get();
    }
    
    public void preload() {
    	System.out.println("enter preload id: " + getId() + ", username = " + username + ", password = " + password);
    	username = "username_" + getId();
    	password = "password_" + getId();
    	System.out.println("exit preload id: " + getId() + ", username = " + username + ", password = " + password);
    }
    
    public void login(ActionEvent event) {
    	System.out.println("login id: " + getId() + ", username = " + username + ", password = " + password);
        RequestContext context = RequestContext.getCurrentInstance();
        FacesMessage message = null;
        boolean loggedIn = false;
         
        if(username != null && username.equals("admin") && password != null && password.equals("admin")) {
            loggedIn = true;
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome", username);
        } else {
            loggedIn = false;
            message = new FacesMessage(FacesMessage.SEVERITY_WARN, "Loggin Error", "Invalid credentials");
        }
         
        FacesContext.getCurrentInstance().addMessage(null, message);
        context.addCallbackParam("loggedIn", loggedIn);
    }
    
    public void cancel(ActionEvent event) {
    	System.out.println("Canceling id: " + getId() + ", username = " + username + ", password = " + password);
    	
    	/**
    	 * these values are already null because JSF creates a new instance of us but does not xfer values
    	 * this is if immediate="true" for commandButton
    	 */
    	username = null;
    	password = null;
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cancel", "");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
