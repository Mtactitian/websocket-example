package com.example.demo.chat;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;

public class MessageDecoder implements Decoder.Text<Message> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message decode(String s) {
        try {
            return objectMapper.readValue(s, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
