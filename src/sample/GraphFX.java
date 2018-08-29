package sample;

import java.util.Vector;

public class GraphFX {

    private Vector<myNode> nodes;
    private Vector<Vector<myConn>> weights;
    private int dimension;
    private int capacity;

    public GraphFX(int dim) {
        capacity=dim;
        dimension=0;
        myConn e=new myConn(1048576);
        for (int i=0; i<dim; i++) {
            myNode pippo= new myNode(dim,i);
            nodes.add(i,pippo);
            for (int j=i+1; j<dim; j++) {
                Vector<myConn> temp = new Vector<myConn>();
                temp.add(j,e);
                weights.add(i,temp);
            }
        }
    }

    public void addNode() {
        if (dimension<capacity) {
            myNode temp= new myNode(capacity,dimension);
            nodes.add(dimension, temp);
        }
        //else doubleArray(), addNode()
    }

    public void addConnection(int a, int b, int weight) {
        myConn newconn =new myConn(weight);
        Vector<myConn> tmp= new Vector<myConn>();
        if (a<b) {
            tmp.add(b,newconn);
            weights.add(a,tmp);
            nodes.get(a).addConnection(b);
            nodes.get(b).addConnection(a);
        }
        else {
            tmp.add(a,newconn);
            weights.add(b,tmp);
            nodes.get(a).addConnection(b);
            nodes.get(b).addConnection(a);
        }
    }

    public int rmConnection(int a, int b) {
        int out;
        if (a<b) {
            out=weights.get(a).remove(b).getWeight();
            nodes.get(a).removeConnection(b);
            nodes.get(b).removeConnection(a);
        }
        else {
            out=weights.get(b).remove(a).getWeight();
            nodes.get(a).removeConnection(b);
            nodes.get(b).removeConnection(a);
        }
        return out;
    }

    public void rmConnection(int a) {
        Vector<myNode> temp = adjs(a);
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

    public Vector<myNode> adjs(int id) {
        Vector<myNode> out=null;
        for (int i=0; i<dimension; i++) {
            if (nodes.get(id).isAdj(i)) out.add(nodes.get(i));
        }
        out.remove(0);
        return out;
    }

    public int getConnection(int a, int b) {
        if (a < b) return weights.get(a).get(b).getWeight();
        else return weights.get(b).get(a).getWeight();
    }

    public Vector<myNode> johnsonAlg(myNode r) {
        PriorityHeap S = new PriorityHeap(dimension);
        S.insert(r,0);
        Vector<myNode> T =new Vector<myNode>(dimension);
        T.removeAllElements();
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
            Vector<myNode> adjacent =adjs(u.getPos());
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

}
