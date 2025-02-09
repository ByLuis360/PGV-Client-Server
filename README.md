Documentación Técnica - Juego Cara o Cruz

1. Descripción General

Este proyecto es un juego de "Cara o Cruz" basado en una arquitectura cliente-servidor. Los jugadores pueden conectarse a un servidor, realizar apuestas y jugar a lanzar una moneda para intentar ganar dinero virtual.

2. Arquitectura del Sistema

El sistema sigue una arquitectura cliente-servidor basada en ServerSocket y Socket para la comunicación de red. Utiliza hilos (Thread) para manejar múltiples clientes simultáneamente.

Componentes principales:

Cliente (ClientApp): Se conecta al servidor, envía datos y recibe respuestas.

Servidor (ServerApp): Acepta conexiones de clientes y maneja la lógica del juego.

Hilo de Cliente (ClientHandler): Maneja la comunicación individual entre el servidor y cada cliente.

Constantes (Constants): Define valores fijos como el puerto del servidor.

3. Implementación Técnica

3.1. Cliente (ClientApp.java)

Ubicación: net.salesianos.client

Funcionalidad:

Se conecta al servidor mediante un Socket.

Lee y escribe datos a través de DataInputStream y DataOutputStream.

Pide el nombre del jugador.

Solicita una apuesta inicial de 25€.

Envía la selección ("cara" o "cruz") al servidor.

Recibe el resultado y permite seguir jugando o salir.

Código relevante:

Socket socket = new Socket("localhost", Constants.SERVER_PORT);
DataOutputStream clientOutputStream = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
DataInputStream clientInputStream = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

3.2. Servidor (ServerApp.java)

Ubicación: net.salesianos.server

Funcionalidad:

Inicia un ServerSocket en un puerto definido en Constants.

Espera conexiones de clientes.

Para cada cliente, crea un ClientHandler en un hilo separado.

Código relevante:

ServerSocket serverSocket = new ServerSocket(Constants.SERVER_PORT);
while (true) {
    Socket clientSocket = serverSocket.accept();
    ClientHandler clientHandler = new ClientHandler(clientInputStream, clientOutputStream);
    clientHandler.start();
}

3.3. Hilo de Cliente (ClientHandler.java)

Ubicación: net.salesianos.server.threads

Funcionalidad:

Solicita el nombre del jugador.

Pregunta si el jugador desea apostar.

Si el jugador acepta, le pide elegir entre "cara" o "cruz".

Simula un lanzamiento de moneda con Random.nextBoolean().

Si gana, suma 50€ al contador; si pierde, resta 25€.

Permite seguir jugando o salir.

Código relevante:

boolean electionWinner = random.nextBoolean();
if (electionWinner) {
    result = option;
    counter += 50;
} else {
    result = option.equals("cara") ? "cruz" : "cara";
    counter -= 25;
}

4. Flujo de Ejecución

El servidor se inicia en un puerto específico.

El cliente se conecta al servidor.

Se solicita el nombre del usuario.

Se pregunta si desea apostar 25€.

Si acepta, el cliente elige "cara" o "cruz".

El servidor genera un resultado aleatorio y evalúa el ganador.

Se muestra el resultado y el saldo acumulado.

El cliente puede seguir jugando o salir.

5. Mejoras y Optimización

Manejo de Excepciones: Actualmente, las excepciones no se manejan adecuadamente en ClientHandler.

Cerrar Recursos: Es recomendable cerrar Socket, DataInputStream y DataOutputStream correctamente.

Evitar Comparación Incorrecta de Strings: Se debe usar .equals() en lugar de == al comparar Strings.

6. Conclusión

Este juego es un buen ejemplo de una aplicación cliente-servidor en Java, manejando múltiples conexiones y comunicación en tiempo real. Puede mejorarse con interfaz gráfica o almacenamiento de datos en base de datos.

