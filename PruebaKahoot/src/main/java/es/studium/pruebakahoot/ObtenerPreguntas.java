package es.studium.pruebakahoot;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ObtenerPreguntas 
{
	static ConexionBd conexion;
	static String sentencia = "";
	static Connection connection = null;
	static Statement statement = null;
	static ResultSet rs = null;
	static int id;
	static String pregunta = "";
	static String correcta = "";
	static String incorrecta1 = "";
	static String incorrecta2 = "";
	static String incorrecta3 = "";
	
	public ObtenerPreguntas() 
	{
		consulta();
		imprimirRespuestas();
		
	}
	public static void main(String[] args) 
	{
		new ObtenerPreguntas();
	}
	
	public static Preguntas consulta() 
	{
		conexion = new ConexionBd();
		connection = conexion.conectar();
		try 
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM  preguntas order by rand() limit 1";
			rs = statement.executeQuery(sentencia);
	
			while(rs.next()) 
			{
				id = rs.getInt("idPregunta");
				pregunta = rs.getString("enunciadoPregunta");
			}
		}
		catch (SQLException sqle)
		{	
		}
		try 
		{
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			sentencia = "SELECT * FROM  respuestas where  idRespuesta =" + id + ";";
			rs = statement.executeQuery(sentencia);
	
			while(rs.next()) 
			{
				correcta = rs.getString("correctoRespuesta");
				incorrecta1 = rs.getString("incorrecto1Respuesta");
				incorrecta2 = rs.getString("incorrecto2Respuesta");
				incorrecta3 = rs.getString("incorrecto3Respuesta");
				
				
				 
			}
		}
		catch (SQLException sqle)
		{	
		}
		Preguntas preguntas = new Preguntas(id, pregunta, correcta, incorrecta1, incorrecta2, incorrecta3);
		System.out.println(preguntas.getEnunciado());
		return preguntas;
	}
	public void imprimirRespuestas() 
	{
		int n = (int) (Math.random() * (4 - 0)) + 0;
		switch(n) 
		{
			case 0:
				System.out.println(correcta);
				System.out.println(incorrecta1);
				System.out.println(incorrecta2);
				System.out.println(incorrecta3);
				break;
			case 1:
				
				System.out.println(incorrecta1);
				System.out.println(correcta);
				System.out.println(incorrecta2);
				System.out.println(incorrecta3);
				break;
			case 2:
				
				System.out.println(incorrecta1);
				System.out.println(incorrecta2);
				System.out.println(correcta);
				System.out.println(incorrecta3);
				break;
			case 3:
				
				System.out.println(incorrecta1);
				System.out.println(incorrecta2);
				System.out.println(incorrecta3);
				System.out.println(correcta);
				break;
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("CORRECTA: " + (n+1));
	}

}



