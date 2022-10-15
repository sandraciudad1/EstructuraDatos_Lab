package Grafos;

/******************************************************************************************************************* 
 * 
 * Class Name: Tenista.
 * Author/s name: Sandra Ciudad Moreno, Natalia García González y Néstor López Torres.
 * Release/Creation date: 10/06/2021
 * Class version: 1.2
 * Class description: Clase que contiene todos los datos referentes a un tenista, los cuales utilizaremos en la clase 
 * 					  principal para hacer diversas operaciones.
 * 
 *******************************************************************************************************************/

public class Tenista {
	//Atributos de los tenistas, pertenecientes a la clase Tenista
	String nombre, mano, pais, rango;
	
	/*****************************************************************************************************************
	*
	* Method name: Tenista
	* Description of the Method: Es el método constructor de la clase Tenista
	* Calling arguments: nombre del tenista, mano con la que juega el tenista, pais de origen y rango que tiene este.
	* 
	******************************************************************************************************************/
	//Definimos la clase Tenista con los atributos anteriores:
	public Tenista(String nombre, String mano, String pais, String rango) {
		this.nombre=nombre;
		this.mano=mano;
		this.pais=pais;
		this.rango = rango;
	}

	//Método getID que devuelve el nombre del tenista.
	public String getID() {
		return nombre;
	}
	
	//Método toString que devuelve el ID del tenista.
	public String toString() {
		return getID();
	}

}
