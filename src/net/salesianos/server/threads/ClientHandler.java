package net.salesianos.server.threads;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Random;

public class ClientHandler extends Thread {

    private String option;
    private DataInputStream clientInputStream;
    private DataOutputStream clienOutputStream;
    private String name;
    private String result;
    private Random random = new Random();
    private boolean keepPlaying = true;
    private int counter = 0;

    public ClientHandler(DataInputStream clientInputStream, DataOutputStream clientOutputStream) {
        this.clientInputStream = clientInputStream;
        this.clienOutputStream = clientOutputStream;
    }

    @Override
    public void run() {
        try {
            clienOutputStream.writeUTF("Introduce el nombre:");
            clienOutputStream.flush();
            name = clientInputStream.readUTF();

            while (keepPlaying) {
                clienOutputStream.writeUTF("Apuesta para empezar 25€, Apuestas? (si/no)");
                clienOutputStream.flush();
                String acceptBet = clientInputStream.readUTF();

                if (acceptBet.equals("no")) {
                    System.out.println("fin del juego");
                    clienOutputStream.writeUTF("fin del juego");
                    clienOutputStream.flush();
                    String totalGenerated = counter >= 0 ? this.name + " ha ganado " + counter
                            : this.name + " nos debe " + counter;
                    clienOutputStream.writeUTF(totalGenerated);
                    clienOutputStream.flush();

                    keepPlaying = false;
                } else {
                    counter += -25;
                    clienOutputStream.writeUTF("Introduce tu elección, cara o cruz?:");
                    clienOutputStream.flush();
                    option = clientInputStream.readUTF();

                    while (!option.equals("cara") && !option.equals("cruz")) {
                        clienOutputStream.writeUTF("Opción no válida");
                        clienOutputStream.flush();
                        option = clientInputStream.readUTF();
                    }

                    System.out.println(name + " ha elegido " + option);

                    System.out.println("Tiramos moneda");

                    boolean electionWinner = random.nextBoolean();

                    if (electionWinner) {
                        result = option;
                        counter += 50;
                    } else {
                        result = option == "cara" ? "cruz" : "cara";
                        counter += -25;
                    }
                    Thread.sleep(5 * 1000);

                    String messageWinner = electionWinner ? "Has ganado 50, salió " + result
                            : "Has perdido tus 25, salió " + result;
                    System.out.println(messageWinner);
                }

            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
