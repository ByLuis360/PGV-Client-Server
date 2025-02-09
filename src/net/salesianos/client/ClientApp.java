package net.salesianos.client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

import net.salesianos.utils.Constants;

public class ClientApp {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String serverMessage = "";

        Socket socket = new Socket("localhost", Constants.SERVER_PORT);
        DataOutputStream clientOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        DataInputStream clientInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

        serverMessage = clientInputStream.readUTF();
        System.out.println(serverMessage + " que es teo es el nombre? ");
        String name = scanner.nextLine();
        clientOutputStream.writeUTF(name);
        clientOutputStream.flush();
        serverMessage = clientInputStream.readUTF();
        System.out.println(serverMessage + " esto que es");
        String bet = scanner.nextLine();
        clientOutputStream.writeUTF(bet);
        clientOutputStream.flush();

        if (bet.equals("no")) {
            System.out.println("fin del juego");
        } else {

            while (true) {
                serverMessage = clientInputStream.readUTF();
                System.out.println(serverMessage);
                clientOutputStream.writeUTF(scanner.nextLine());
                clientOutputStream.flush();

            }

        }

    }
}
