package com.example.demo.chat;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/chat/{username}",
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class,
        configurator = SpringConfigurator.class)
@Component
public class ChatEndpoint {

    private static Set<ChatEndpoint> chatEndpoints = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();
    private Session session;

    private static void broadcast(Message message) {

        chatEndpoints.forEach(endpoint -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote().sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "username") String username) throws InterruptedException {
        this.session = session;
        chatEndpoints.add(this);
        users.put(session.getId(), username);
        Message message = new Message(username, "Connected");

        for (int i = 0; i < 100_000; i++) {
            Thread.sleep(1000);
            broadcast(message);
        }
    }

    @OnMessage
    public void onMessage(Session session, Message message) {
        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) {
        chatEndpoints.remove(this);
        Message message = new Message(users.get(session.getId()), "Disconected!");
        broadcast(message);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }
}
