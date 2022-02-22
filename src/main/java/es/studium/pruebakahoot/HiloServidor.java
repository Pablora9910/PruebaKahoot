package es.studium.pruebakahoot;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HiloServidor extends Thread {
	DataInputStream fentrada;
	ObjectOutputStream fentradaPregunta;
	Socket socket;
	String nombre;
	boolean fin = false;
	int resultado=-1;
	int puntuacion = 0;

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
		//EnviarMensajes(texto);
		// Seguidamente, se crea un bucle en el que se recibe lo que el cliente escribe en el chat.
		// Cuando un cliente finaliza con el botón Salir, se envía un * al servidor del Chat,
		// entonces se sale del bucle while, ya que termina el proceso del cliente,
		// de esta manera se controlan las conexiones actuales
		while (!fin) {
			String cadena = "";
			try {
				nombre =  fentrada.readUTF();
				ServidorChat.textarea.append("Servidor> " + nombre);
				this.setName(nombre);
				System.out.println(this.getName() + "Nombre Jugador");
				if (cadena.trim().equals("*")) {
					ServidorChat.ACTUALES--;
					ServidorChat.mensaje.setText("Número de conexiones actuales: " + ServidorChat.ACTUALES);
					fin = true;
				}
				// El texto que el cliente escribe en el chat,
				// se añade al textarea del servidor y se reenvía a todos los clientes
				else {
					ServidorChat.textarea.append(cadena + "\n");
				
					Preguntas p = ObtenerPreguntas.consulta();
					ServidorChat.textarea.append(p.getEnunciado());
					EnviarMensajes(p);
					resultado = recibirResultado(); 
					System.out.println(resultado + "SiEsCorrecto");
					establecerPuntuacion();
					System.out.println(puntuacion + "PuntJugador");
					
					/*EnviarMensajes(p.getCorrecta());
					EnviarMensajes(p.getIncorrecta1());
					EnviarMensajes(p.getIncorrecta2());
					EnviarMensajes(p.getIncorrecta3());*/



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
	private void EnviarMensajes(Preguntas pregunta) {
		//for (int i = 0; i < ServidorChat.CONEXIONES; i++) {
		//Socket socket = ServidorChat.tabla[i];
		try {
			fentradaPregunta = new ObjectOutputStream(socket.getOutputStream());
			fentradaPregunta.writeObject(pregunta);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//}
	}
	
	private int recibirResultado() {
		//for (int i = 0; i < ServidorChat.CONEXIONES; i++) {
		//Socket socket = ServidorChat.tabla[i];
		int correcto = -1;
		try {
			fentrada = new DataInputStream(socket.getInputStream());
			correcto =  fentrada.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return correcto;
		//}
	}
	private void establecerPuntuacion() 
	{
		if(resultado == 1) 
		{
			puntuacion++;
		}
	}


}
