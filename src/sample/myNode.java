package sample;

import javafx.scene.shape.Circle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class myNode extends Circle {
    private boolean[] connections; // array booleano delle connessioni tra il nodo e gli altri nodi
    private int pos; // id del nodo
    private int priority; // priorità del nodo


    public myNode(int dim, int POS, int PRIORITY) { // costruttore che imposta dimensione dell'array di booleani, id del nodo
        connections = new boolean[dim];				// e priorità dell'oggetto
        pos=POS;
        priority=PRIORITY;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS) { // costruttore che imposta dimensione dell'array di booleani e id del nodo
        connections = new boolean[dim];
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public myNode(int dim, int POS, double X, double Y) { // costruttore che imposta dimensionde dell'array booleano, id del nodo
        connections = new boolean[dim];					  // e posizioni x e y del centro, il raggio è 10 di default
        setCenterX(X);
        setCenterY(Y);
        setRadius(10.0);
        pos=POS;
        priority=0;
        for (int i=0; i<dim; i++) {
            connections[i]=false;
        }
    }

    public void setPosition(int x, int y) { // imposta x e y come coordinate del centro del nodo
        setCenterX(x);
        setCenterY(y);
    }

    public void saveNode(OutputStream stream) throws IOException // salva il nodo nell outputFileStream
    {
        stream.write(pos);
        stream.write(priority);
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(getCenterX());
        stream.write(bytes);
        ByteBuffer.wrap(bytes).putDouble(getCenterY());
        stream.write(bytes);
        for (boolean item : connections)
        {
            stream.write(item ? 1 : 0);
        }
    }

    public void loadNode(InputStream stream, int lenght) throws IOException // carica nodo da fileInputStream, prende come parametro la lunghezza dell'array
    {																			// di booleani da estrarre
        byte[] data = new byte[lenght];
        byte[] bytes = new byte[8];
        pos=stream.read();
        priority=stream.read();
        stream.read(bytes,0,8);
        setCenterX(ByteBuffer.wrap(bytes).getDouble());
        stream.read(bytes,0,8);
        setCenterY(ByteBuffer.wrap(bytes).getDouble());
        setRadius(10.0);
        stream.read(data,0,lenght);
        for (int X = 0; X < lenght; X++)
        {
            if (data[X] != 0)
            {
                connections[X] = true;
            }
            else connections[X] = false;
        }
     }


    public int getPos() { return  pos; } // risponde con id del nodo

    public int getPriority() { return priority; } // risponde con priorità del nodo

    public void setPriority(int PRIORITY) { priority=PRIORITY; } // definisce la priorità del nodo

    public void setPos(int POS) { pos=POS; } // definisce l'id del nodo

    public boolean hasConnection() { // ritorna true se il nodo ha almeno una connessione, false altrimenti
    	for (int i=0; i<connections.length; i++) {
    		if (isAdj(i)) return true;
    	}
    	return false;
    }

    public void addConnection(int adj) { // aggiunge una connessione tra il nodo corrente e il nodo "adj"
        connections[adj]=true;
    }

    public void removeConnection(int id) { // rimuove la connessione tra il nodo corrente e il nodo "id"
        connections[id]=false;
    }

    public boolean isAdj(int b) { // ritorna true se il nodo è adiacente al nodo "sel" false altrimenti
        return connections[b];
    }
}
