package Grafos;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

import graphsDSESIUCLM.Edge;
import graphsDSESIUCLM.Graph;
import graphsDSESIUCLM.TreeMapGraph;
import graphsDSESIUCLM.Vertex;
/********************************************************************************************************************************
*
* Class Name: Grafos
* Author/s name: Sandra Ciudad Moreno, Natalia Garc�a Gonz�lez y N�stor L�pez Torres.
* Release/Creation date: 10/06/2021
* Class version: 2.1
* Class description: Clase que corresponde al main de la pr�ctica de grafos que contiene todos los m�todos diferentes necesarios 
* 					 para construir los grafos correspondientes a los apartados a), b) y c)
* 
*********************************************************************************************************************************/

public class Principal {
	
	/***********************************************************************************************************************
	*
	* Method name: main()
	* Description of the Method: En el m�todo main del programa se realizan las llamadas a los distintos m�todos buscan los 
	* 							 caminos requeridos de los grafos.
	* 							 Adem�s, en el main obtendremos el n�mero total de jugadores, los partidos totales y buscaremos 
	* 							 qui�n es el tenista m�s competitivo (y los jugadores a los que se ha enfrentado) y quien es el 
	* 							 tenista con menor rango (y su n�mero de ranking).
	* Required Files: El fichero requerido es atp_matches_2020.csv
	* Exceptions: Se captura la excepci�n e (que es al excepci�n por defecto del try catch) y la excepci�n NumberFormatException.
	* 
	************************************************************************************************************************/
	public static void main(String args[]) {
		try {
			//Lectura del fichero:
			Vector<String[]> partidos = cargarFichero("atp_matches_2020.csv");
			//Inicializamos las variables y estructuras de datos necesarios, que utilizaremos posteriormente:
			Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> graph = crearGrafo(partidos);
			Vertex<ElementoDecorado<Tenista>> vertexU = null, vertexV = null;
			Iterator<Edge<ElementoDecorado<Partido>>> it = graph.getEdges();
			Hashtable<Vertex<ElementoDecorado<Tenista>>, Calculo> jugadores = new Hashtable<>();
			Iterator<Vertex<ElementoDecorado<Tenista>>> iterator1 = graph.getVertices();
			Iterator<Edge<ElementoDecorado<Partido>>> iterator2 = graph.getEdges();
			int different = 0, range = 0;
			
			//Apartado a): 
				//Mostramos el n�mero total de jugadores, de partidos (sin tener el cuenta la copa Davis), el jugador m�s competitivo y el de menor ranking:
			System.out.println("a)");
			System.out.println("Construir el grafo correspondiente, en el que cada partido del fichero se traduce en una relaci�n (arista) entre dos jugadores (nodos).\n"
								+ "Una vez construido, mostrar el n�mero de jugadores, el n�mero de relaciones entre jugadores, el jugador m�s competitivo y el jugador de menor ranking."
								+ "\nNo se tendr�n en cuenta para la construcci�n del grafo los partidos de la Copa Davis");
			System.out.println("\tJugadores totales: " + graph.getN());
			System.out.println("\tPartidos totales: " + graph.getM());
			
			while (it.hasNext()) {
				Edge<ElementoDecorado<Partido>> ed = it.next();
				Vertex<ElementoDecorado<Tenista>> vertices[] = graph.endVertices(ed);
				anadirJugador(jugadores, vertices[0], vertices[1], ed);
			}
			
			for (Vertex<ElementoDecorado<Tenista>> t : jugadores.keySet()) {
				if (different < jugadores.get(t).diferentes.size()) {
					vertexU = t;
					different = jugadores.get(t).diferentes.size();
				}
				if (range < jugadores.get(t).rango) {
					vertexV= t;
					range = jugadores.get(t).rango;
				}
			}
			System.out.println("\tEl tenista mas competitivo es " +vertexU.getID()+ " y se ha enfrentado con " +different+ " jugadores diferentes");
			System.out.println("\tEl tenista con menor rango es " +vertexV.getID()+ " con un ranking de " +range);

			
			//Apartado b):
				//Creaci�n del equipo con jugadores que tengan un ranking mayor de 1500 y en el que los jugadores conectados no sean del mismo pais:
			System.out.println("\nb)");
			System.out.println("La ronda de inauguraci�n estar� formada por un equipo capitaneado por 2 jugadores le�dos por teclado, de forma que se ir�n relevando en cada set. "
								+ "\nPara conformar el equipo se elegir� la secuencia m�s corta de jugadores que conecta a ambos capitanes con un n�mero de puntos en su ranking original "
								+ "\nmayor que 1.500. Los capitanes integrar�n el equipo aunque no cumplan con esta condici�n. Dos jugadores conectados no podr�n ser del mismo pa�s.");
			System.out.println("Nombre del primer capitan:");
			ElementoDecorado<Tenista> firstNode = leerCapitan(graph).getElement();
			System.out.println("Nombre del segundo capitan:");
			ElementoDecorado<Tenista> finalNode = leerCapitan(graph).getElement();
			Stack<ElementoDecorado<Tenista>> camino = buscarBFS(graph, firstNode, finalNode);

			System.out.println("El equipo formado es el siguiente:");
			if (camino == null) {
				System.out.println(firstNode.getID() + "\n" + finalNode.getID());
			} else {
				while (!camino.empty()) {
					System.out.println(camino.pop().getID());
				}
			}

			while(iterator1.hasNext()) {
				iterator1.next().getElement().setVisited(false);
			}
			while(iterator2.hasNext()) {
				iterator2.next().getElement().setVisited(false);
			}
			
			//Apartado c):
				//Creaci�n del equipo con jugadores que juegan con la misma mano que alguno de los capitanes y que tienen un n�mero de ranking menor al de ambos capitanes:
			System.out.println("\nc)");
			System.out.println("Se realizar� una ronda expr�s paralela en la que los jugadores se ir�n eliminando entre s� jugando un tie break. "
					+ "\nEsta ronda estar� formada por otro equipo que capitanear�n dos jugadores le�dos por teclado. "
					+ "\nPara configurar el equipo se calcular� una secuencia cualquiera de jugadores que conecte a esos dos teniendo en cuenta que jueguen con la misma mano "
					+ "\nque alguno de los dos capitanes y su n�mero de ranking sea inferior al menor de los dos capitanes.");
			System.out.println("Nombre del primer capitan:");
			firstNode = leerCapitan(graph).getElement();
			System.out.println("Nombre del segundo capitan:");
			finalNode = leerCapitan(graph).getElement();
			HashSet<ElementoDecorado<Tenista>> ruta = buscarDFS(graph, firstNode, finalNode);

			System.out.println("El equipo formado es el siguiente:");
			if (ruta == null) {
				System.out.println(firstNode.getID() + "\n" + finalNode.getID());
			} else {
				//System.out.println(firstNode.getID() + "\n" + finalNode.getID());
				for(ElementoDecorado<Tenista> tenista : ruta)
					System.out.println(tenista.getID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//M�TODO leerCapitan:
	/********************************************************************************************************************************
	*
	* Method name: leerCapitan()
	* Description of the Method: En este m�todo, el usuario introduce por teclado el nombre del tenista que quiere que sea capit�n, este nombre 
	* 							 se busca en la base de datos y si lo encuentra, lo retornar� al m�todo principal. Por el contrario, si no lo 
	* 							 encuentra, mostrar� un mensaje de error y pedir� de nuevo la introducci�n de un nombre de un tenista.
	* Calling arguments: ElementoDecorado<Partido>> g, que es el grafo del fichero atp_matches_2020.
	* Return value: Devuelve el v�rtice correspondiente al tenista que hemos introducido por teclado (si no es err�neo).
	*  
	********************************************************************************************************************************/
	public static Vertex<ElementoDecorado<Tenista>> leerCapitan(Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> g) {
		Vertex<ElementoDecorado<Tenista>> v = null;
		Scanner teclado = new Scanner(System.in);
		while (v == null) {
			String nombre = teclado.nextLine();
			v = g.getVertex(nombre);
			if (v == null)
				System.out.println("Tenista no encontrado.  \nPor favor introduzca nuevamente el nombre:");
		}
		return v ;
	}
	
	
	//M�TODO a�adirJugador:
	/********************************************************************************************************************************
	*
	* Method name: a�adirJugador()
	* Description of the Method: Este m�todo a�ade dos tenistas, haciendo uso de la clase C�lculo, y comprobando que ambos sean diferentes.
	* 							 Adem�s, asigna al primero de ellos el rango ganador, y al segundo el rango perdedor.
	* Calling arguments: Hashtable<Vertex<ElementoDecorado<Tenista>>.
	* 					 Calculo> tabla, donde guardar los datos de los tenistas.
	* 					 Vertex<ElementoDecorado<Tenista>> tenista1, primer tenista que a�adimos.
	* 					 Vertex<ElementoDecorado<Tenista>> tenista2, segundo tenista que a�adimos.
	* 					 Edge<ElementoDecorado<Partido>> partido, partido que se juega entre el tenista1 y el tenista2.
	* Return value: No devuelve nada.
	*  
	********************************************************************************************************************************/
	public static void anadirJugador(Hashtable<Vertex<ElementoDecorado<Tenista>>, Calculo> tabla, Vertex<ElementoDecorado<Tenista>> tenista1,
			Vertex<ElementoDecorado<Tenista>> tenista2, Edge<ElementoDecorado<Partido>> partido) {
		if (tabla.get(tenista1) == null)
			tabla.put(tenista1, new Calculo());
		if (tabla.get(tenista2) == null)
			tabla.put(tenista2, new Calculo());
		tabla.get(tenista1).diferentes.add(tenista2);
		tabla.get(tenista1).rango = Integer.parseInt("0" + partido.getElement().getElement().rangoGanador);
		tabla.get(tenista2).diferentes.add(tenista1);
		tabla.get(tenista2).rango = Integer.parseInt("0" + partido.getElement().getElement().rangoPerdedor);
	}
	
	
	//M�TODO crearGrafo:
	/********************************************************************************************************************************
	*
	* Method name: crearGrafo()
	* Description of the Method: Este m�todo es el encargado de crear el grafo a trav�s del fichero atp_matches_2020.csv.
	* 							 En primer lugar, se omite el torneo "Davis Cup" y se relacionan las columnas del fichero, con los atributos de 
	* 							 las clases Tenista y Partido, por ejemplo, la columna 2 corresponde al nombre del tenista ganador, por lo que 
	* 							 se almacenar� en el tenista llamado datosGanador.
	* Calling arguments: Vector<String[]> partidos, partidos de nuestro fichero.
	* Return value: Devuelve el grafo creado correctamente.
	*  
	********************************************************************************************************************************/
	public static Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> crearGrafo(Vector<String[]> partidos) {
		Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> g = new TreeMapGraph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>>();
		for (String[] columna: partidos) {
			if (columna[0].startsWith("Davis Cup")) continue;
			Tenista datosGanador = new Tenista(columna[2], columna[3], columna[4], columna[8]);
			Tenista datosPerdedor = new Tenista(columna[5], columna[6], columna[7], columna[10]);
			ElementoDecorado<Tenista> nombreGanador = new ElementoDecorado<Tenista>(columna[2], datosGanador);
			ElementoDecorado<Tenista> nombrePerdedor = new ElementoDecorado<Tenista>(columna[5], datosPerdedor);
			ElementoDecorado<Partido> datosPartido = new ElementoDecorado<Partido>(columna[0] + " " + columna[1],
					new Partido(columna[0], columna[1], columna[8], columna[9] , columna[10], columna[11], columna[4], columna[7], datosGanador, datosPerdedor));
			Vertex<ElementoDecorado<Tenista>> vertexGanador = g.getVertex(columna[2]), vertexPerdedor = g.getVertex(columna[5]);
			if (vertexGanador == null) vertexGanador = g.insertVertex(nombreGanador);
			if (vertexPerdedor == null)	vertexPerdedor = g.insertVertex(nombrePerdedor);
			g.insertEdge(vertexGanador, vertexPerdedor, datosPartido);
		}
		return g;
	}
	
	
	//M�TODO cargarFichero:
	/********************************************************************************************************************************
	*
	* Method name: cargarFichero()
	* Description of the Method: Este m�todo carga el fichero atp_matches_2020.csv
	* Calling arguments: String nombre, nombre del fichero que queremos cargar.
	* Return value: Devuelve el archivo cargado correctamente.
	*  
	********************************************************************************************************************************/
	public static Vector<String[]> cargarFichero(String nombre) throws Exception {
		Vector<String[]> listado = new Vector<String[]>();
		BufferedReader lector = new BufferedReader(new FileReader(new File(nombre)));
		String columna = lector.readLine(); 
		while ((columna = lector.readLine()) != null) {
			String splitted[] = columna.split(";");
			String full[] = new String[12];
			for (int i = 0; i < 12; i++)
				if (i >= splitted.length) {
					full[i] = "";
				} else {
					full[i] = splitted[i];
				}
			listado.add(full);
		}
		lector.close();
		return listado;
	}
	
	
	//M�TODO buscarDFS:
	/********************************************************************************************************************************
	*
	* Method name: buscarDFS()
	* Description of the Method: Este m�todo busca un camino DFS a trav�s de la llamada al m�todo caminoDFS, donde se especificar�n las 
	* 						     restricciones necesarias para que se devuelva el camino requerido en el apartado c del enunciado.
	* Calling arguments: ElementoDecorado<Partido>> g, que es el grafo del fichero atp_matches_2020
	* 					 ElementoDecorado<Tenista> firstNode, que es el primer nodo del grafo.
	* 					 ElementoDecorado<Tenista> finalNode, que es el �ltimo nodo del grafo.
	* Return value: int, retorna el total de ira de los jugadores.
	*  
	********************************************************************************************************************************/
	public static HashSet<ElementoDecorado<Tenista>> buscarDFS(Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> g, ElementoDecorado<Tenista> firstNode, 
																ElementoDecorado<Tenista> finalNode) {
		Vertex<ElementoDecorado<Tenista>> u, origen = null, destino = null;
		Vertex<ElementoDecorado<Tenista>>[] vector;
		ElementoDecorado<Tenista> z;
		HashSet<ElementoDecorado<Tenista>> r = new HashSet<ElementoDecorado<Tenista>>();
		Iterator<Vertex<ElementoDecorado<Tenista>>> it;
		Stack<Edge<ElementoDecorado<Partido>>> pila1 = new Stack(), pila2 = new Stack();
		boolean noOrigen = true, noDestino = true, camino;
		
		it = g.getVertices();
		while (it.hasNext() && (noOrigen || noDestino)) {
			u = it.next();
			z = u.getElement();
			if (z.equals(firstNode)) {
				origen = u;
				noOrigen = false;
			} else if (z.equals(finalNode)) {
				destino = u;
				noDestino = false;
			}
		}
		
		if (!(noOrigen || noDestino)) {
			camino = caminoDFS(g, origen, destino, pila1, firstNode.getElement().mano, finalNode.getElement().mano, firstNode, finalNode);
			if (!camino) {
				while(!pila1.isEmpty()) {
					pila2.push(pila1.pop());
				}
				while (!pila2.isEmpty()) {
					vector = g.endVertices(pila2.pop());
					r.add(vector[0].getElement());
					r.add(vector[1].getElement());
				}
				return r;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	//M�TODO buscarDFS:
	/***********************************************************************************************************************************************
	*
	* Method name: caminoDFS()
	* Description of the Method: En este m�todo se especifican las restricciones necesarias para formar un equipo en el apartado c, de manera que, 
	* 							 usando la b�squeda en profundidad, se formar� un camino entre aquellos jugadores que jueguen con la misma mano que 
	* 							 alguno de los dos capitanes, y que sun n�mero de ranking sea inferior al de los dos capitanes.
	* 							 Los capitanes ser� introducidos por teclado haciendo uso del m�todo LeerCapitan().
	* Calling arguments: ElementoDecorado<Partido>> g, que es el grafo del fichero atp_matches_2020.
	* 					 Vertex<ElementoDecorado<Tenista>> origen, que es el nodo del que se parte para formar el grafo.
	* 					 Vertex<ElementoDecorado<Tenista>> destino, que es el nodo al que hay que llegar para formar el grafo.
	* 					 Stack<Edge<ElementoDecorado<Partido>>> pila, que es la pila donde se almacenar�n los jugadores que cumplan las restricciones 
	* 															necesarias para formar parte del equipo
	* 					 String manoOrigen, que es la mano con la que juega el primer capit�n.
	* 					 String manoDestino, que es la mano con la que juega el segundo capit�n.
	* 					 ElementoDecorado<Tenista> firstNode, que es el primer nodo del grafo.
	* 					 ElementoDecorado<Tenista> finalNode, que es el �ltimo nodo del grafo.
	* Return value: boolean, retorna noEnd, que es la variable que nos indica que el origen es distinto al destino.
	*  
	*************************************************************************************************************************************************/
	public static boolean caminoDFS(Graph<ElementoDecorado<Tenista>, ElementoDecorado<Partido>> g, Vertex<ElementoDecorado<Tenista>> origen, Vertex<ElementoDecorado<Tenista>> destino, 
									Stack<Edge<ElementoDecorado<Partido>>> pila, String manoOrigen, String manoDestino, ElementoDecorado<Tenista> firstNode, ElementoDecorado<Tenista> finalNode) {

		Vertex<ElementoDecorado<Tenista>> vertex;
		Edge<ElementoDecorado<Partido>> edge;
		Iterator<Edge<ElementoDecorado<Partido>>> it;
		boolean noEnd = !origen.equals(destino);
		
		origen.getElement().setVisited(true);
		it = g.incidentEdges(origen);
		while (it.hasNext() && noEnd) {
			edge = it.next();
			vertex = g.opposite(origen, edge);
			Tenista t = vertex.getElement().getElement();
			boolean mismaMano = t.mano.equalsIgnoreCase(manoOrigen) || t.mano.equalsIgnoreCase(manoDestino);
			
			boolean menorRango = false;
			String rangoInicial = firstNode.getElement().rango; 
			String rangoFinal = finalNode.getElement().rango;
			
			String rango = edge.getElement().getElement().rangoGanador;
			
			
			try {
				menorRango = Integer.parseInt(rango) < Integer.parseInt(rangoInicial) && Integer.parseInt(rango) < Integer.parseInt(rangoFinal); 
		    } catch (NumberFormatException exception) {}
			
			if (!vertex.getElement().getVisited() && (mismaMano && menorRango)) {
				pila.push(edge);
				noEnd = caminoDFS(g, vertex, destino, pila, manoOrigen, manoDestino, firstNode, finalNode);
				if (noEnd)
					pila.pop();
			}
		}
		return noEnd;
	}

	//M�TODO buscarBFS:
	/**********************************************************************************************************************************
	*
	* Method name: buscarBFS()
	* Description of the Method: Este m�todo busca un camino BFS a trav�s de la llamada al m�todo caminoBFS, donde se especificar�n las 
	* 						     restricciones necesarias para que se devuelva el camino requerido en el apartado b del enunciado.
	* Calling arguments: Graph g, que es el grafo.
	* 					 ElementoDecorado<Tenista> firstNode, que es el primer nodo del grafo.
	* 					 ElementoDecorado<Tenista> finalNode, que es el �ltimo nodo del grafo.
	* Return value: Retorna la pila, Stack<ElementoDecorado<Tenista>>.
	*  
	***********************************************************************************************************************************/
	public static Stack<ElementoDecorado<Tenista>> buscarBFS(Graph g, ElementoDecorado<Tenista> firstNode, ElementoDecorado<Tenista> finalNode) {		
		Stack<ElementoDecorado<Tenista>> pila = new Stack<ElementoDecorado<Tenista>>();
		Vertex<ElementoDecorado> v, origen = null, destino = null;
		ElementoDecorado z, decoratedElement = null;
		Iterator<Vertex<ElementoDecorado>> it;
		boolean noOrigen = true, noDestino = true;
		
		it = g.getVertices();
		while (it.hasNext() && (noOrigen || noDestino)) {
			v = it.next();
			z = v.getElement();
			if (z.equals(firstNode)) {
				origen = v;
				noOrigen = false;
			}
			if (z.equals(finalNode)) {
				destino = v;
				noDestino = false;
			}
		}
		if (!(noOrigen || noDestino)) {
			decoratedElement = caminoBFS(g, origen, destino, firstNode.getElement().pais, finalNode.getElement().pais);
			if (decoratedElement.getParent() != null) {
				while (decoratedElement.getParent() != null) {
					pila.push(decoratedElement);
					decoratedElement = decoratedElement.getParent();
				}
				pila.push(decoratedElement);
				return pila;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
	
	//M�TODO caminoBFS:
	/*********************************************************************************************************************************************
	*
	* Method name: caminoBFS()
	* Description of the Method: En este m�todo se especifican las restricciones necesarias para formar un equipo en el apartado b, de manera que, 
	* 							 usando la b�squeda en anchura, se formar� un camino entre aquellos jugadores que tengan m�s de 1500 puntos en su
	* 							 ranking. Adem�s, aquellos jugadores que est�n conectados no podr�n pertenecer al mismo pa�s.
	* 							 Los capitanes ser� introducidos por teclado haciendo uso del m�todo LeerCapitan().
	* Calling arguments: Graph g, que es el grafo formado siguiendo las restricciones anteriores.
	* 					 Vertex<ElementoDecorado> origen, que es el nodo del que se parte para formar el grafo.
	* 					 Vertex<ElementoDecorado> destino, que es el nodo al que hay que llegar para formar el grafo.
	* 					 String paisOrigen, que es el pais de origen del primer capit�n.
	* 					 String paisDestino, que es el pais de origen del segundo capit�n.
	* Return value: Devuelve cada v�rtice que cumple las restricciones necesarias para formar parte del equipo.
	*  
	*********************************************************************************************************************************************/
	public static ElementoDecorado caminoBFS(Graph g, Vertex<ElementoDecorado> origen, Vertex<ElementoDecorado> destino, String paisOrigen, String paisDestino) {
		Queue<Vertex<ElementoDecorado>> q = new LinkedList();
		Vertex<ElementoDecorado> u, v = null;
		
		Edge<ElementoDecorado<Partido>> ed;
		Iterator<Edge<ElementoDecorado<Partido>>> it;
		boolean noEnd = true;
		
		origen.getElement().setVisited(true);
		q.offer(origen);
		while (!q.isEmpty() && noEnd) {
			u = q.remove();
			it = g.incidentEdges(u);
			while (it.hasNext() && noEnd) {
				ed = it.next();
				v = g.opposite(u, ed);
				String rango = ed.getElement().getElement().puntosGanador;
				boolean rangoMayor = rango.length() > 0 && Integer.parseInt(rango) >= 1500;
				boolean pais = ed.getElement().getElement().paisGanador != ed.getElement().getElement().paisPerdedor;
				
				if (!v.getElement().getVisited() && rangoMayor && pais) {
					(v.getElement()).setVisited(true);
					(v.getElement()).setParent(u.getElement());
					(v.getElement()).setDistance(((u.getElement()).getDistance()) + 1);
					q.offer(v);
					noEnd = !(v.getElement().equals(destino.getElement()));
				}
			}
		}
		if (noEnd) {
			v.getElement().setParent(null);
		}
		return v.getElement();
	}

	

}


