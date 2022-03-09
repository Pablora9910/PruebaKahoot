package es.studium.pruebakahoot;

import java.io.Serializable;

public class Preguntas implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int id;
	public String enunciado, correcta, incorrecta1, incorrecta2, incorrecta3;
	public Preguntas()
	{
		
	}
	public Preguntas(int id, String enunciado,  String correcta, String incorrecta1, String incorrecta2, String incorrecta3)
	{
		this.id = id;
		this.enunciado = enunciado;
		this.correcta = correcta;
		this.incorrecta1 = incorrecta1;
		this.incorrecta2 = incorrecta2;
		this.incorrecta3 = incorrecta3;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	public String getCorrecta() {
		return correcta;
	}

	public void setCorrecta(String correcta) {
		this.correcta = correcta;
	}

	public String getIncorrecta1() {
		return incorrecta1;
	}

	public void setIncorrecta1(String incorrecta1) {
		this.incorrecta1 = incorrecta1;
	}

	public String getIncorrecta2() {
		return incorrecta2;
	}

	public void setIncorrecta2(String incorrecta2) {
		this.incorrecta2 = incorrecta2;
	}

	public String getIncorrecta3() {
		return incorrecta3;
	}

	public void setIncorrecta3(String incorrecta3) {
		this.incorrecta3 = incorrecta3;
	}

}
