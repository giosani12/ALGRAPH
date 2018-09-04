package sample;

import java.util.ArrayList;

public class JohnsonAlg {
	
	private PriorityHeap S;
	private ArrayList<myNode> T;
	private int[] d;
    private boolean[] b;
    private myNode u;

    
    public JohnsonAlg(myNode r, int dim) {
    	S = new PriorityHeap(dim+1);
    	T = new ArrayList<myNode>(dim+1);
    	d = new int[dim+1];
    	b = new boolean[dim+1];
    	u=null;
    	S.insert(r, 0);
    	for (int i=0; i<dim+1; i++) {
            d[i]=1048576;
            b[i]=false;
            T.add(i,null);
        }
    	System.out.println("Nodo r="+r.getPos());
    	d[r.getPos()]=0;
        b[r.getPos()]=true;
    }
    
    public myNode firstIterator() {
    	u=S.deleteMin();
    	System.out.println("Preso come minimo "+u.getPos()+ " di priorità " + u.getPriority());
    	b[u.getPos()]=false;
    	return u;
    }
    
    public void secondIterator(myNode v, int connValue) {
    	System.out.println("D= "+d[u.getPos()]+", connValue="+connValue+", D2="+d[v.getPos()]+" e b[v]=" +b[v.getPos()]);
    	if (d[u.getPos()]+connValue<d[v.getPos()]) {
            if (!(b[v.getPos()])) {
            	System.out.println("Aggiunto ad S, pos="+v.getPos());
                S.insert(v,d[u.getPos()]+connValue);
                b[v.getPos()]=true;
            }
            else {
            	System.out.println("Abbassato");
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
