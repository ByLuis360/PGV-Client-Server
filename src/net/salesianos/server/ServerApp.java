package net.salesianos.server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import net.salesianos.server.threads.ClientHandler;
import net.salesianos.utils.Constants;

public class ServerApp {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
        System.out.println("Servidor levantado en el puerto " + serverSocket.getLocalPort());

        while (true) {
            System.out.println("Esperando cliente...");
            Socket clientSocket = serverSocket.accept();

            DataInputStream clientInputStream = new DataInputStream(
                    new BufferedInputStream(clientSocket.getInputStream()));
            DataOutputStream clientOutputStream = new DataOutputStream(
                    new BufferedOutputStream(clientSocket.getOutputStream()));

            ClientHandler clientHandler = new ClientHandler(clientInputStream, clientOutputStream);
            clientHandler.start();

        }

    }
}
