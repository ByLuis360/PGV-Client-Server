package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ClientHandler extends Thread {
    private DataInputStream clientInputStream;
    private DataOutputStream clienOutputStream;

    public ClientHandler(DataInputStream clientInputStream, DataOutputStream clientOutputStream) {
        this.clientInputStream = clientInputStream;
        this.clienOutputStream = clientOutputStream;
    }
}
