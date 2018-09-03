package sample;

import java.util.ArrayList;

public class JohnsonAlg {
	
	private PriorityHeap S;
	private ArrayList<myNode> T;
	private int[] d;
    private boolean[] b;
    private int dimension;
    private myNode u;

    
    public JohnsonAlg(myNode r, int dim) {
    	dimension=dim;
    	S = new PriorityHeap(dim);
    	T = new ArrayList<myNode>(dim);
    	d = new int[dim];
    	b = new boolean[dim];
    	u=null;
    	S.insert(r, 0);
    	for (int i=0; i<dimension; i++) {
            d[i]=1048576;
            b[i]=false;
        }
    	d[r.getPos()]=0;
        b[r.getPos()]=true;
    }
    
    public myNode firstIterator() {
    	u=S.deleteMin();
    	b[u.getPos()]=false;
    	return u;
    }
    
    public void secondIterator(myNode v, int connValue) {
    	if (d[u.getPos()]+connValue<d[v.getPos()]) {
            if (!(b[v.getPos()])) {
                S.insert(v,d[u.getPos()]+connValue);
                b[v.getPos()]=true;
            }
            else {
                S.decrease(v,d[u.getPos()]+connValue);
            }
            T.set(v.getPos(),u);
            d[v.getPos()]=d[u.getPos()]+connValue;
        }
    }
    
    public ArrayList<myNode> algReturn() {
    	return T;
    }
    
    public int getUid() {
    	return u.getPos();
    }
    
    public boolean isSEmpty() {
    	return S.isEmpty();
    }
}
