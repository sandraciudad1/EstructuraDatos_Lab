package Grafos;
import graphsDSESIUCLM.*;

/******************************************************************************************************************* 
 * 
 * Class Name: ElementoDecorado
 * Author/s name: Sandra Ciudad Moreno, Natalia García González y Néstor López Torres.
 * Release/Creation date: 10/06/2021
 * Class version: 1.0
 * Class description: Clase que contiene los datos necesarios para realizar diversas operaciones y/o comprobaciones 
 * 					  en los objetos de tipo ElementoDecorado, como por ejemplo visitar un elemento o conocer su ID.
 * 
 *******************************************************************************************************************/

public class ElementoDecorado<T> implements Element {
	//Atributos de los elementos decorados:
	private String ID;                
	private T element;                
	private boolean visited;          
	private ElementoDecorado<T> parent; 
	private int distance;    

	/**********************************************************************************************************
	*
	* Method name: ElementoDecorado
	* Description of the Method: Es el método constructor de la clase ElementoDecorado
	* Calling arguments: key y element.
	* 
	***********************************************************************************************************/
	//Definimos la clase ElementoDecorado con los atributos anteriores
	public ElementoDecorado(String key, T element) {
		this.element = element;
		ID = key;
		visited = false;
		parent = null;
		distance = 0;
	}

	//Declaramos los métodos getter y setter
	//GETTERS
	public T getElement() {
		return element;
	}
	
	public boolean getVisited() {
		return visited;
	}
	
	public ElementoDecorado<T> getParent() {
		return parent;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public String getID() {
		return ID;
	}
	
	//SETTERS
	public void setParent(ElementoDecorado<T> u) {
		parent = u;
	}
	
	public void setVisited(boolean t) {
		visited = t;
	}
	
	public void setDistance(int d) {
		distance = d;
	}

	public boolean equals (Object n) {
		return (ID.equals(((ElementoDecorado) n).getID())
				&& element.equals(((ElementoDecorado<T>) n).getElement()));
	}
	
	//Metodo toString, que devolverá element.
	public String toString() {
		return element.toString();   
	}
	
	
}