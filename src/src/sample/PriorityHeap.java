package sample;

import java.util.ArrayList;

public class PriorityHeap {
    private int dimension;
    private int capacity;
    private ArrayList<myNode> H;

    public PriorityHeap(int n) {
        capacity=n;
        dimension=0;
        H= new ArrayList<myNode>(n);
        H.clear();
        H.add(0,null);
    }
    
    public myNode min() {
        if (dimension>0) return H.get(1);
        else return null;
    }

    public myNode insert(myNode x, int p) {
        if (dimension<capacity) {
        	System.out.println("inizio insert pos="+x.getPos());
        	dimension=dimension+1;
            H.add(dimension, x);
            H.get(dimension).setPriority(p);
            int i=dimension;
            while ((i>1)&&(H.get(i).getPriority()<H.get((int)i/2).getPriority())) {
                swap(i, (int) i / 2);
                i=(int)i/2;
            }
            System.out.println("fine insert pos="+H.get(i).getPos());
            return H.get(i);
        }
        else return null;
    }

    private void minHeapRestore(ArrayList<myNode> A, int i, int dim) {
        int min =i;
        if ((2*i<=dim)&&(A.get(2*i).getPriority()<A.get(min).getPriority())) min=2*i;
        if ((2*i+1<=dim)&&(A.get(2*i+1).getPriority()<A.get(min).getPriority())) min=2*i+1;
        if (i!=min) {
            swap(i, min);
            minHeapRestore(A,min,dim);
        }
    }

    public myNode deleteMin() {
        if (dimension>0) {
            swap(1, dimension);
            dimension=dimension-1;
            minHeapRestore(H,1,dimension);
            System.out.println("In deletemin pos="+H.get(dimension+1).getPos());
            return H.get(dimension+1);
        }
        else return null;
    }

    public void decrease(myNode x, int p) {
        if (p< x.getPriority()) {
            x.setPriority(p);
            int i = getPosition(x);
            while ((i > 1) && (H.get(i).getPriority() < H.get((int) i / 2).getPriority())) {
                swap(i, (int) i /2);
                i = (int) i / 2;
            }
        }
    }

    private void swap(int i, int j) {
    	myNode temp= H.get(i);
    	H.set(i, H.get(j));
    	H.set(j, temp);
    	temp=null;
    }
    
    private int getPosition(myNode x) {
    	int i=1;
    	while (H.get(i)!=x) i++;
    	return i;
    }
    
    /*private java.lang.String getLatestNumber() {
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
    }*/

    public boolean isEmpty() { return dimension==0; }
}
