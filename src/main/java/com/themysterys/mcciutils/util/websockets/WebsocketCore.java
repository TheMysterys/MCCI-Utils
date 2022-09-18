package com.themysterys.mcciutils.util.websockets;


import com.google.gson.*;
import com.themysterys.mcciutils.McciUtils;
import net.minecraft.client.MinecraftClient;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class WebsocketCore {

    private static final String WEBSOCKET_URL = "ws://mcciutils.mysterybots.com:8831";
    private static final String DEV_WEBSOCKET_URL = "ws://localhost:8080";

    private static int errorCount = 0;
    private static boolean closed = false;

    private static WebSocketClient client;

    private static void connect() throws URISyntaxException {
        client = new WebSocketClient(new URI(WEBSOCKET_URL), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                McciUtils.LOGGER.info("Connected to websocket server!");
                errorCount = 0;
                // Create json object to send to server containing uuid
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("uuid", MinecraftClient.getInstance().getSession().getUuid());
                // Send json object to server
                send(jsonObject.toString());
            }

            @Override
            public void onMessage(String message) {
                if (message.equals("Invalid UUID")) {
                    McciUtils.LOGGER.error("Invalid UUID! Please restart Client!");
                    McciUtils.LOGGER.info("Shutting down websocket.");
                    closed = true;
                    return;
                }
                // parse message to json
                JsonElement jsonElement = JsonParser.parseString(message);
                JsonArray jsonArray = jsonElement.getAsJsonArray();

                // Clear (removes users who have logged out)
                ModUsers.clear();
                // Add current users to list
                for (JsonElement user : jsonArray) {
                    JsonObject userObject = user.getAsJsonObject();
                    String username = userObject.get("username").getAsString();
                    String rank = userObject.get("rank").getAsString();
                    ModUsers.addUser(username, rank);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                if (closed) {
                    return;
                }
                McciUtils.LOGGER.info("Reconnecting in " + (5 + (errorCount * 5)) + " seconds...");
                ModUsers.clear();
                if (errorCount < 10) {
                    new Thread(() -> {
                        try {
                            // Wait 5 seconds and extend for each error after that
                            // Continues up to a max of 50 seconds.
                            Thread.sleep((5 + (errorCount * 5L)) * 1000);
                            this.reconnect();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                } else {
                    McciUtils.LOGGER.error("Failed to reconnect to websocket server!");
                }
            }

            @Override
            public void onError(Exception ex) {
                if (closed) {
                    return;
                }
                McciUtils.LOGGER.error("Error while connecting to websocket server!");
                System.out.println(ex.getMessage());
                ModUsers.clear();
                errorCount++;
            }
        };
        client.connect();
    }

    public static void init() {
        if (client == null) {
            try {
                connect();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void reconnect() {
        if (!client.isOpen()) {
            client.reconnect();
        }
    }
}
