package at.htl.boundary;

import org.jboss.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/team/{teamName}")
@ApplicationScoped
public class TeamSocket {

    @Inject
    Logger log;

    Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("teamName") String username) {
        sessions.put(username, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("teamName") String username) {
        sessions.remove(username);
        broadcast("Team hat spiel verlassen");
    }

    @OnError
    public void onError(Session session, @PathParam("teamName") String username, Throwable throwable) {
        sessions.remove(username);
        broadcast("Fehler " + throwable);
    }

    @OnMessage
    public void onMessage(String message, @PathParam("teamName") String teamName) {
        if (message.equalsIgnoreCase("_ready_")) {
            broadcast("Team " + teamName + " joined");
        } else {
            broadcast(">> " + teamName + ": " + message);
        }
    }

    private void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                log.info(message);
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }

}