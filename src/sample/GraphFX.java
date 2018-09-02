package sample;

import java.util.ArrayList;

public class GraphFX {

    private ArrayList<myNode> nodes; // arrayList dei nodi
    private myConn weights[][]; // matrice delle linee 
    private int dimension; // dimensione attuale del grafo (numero di nodi)
    private int conns; // numero attuale di connessioni
    private int capacity; // capacità totale del grafo

    public GraphFX(int dim) { // costruttore generico che imposta la dimensione massima del grafo
        capacity=dim;
        dimension=0;
        conns=0;
        nodes = new ArrayList<myNode>(dim);
        weights= new myConn[dim][dim];
    }

    public ArrayList<myNode> getNodes() { // ritorna l'array con tutti i nodi del grafo
        return nodes;
    }

    public int getConns() { // ritorna il numero di connessioni
        return conns;
    }

    public int getDimension() { // ritorna la dimensione attuale del grafo
        return dimension;
    }

    public boolean addNode() { // aggiunge un nodo generico, risponde false solo se il grafo ha raggiunto la dimensione massima
        if (dimension<capacity) {
            myNode temp= new myNode(capacity,dimension);
            nodes.add(dimension,temp);
            dimension++;
            return true;
        }
        else return false;
    }

    public boolean addNode(double X, double Y) { // aggiunge un nodo di coordinate x e y, risponde false solo se il grafo ha raggiunto la dimensione massima
        if (dimension<capacity) {
            myNode temp= new myNode(capacity,dimension, X, Y);
            nodes.add(dimension, temp);
            dimension++;
            return true;
        }
        else return false;
    }

    public myConn addConnection(int a, int b) { // aggiunge una connessione tra i nodi "a" e "b", calcola anche il peso di questa in base alle coordinate dei nodi
        int weight= (int)Math.sqrt(((nodes.get(a).getCenterX()-nodes.get(b).getCenterX())*(nodes.get(a).getCenterX()-nodes.get(b).getCenterX()))+((nodes.get(a).getCenterY()-nodes.get(b).getCenterY())*(nodes.get(a).getCenterY()-nodes.get(b).getCenterY())));
        myConn newconn =new myConn(weight);
        newconn.setCoords(nodes.get(a).getCenterX(),nodes.get(a).getCenterY(),nodes.get(b).getCenterX(),nodes.get(b).getCenterY());
        myConn[] tmp= new myConn[dimension];
        if (a<b) {
            tmp[b]=newconn;
            weights[a]=tmp;
            weights[a][b].setCoords(nodes.get(a).getCenterX(),nodes.get(a).getCenterY(),nodes.get(b).getCenterX(),nodes.get(b).getCenterY());
            nodes.get(a).addConnection(b);
            nodes.get(b).addConnection(a);
        }
        else if (a!=b){
            tmp[a]=newconn;
            weights[b]=tmp;
            weights[b][a].setCoords(nodes.get(b).getCenterX(),nodes.get(b).getCenterY(),nodes.get(a).getCenterX(),nodes.get(a).getCenterY());
            nodes.get(a).addConnection(b);
            nodes.get(b).addConnection(a);
        }
        conns=conns+1;
        return newconn;
    }

    public myConn addRandomConnection() { // aggiunge una connessione tra due nodi scelti a caso, restituisce la connessione
        int sel=(int) (Math.random()*(dimension-1));
        int sel2=(int) (Math.random()*(dimension-1));
        myConn temp;
        if (sel<sel2) {
            if (!nodes.get(sel).isAdj(sel2)) {
                temp=addConnection(sel,sel2);
                conns=conns+1;
                return temp;
            }
            else addRandomConnection();
        }
        else if (sel!=sel2){
            if (!nodes.get(sel2).isAdj(sel)) {
                temp=addConnection(sel2,sel);
                conns=conns+1;
                return temp;
            }
            else addRandomConnection();
        }
        return null;
    }

    public int rmConnection(int a, int b) { // rimuove la connessione tra i nodi "a" e "b", ne restituisce il peso
        int out;
        if (a<b) {
            out=weights[a][b].getWeight();
            nodes.get(a).removeConnection(b);
            nodes.get(b).removeConnection(a);
        }
        else {
            out=weights[b][a].getWeight();
            nodes.get(a).removeConnection(b);
            nodes.get(b).removeConnection(a);
        }
        return out;
    }

    public void rmConnection(int a) { // rimuove ogni connessione del nodo "a"
        ArrayList<myNode> temp = adjs(a);
        int i=0;
        while (i<temp.size()) {
            rmConnection(a, temp.get(i).getPos());
        }
    }

    public myNode removeNode(int id) { // rimuove (se esiste) il nodo "id", altrimenti ritorna null
        if (id<dimension) {
            rmConnection(id);
            myNode out = nodes.get(id);
            for (int i=0;i<dimension;i++) {
                nodes.get(i).removeConnection(id);
            }
            nodes.remove(id);
            dimension--;
            return out;
        }
        else return null;
    }

    public boolean isEmpty() { // ritorna true se il grafo è vuoto, false altrimenti
        return dimension==0;
    }

    public ArrayList<myNode> adjs(int id) { // ritorna un array di nodi contenenti tutti i nodi adiacenti al nodo "id"
        ArrayList<myNode> out=new ArrayList<myNode>(dimension);
        for (int i=0; i<dimension; i++) {
            if (nodes.get(id).isAdj(i)) out.add(nodes.get(i));
        }
        return out;
    }

    public int getConnection(int a, int b) { // ritorna il peso della connesssione tra "a" e "b", assume che il chiamante ne abbia verificato l'esistenza
        if (a < b) return weights[a][b].getWeight();
        else return weights[b][a].getWeight();
    }

    public myConn getConnectionObj(int a, int b) { // ritona l'oggetto connessione tra i nodi "a" e "b", assume che il chiamante ne abbia verificato l'esistenza
        if (a < b) return weights[a][b];
        else return weights[b][a];
    }

    public ArrayList<myNode> johnsonAlg(myNode r) { // applica l'algoritmo di johnson al grafo, restituisce l'array dei nodi ordinati per visita
        PriorityHeap S = new PriorityHeap(dimension);
        S.insert(r,0);
        ArrayList<myNode> T =new ArrayList<myNode>(dimension);
        T.clear();
        int[] d =new int[dimension];
        boolean[] b = new boolean[dimension];
        for (int i=0; i<dimension; i++) {
            d[i]=1048576;
            b[i]=false;
        }
        d[r.getPos()]=0;
        b[r.getPos()]=true;
        while (!(S.isEmpty())) {
            myNode u=S.deleteMin();
            b[u.getPos()]=false;
            ArrayList<myNode> adjacent =adjs(u.getPos());
            int i=0;
            while (i<adjacent.size()) {
                if (d[u.getPos()]+getConnection(u.getPos(),adjacent.get(i).getPos())<d[adjacent.get(i).getPos()]) {
                    if (!(b[adjacent.get(i).getPos()])) {
                        S.insert(adjacent.get(i),d[u.getPos()]+getConnection(u.getPos(),adjacent.get(i).getPos()));
                        b[adjacent.get(i).getPos()]=true;
                    }
                    else {
                        S.decrease(adjacent.get(i),d[u.getPos()]+getConnection(u.getPos(),adjacent.get(i).getPos()));
                    }
                    T.add(adjacent.get(i).getPos(),u);
                    d[adjacent.get(i).getPos()]=d[u.getPos()]+getConnection(u.getPos(),adjacent.get(i).getPos());
                }
            }
        }
        return T;
    }

    public void randomGraph(int size) { // costruisce un grafo casuale di dimensione "size"
        for (int i=0;i<size;i++) {
            double x = (Math.random()*709+46);
            double y = (Math.random()*402+60);
            addNode(x,y);
        }
        for (int i=0; i<size;i++) {
        	if (!nodes.get(i).hasConnection()) {
	        	int mate=i;
	        	while ((mate==i)&&(nodes.get(i).isAdj(mate))) {
	        		mate= (int)(Math.random()*size);
	        	}
	        	addConnection(i,mate);
        	}
        }
        /*int edges = (int)(Math.random()*size);
        int i=0;
        while (i<edges) {
            addRandomConnection();
            i++;
        }*/
    }

}
