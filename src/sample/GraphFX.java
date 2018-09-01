package sample;

import java.util.ArrayList;

public class GraphFX {

    private ArrayList<myNode> nodes;
    //private ArrayList<ArrayList<myConn>> weights;
    private myConn weights[][];
    private int dimension;
    private int conns;
    private int capacity;

    public GraphFX(int dim) {
        capacity=dim;
        dimension=0;
        conns=0;
        nodes = new ArrayList<myNode>(dim);
        weights= new myConn[dim][dim];
    }

    public ArrayList<myNode> getNodes() {
        return nodes;
    }

    public int getConns() {
        return conns;
    }

    public int getDimension() {
        return dimension;
    }

    public boolean addNode() {
        if (dimension<capacity) {
            myNode temp= new myNode(capacity,dimension);
            nodes.add(dimension,temp);
            dimension++;
            return true;
        }
        else return false;
    }

    public void addNode(double X, double Y) {
        if (dimension<capacity) {
            myNode temp= new myNode(capacity,dimension, X, Y);
            nodes.add(dimension, temp);
            dimension++;
        }
        //else doubleArray(), addNode()
    }

    public myConn addConnection(int a, int b) {
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

    public myConn addRandomConnection() {
        int sel=(int) (Math.random()*(dimension-1));
        int sel2=(int) (Math.random()*(dimension-1));
        myConn temp;
        if (sel<sel2) {
            if (!nodes.get(sel).getConnection(sel2)) {
                temp=addConnection(sel,sel2);
                conns=conns+1;
                return temp;
            }
            else addRandomConnection();
        }
        else if (sel!=sel2){
            if (!nodes.get(sel2).getConnection(sel)) {
                temp=addConnection(sel2,sel);
                conns=conns+1;
                return temp;
            }
            else addRandomConnection();
        }
        return null;
    }

    public int rmConnection(int a, int b) {
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

    public void rmConnection(int a) {
        ArrayList<myNode> temp = adjs(a);
        int i=0;
        while (i<temp.size()) {
            rmConnection(a, temp.get(i).getPos());
        }
    }

    public myNode removeNode(int id) {
        if (id<dimension) {
            rmConnection(id);
            myNode out = nodes.get(id).removeNode();
            for (int i=0;i<dimension;i++) {
                nodes.get(i).removeConnection(id);
            }
            nodes.remove(id);
            dimension--;
            return out;
        }
        else return null;
    }

    public boolean isEmpty() {
        return dimension==0;
    }

    public ArrayList<myNode> adjs(int id) {
        ArrayList<myNode> out=null;
        for (int i=0; i<dimension; i++) {
            if (nodes.get(id).isAdj(i)) out.add(nodes.get(i));
        }
        out.remove(0);
        return out;
    }

    public int getConnection(int a, int b) {
        if (a < b) return weights[a][b].getWeight();
        else return weights[b][a].getWeight();
    }

    public myConn getConnectionObj(int a, int b) {
        if (a < b) return weights[a][b];
        else return weights[b][a];
    }

    public ArrayList<myNode> johnsonAlg(myNode r) {
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

    public void randomGraph(int size) {
        for (int i=0;i<size;i++) {
            double x = (Math.random()*501);
            double y = (Math.random()*501);
            addNode(x,y);
        }
        int edges = (int)(Math.random()*size*(size-1));
        int i=0;
        while (i<edges) {
            addRandomConnection();
            i++;
        }
    }

}
