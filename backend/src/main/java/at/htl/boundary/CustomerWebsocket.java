package at.htl.boundary;

import at.htl.controller.CustomerRepository;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/customer")
@ApplicationScoped
public class CustomerWebsocket {
    @Inject
    CustomerRepository customerRepo;

    List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        new Thread(() -> {
            String customerJson = JsonbBuilder.create().toJson(customerRepo.getAll());
            sendCustomersToSession(session, customerJson);
        }).start();
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("Der Client hat etwas gesendet: "+message);
    }

    private void sendCustomersToSession(Session session, String customerJsonArray) {
        session.getAsyncRemote().sendText(customerJsonArray, result -> {
            if (result.getException() != null) {
                System.out.println("Unable to send message: " + result.getException());
            }
        });
    }
}
