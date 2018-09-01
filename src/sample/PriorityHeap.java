package sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PriorityHeap {
    private int dimension;
    private int capacity;
    private ArrayList<myNode> H;

    public PriorityHeap(int n) {
        capacity=n;
        dimension=0;
        H= new ArrayList<myNode>(n);
        for (int i=0; i<n; i++) {
            myNode tmp = new myNode(n,i,0);
            H.add(i, tmp);
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
                H.add(i, H.get((int)i/2));
                H.add((int)i/2, temp);
                H.get(i).setPos(i);
                H.get((int)i/2).setPos((int)i/2);
                i=(int)i/2;
            }
            return H.get(i);
        }
        else return null;
    }

    private void minHeapRestore(ArrayList<myNode> A, int i, int dim) {
        int min =i;
        if ((2*i<=dim)&&(A.get(2*i).getPriority()<A.get(min).getPriority())) min=2*i;
        if ((2*i+1<=dim)&&(A.get(2*i+1).getPriority()<A.get(min).getPriority())) min=2*i+1;
        if (i!=min) {
            myNode temp=H.get(i);
            H.add(i, H.get(min));
            H.add(min, temp);
            H.get(i).setPos(i);
            H.get(min).setPos(min);
            minHeapRestore(A,min,dim);
        }
    }

    public myNode deleteMin() {
        if (dimension>0) {
            myNode temp=H.get(1);
            H.add(1,H.get(dimension));
            H.add(dimension, temp);
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
                H.add(i, H.get((int) i / 2));
                H.add((int) i / 2, temp);
                H.get(i).setPos(i);
                H.get((int) i / 2).setPos((int) i / 2);
                i = (int) i / 2;
            }
        }
    }

    private java.lang.String getLatestNumber() {
        int i=0;
        File tmp = new File("./data/PH"+Integer.toString(i)+".ph");
        while (tmp.exists()) {
            i++;
            tmp = new File("./data/PH"+Integer.toString(i)+".ph");
        }
        return Integer.toString(i);
    }

    public boolean writeToFile() {
        try (FileOutputStream output = new FileOutputStream(new File("./data/PH"+getLatestNumber()+".ph"));) {
            output.write(dimension);
            output.write(capacity);
            for (int i=0;i<dimension; i++) {
                H.get(i).saveNode(output);
            }
        } catch (IOException x) {
            System.err.println(x);
            return false;
        }
        return true;
    }

    public boolean readFromFile(int select) {
        try (FileInputStream input = new FileInputStream(new File("./data/PH"+Integer.toString(select)+".ph"));) {
            dimension = input.read();
            capacity = input.read();
            for (int i = 0; i < dimension; i++) {
                H.get(i).loadNode(input, dimension);
            }
        } catch (IOException x) {
            System.err.println(x);
            return false;
        }
        return true;
    }

    public boolean isEmpty() { return dimension==0; }
}
