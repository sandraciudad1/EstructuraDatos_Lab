package Grafos;

/******************************************************************************************************************* 
 * 
 * Class Name: Partido.
 * Author/s name: Sandra Ciudad Moreno, Natalia Garc�a Gonz�lez y N�stor L�pez Torres.
 * Release/Creation date: 10/06/2021
 * Class version: 1.2
 * Class description: Clase que contiene todos los datos referentes a un partido, los cuales utilizaremos en la clase 
 * 					  principal para hacer diversas comprobaciones.
 * 
 *******************************************************************************************************************/

public class Partido {
	//Atributos de los partidos, pertenecientes a la clase Partido
	String nombretorneo, numpartido, rangoGanador, rangoPerdedor, puntosGanador, puntosPerdedor;
	String paisGanador, paisPerdedor;
	Tenista ganador, perdedor;
	
	/*****************************************************************************************************************
	*
	* Method name: Partido
	* Description of the Method: Es el m�todo constructor de la clase Partido
	* Calling arguments: nombre del torneo, n�mero del partido, rango del jugador ganador, rango del jugador perdedor, 
	* 					 puntos del jugador ganador, puntos del jugador perdedor, pa�s del jugador ganador, pa�s del 
	* 					 jugador perdedor, nombre del ganador y nombre del perdedor.
	* 
	******************************************************************************************************************/
	//Definimos la clase Partido con los atributos anteriores:
	public Partido(String nombretorneo, String numpartido, String rangoGanador, String puntosGanador, String rangoPerdedor, String puntosPerdedor, String paisGanador, String paisPerdedor, Tenista ganador, Tenista perdedor) {
		this.nombretorneo = nombretorneo;
		this.numpartido = numpartido;
		this.rangoGanador = rangoGanador;
		this.rangoPerdedor = rangoPerdedor;
		this.puntosGanador = puntosGanador;
		this.puntosPerdedor = puntosPerdedor;
		this.paisGanador = paisGanador;
		this.paisPerdedor = paisPerdedor;
		this.ganador = ganador;
		this.perdedor = perdedor;
	}

	//Declaramos el m�todo getID que devolver� el nombre del torneo y el n�mero de partido.
	public String getID() {
		return nombretorneo + " " + numpartido;
	}

	//M�todo toString que devolver� el ID.
	@Override
	public String toString() {
		return getID();
	}
	
	
	
}

