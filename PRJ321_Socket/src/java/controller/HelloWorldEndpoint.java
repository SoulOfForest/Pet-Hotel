/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author ViruSs0209
 */
@ServerEndpoint("/hello")
public class HelloWorldEndpoint {
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    
    @OnOpen
    public void onOpen(Session session) {
        String userRole = session.getQueryString().substring(session.getQueryString().lastIndexOf("=") + 1, session.getQueryString().length());
        
        session.getUserProperties().put("role", userRole);
        
        sessions.add(session); 
        
        System.out.println("Session opened, id: " + session.getId());
    }
    
    @OnMessage
    public void onMessage(String txt, Session session) throws IOException {
    
        if (session.getUserProperties().get("role").toString().equals("petowner")) {

        Iterator<Session> it = sessions.iterator();
        
        while (it.hasNext()) {
            Session temp = it.next();
            
            String role = temp.getUserProperties().get("role").toString();
            
            if (role != null && (role.equals("manager") || role.equals("employee"))) {
                System.out.println("send");
                temp.getBasicRemote().sendText(txt);
                break;
            }
        }
        
        }
        
    }
    
    @OnClose
    public void onClose(Session session) throws IOException {
        sessions.remove(session);
        System.out.println("Session Closed, id: " + session.getId());
        
        
    }
}
