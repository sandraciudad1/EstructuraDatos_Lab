package Grafos;

/******************************************************************************************************************* 
 * 
 * Class Name: Partido.
 * Author/s name: Sandra Ciudad Moreno, Natalia García González y Néstor López Torres.
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
	* Description of the Method: Es el método constructor de la clase Partido
	* Calling arguments: nombre del torneo, número del partido, rango del jugador ganador, rango del jugador perdedor, 
	* 					 puntos del jugador ganador, puntos del jugador perdedor, país del jugador ganador, país del 
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

	//Declaramos el método getID que devolverá el nombre del torneo y el número de partido.
	public String getID() {
		return nombretorneo + " " + numpartido;
	}

	//Método toString que devolverá el ID.
	@Override
	public String toString() {
		return getID();
	}
	
	
	
}

