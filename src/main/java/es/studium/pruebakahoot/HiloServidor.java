package es.studium.pruebakahoot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HiloServidor extends Thread {
	DataInputStream fentrada;
	Socket socket;
	boolean fin = false;

	public HiloServidor(Socket socket) {
		this.socket = socket;
		try {
			fentrada = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("Error de E/S");
			e.printStackTrace();
		}
	}

// En el método run() lo primero que hacemos
// es enviar todos los mensajes actuales al cliente que se
// acaba de incorporar
	public void run() {
		ServidorChat.mensaje.setText("Número de conexiones actuales: " + ServidorChat.ACTUALES);
		String texto = ServidorChat.textarea.getText();
		EnviarMensajes(texto);
// Seguidamente, se crea un bucle en el que se recibe lo que el cliente escribe en el chat.
// Cuando un cliente finaliza con el botón Salir, se envía un * al servidor del Chat,
// entonces se sale del bucle while, ya que termina el proceso del cliente,
// de esta manera se controlan las conexiones actuales
		while (!fin) {
			String cadena = "";
			try {
				cadena = fentrada.readUTF();
				if (cadena.trim().equals("*")) {
					ServidorChat.ACTUALES--;
					ServidorChat.mensaje.setText("Número de conexiones actuales: " + ServidorChat.ACTUALES);
					fin = true;
				}
// El texto que el cliente escribe en el chat,
// se añade al textarea del servidor y se reenvía a todos los clientes
				else {
					ServidorChat.textarea.append(cadena + "\n");
					texto = ServidorChat.textarea.getText();
					Preguntas p = ObtenerPreguntas.consulta();
					EnviarMensajes(p.getEnunciado());
					EnviarMensajes(p.getIncorrecta1());
					EnviarMensajes(p.getIncorrecta2());
					EnviarMensajes(p.getIncorrecta3());
					EnviarMensajes(p.getCorrecta());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				fin = true;
			}
		}
	}

// El método EnviarMensajes() envía el texto del textarea a
// todos los sockets que están en la tabla de sockets,
// de esta forma todos ven la conversación.
// El programa abre un stream de salida para escribir el texto en el socket
	private void EnviarMensajes(String texto) {
		for (int i = 0; i < ServidorChat.CONEXIONES; i++) {
			Socket socket = ServidorChat.tabla[i];
			try {
				DataOutputStream fsalida = new DataOutputStream(socket.getOutputStream());
				fsalida.writeUTF(texto);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
