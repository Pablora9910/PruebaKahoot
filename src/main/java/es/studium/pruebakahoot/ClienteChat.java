package es.studium.pruebakahoot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClienteChat extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Socket socket;
	DataInputStream fentrada;
	DataOutputStream fsalida;
	String nombre;
	boolean repetir = true;
	JLabel lbl_pregunta=new JLabel("PREGUNTA");

	JRadioButton respuesta1=new JRadioButton("texto1");
	JRadioButton respuesta2=new JRadioButton("texto2");
	JRadioButton respuesta3=new JRadioButton("texto3");
	JRadioButton respuesta4=new JRadioButton("texto4");

	ButtonGroup grupo=new ButtonGroup();

	JTextArea area=new JTextArea();

	JButton btn_enviar= new JButton("Enviar");

	public ClienteChat(Socket socket, String nombre) {
// Prepara la pantalla. Se recibe el socket creado y el nombre del cliente
		super(" Conexión del cliente chat: " + nombre);
			setTitle("Cliente 1");
			setBounds(100, 100, 500, 600);
			//JPanel
			JPanel panel= new JPanel();		
			panel.setLayout(null);	

			lbl_pregunta.setBounds(10, 1, 300, 100);
			panel.add(lbl_pregunta);

			respuesta1.setBounds(50, 100, 200, 50);
			grupo.add(respuesta1);
			respuesta2.setBounds(50, 150, 200, 50);
			grupo.add(respuesta2);
			respuesta3.setBounds(50, 200, 200, 50);
			grupo.add(respuesta3);
			respuesta4.setBounds(50, 250, 200, 50);
			grupo.add(respuesta4);

			panel.add(respuesta1);
			panel.add(respuesta2);
			panel.add(respuesta3);
			panel.add(respuesta4);

			area.setBounds(20, 350, 300, 200);
			panel.add(area);

			btn_enviar.setBounds(350, 380, 100, 150);
			panel.add(btn_enviar);

			add(panel);
			
			//Fin JPanel
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			btn_enviar.addActionListener(this);
		this.socket = socket;
		this.nombre = nombre;
//Se crean los flujos de entrada y salida.
//En el flujo de salida se escribe un mensaje
//indicando que el cliente se ha unido al Chat.
//El HiloServidor recibe este mensaje y
//lo reenvía a todos los clientes conectados
		try {
			fentrada = new DataInputStream(socket.getInputStream());
			fsalida = new DataOutputStream(socket.getOutputStream());
			String texto = "SERVIDOR> Entra en el chat... " + nombre;
			fsalida.writeUTF(texto);
		} catch (IOException ex) {
			System.out.println("Error de E/S");
			ex.printStackTrace();
			System.exit(0);
		}
	}

//El método main es el que lanza el cliente,
//para ello en primer lugar se solicita el nombre o nick del
//cliente, una vez especificado el nombre
//se crea la conexión al servidor y se crear la pantalla del Chat(ClientChat)
//lanzando su ejecución (ejecutar()).
	public static void main(String[] args) throws Exception {
		int puerto = 44444;
		String nombre = JOptionPane.showInputDialog("Introduce tu nombre o nick:");
		Socket socket = null;
		try {
			socket = new Socket("192.168.0.19", puerto);
		} catch (IOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor \n" + ex.getMessage(),
					"<<Mensaje de Error:1>>", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		if (!nombre.trim().equals("")) {
			ClienteChat cliente = new ClienteChat(socket, nombre);
			cliente.setBounds(0, 0, 500, 600);
			cliente.setVisible(true);
			cliente.ejecutar();
		} else {
			System.out.println("El nombre está vacío...");
		}
	}

// Cuando se pulsa el botón Enviar,
// el mensaje introducido se envía al servidor por el flujo de salida
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_enviar) {
			String texto=area.getText();
			if(respuesta1.isSelected())
			{
				texto=respuesta1.getText();
				respuesta1.setSelected(false);
			}
			if(respuesta2.isSelected())
			{
				texto=respuesta2.getText();
				respuesta2.setSelected(false);
			}
			if(respuesta3.isSelected())
			{
				texto=respuesta3.getText();
				respuesta3.setSelected(false);
			}
			if(respuesta4.isSelected())
			{
				texto=respuesta4.getText();
				respuesta4.setSelected(false);
			}
			try {
				area.setText("");
				fsalida.writeUTF(texto);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
// Si se pulsa el botón Salir,
// se envía un mensaje indicando que el cliente abandona el chat
// y también se envía un * para indicar
// al servidor que el cliente se ha cerrado
	/*	else if (e.getSource() == btn_enviar) {
			String texto = "SERVIDOR> Abandona el chat... " + nombre;
			try {
				fsalida.writeUTF(texto);
				fsalida.writeUTF("*");
				repetir = false;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}*/
	}

// Dentro del método ejecutar(), el cliente lee lo que el
// hilo le manda (mensajes del Chat) y lo muestra en el textarea.
// Esto se ejecuta en un bucle del que solo se sale
// en el momento que el cliente pulse el botón Salir
// y se modifique la variable repetir
	public void ejecutar() {
		String texto = "";
		while (repetir) {
			try {
				rellenar();
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Imposible conectar con el servidor \n" + ex.getMessage(),
						"<<Mensaje de Error:2>>", JOptionPane.ERROR_MESSAGE);
				repetir = false;
			}
		}
		try {
			socket.close();
			System.exit(0);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

private void rellenar() throws IOException {
	String texto;
	texto = fentrada.readUTF();
	respuesta1.setText(texto);
	texto = fentrada.readUTF();
	lbl_pregunta.setText(texto);
	texto = fentrada.readUTF();
	respuesta2.setText(texto);
	texto = fentrada.readUTF();
	respuesta3.setText(texto);
	texto = fentrada.readUTF();
	respuesta4.setText(texto);
}
}
