package sample;

import java.util.Vector;

public class PriorityHeap {
    private int dimension;
    private int capacity;
    private Vector<myNode> H;

    public PriorityHeap(int n) {
        capacity=n;
        dimension=0;
        H= new Vector<myNode>(n);
        for (int i=0; i<n; i++) {
            H.get(i).setPriority(0);
            H.get(i).setPos(i);
        }
    }

    public myNode min() {
        if (dimension>0) return H.get(0);
        else return null;
    }

    public myNode insert(myNode x, int p) {
        if (dimension<capacity) {
            dimension=dimension+1;
            H.add(dimension, x);
            H.get(dimension).setPriority(p);
            int i=dimension;
            while ((i>1)&&(H.get(i).getPriority()<H.get((int)1/2).getPriority())) {
                myNode temp=H.get(i);
                H.insertElementAt(H.get((int)i/2),i);
                H.insertElementAt(temp,(int)i/2);
                H.get(i).setPos(i);
                H.get((int)i/2).setPos((int)i/2);
                i=(int)i/2;
            }
            return H.get(i);
        }
        else return null;
    }

    private void minHeapRestore(Vector<myNode> A, int i, int dim) {
        int min =i;
        if ((2*i<=dim)&&(A.get(2*i).getPriority()<A.get(min).getPriority())) min=2*i;
        if ((2*i+1<=dim)&&(A.get(2*i+1).getPriority()<A.get(min).getPriority())) min=2*i+1;
        if (i!=min) {
            myNode temp=H.get(i);
            H.insertElementAt(H.get(min),i);
            H.insertElementAt(temp,min);
            H.get(i).setPos(i);
            H.get(min).setPos(min);
            minHeapRestore(A,min,dim);
        }
    }

    public myNode deleteMin() {
        if (dimension>0) {
            myNode temp=H.get(1);
            H.insertElementAt(H.get(dimension),1);
            H.insertElementAt(temp,dimension);
            H.get(1).setPos(1);
            H.get(dimension).setPos(dimension);
            dimension=dimension-1;
            minHeapRestore(H,1,dimension);
            return H.get(dimension+1);
        }
        else return null;
    }

    public void decrease(myNode x, int p) {
        if (p< x.getPriority()) {
            x.setPriority(p);
            int i = x.getPos();
            while ((i > 1) && (H.get(i).getPriority() < H.get((int) i / 2).getPriority())) {
                myNode temp = H.get(i);
                H.insertElementAt(H.get((int) i / 2), i);
                H.insertElementAt(temp, (int) i / 2);
                H.get(i).setPos(i);
                H.get((int) i / 2).setPos((int) i / 2);
                i = (int) i / 2;
            }
        }
    }

    public boolean isEmpty() { return dimension==0; }
}
