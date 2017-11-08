package bst;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;

public class BinarySearchTreeTest {

    private BinarySearchTree<Integer> tree;

    @Before
    public void setUp() throws Exception {
        tree = new BinarySearchTree<>();
    }

    @Test
    public void getIndex() throws Exception {
        tree.add(1, 1);
        tree.add(2, 2);
        tree.add(3, 3);
        tree.add(1, 4);

        assertEquals(0, tree.getIndex(1));
        assertEquals(1, tree.getIndex(2));
        assertEquals(2, tree.getIndex(3));
    }

    @Test
    public void add() throws Exception {
        tree.add(1, 1);
    }

    @Test
    public void add_4() throws Exception {
        tree.add(1, 1);
        tree.add(2, 2);
        tree.add(3, 3);
        tree.add(1, 4);
    }

    @Test
    public void add_bln() throws Exception {
        Random random = new Random(7);
        int n = 1000000;

        Map<Integer, Integer> set = new TreeMap<>();
        long startTreeMap = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            set.put(random.nextInt(10000000), random.nextInt(10000000));
        }
        long endTreeMap = System.currentTimeMillis();

        BinarySearchTree<Integer> myTree = new BinarySearchTree<>();
        long myTreeStart = System.currentTimeMillis();
        int a[] = new int[n];
        for (int i = 0; i < n; i++) {
            int value = random.nextInt(n);
            int r = random.nextInt(1 + i);
            a[i] = a[r];
            a[r] = i;
        }

        for (int i = 0; i < n; i++) {
            //myTree.add(random.nextInt(n), random.nextInt(n));
            myTree.add(a[i], a[i]);
        }
        long myTreeEnd = System.currentTimeMillis();

        //assertEquals((endTreeMap - startTreeMap), (myTreeEnd - myTreeStart));

        System.out.println("Max d: " + myTree.getMaxDeepth() + "'Min d: " + myTree.getMinDeepth());
    }

    @Test
    public void iterators() throws Exception {
        int maxP = 20;
        creatingTimes = new long[maxP];
        traverseTimes = new long[maxP];
        for (int i = 0; i < 10; i++) {
            for (int p = 10; p < maxP; p++) {
                this.tree = new BinarySearchTree<>();
                forIterators(p);
            }
        }
        System.out.print("Traverse dynamic: ");
        for (int p = 11; p < maxP; p++) {
            double d = 1.0 * traverseTimes[p] / traverseTimes[p - 1];
            System.out.print(String.format("%.2f ", d));
        }
        System.out.println("");
        System.out.print("Creating dynamic: ");
        for (int p = 11; p < maxP; p++) {
            double d = 1.0 * creatingTimes[p] / creatingTimes[p - 1];
            System.out.print(String.format("%.2f ", d));
        }
    }

    public void forIterators(int p) throws Exception {
        int n = 1 << p;
        long startCreate = System.nanoTime();
        addNumbers(n);
        long endCreate = System.nanoTime();

        long startIterator = System.nanoTime();
        int sum = new Random(7).nextInt(n);
        Iterator<Integer> leftIt = tree.createIterator();
        Iterator<Integer> rightIt = tree.createReverseIterator();
        Integer left = leftIt.next();
        Integer right = rightIt.next();
        int pairs = 0;

        while (left < right) {
            if (left + right == sum) {
                //System.out.println(left + " + " + right + " = " + sum);
                left = leftIt.next();
                right = rightIt.next();
                pairs++;
            } else if (left + right < sum) {
                left = leftIt.next();
            } else {
                right = rightIt.next();
            }
        }
        long endIterator = System.nanoTime();
        //System.out.println("Pairs: " + pairs + "; for " + n);
        //System.out.println((endCreate - startCreate) + " vs  " + (endIterator - startIterator));
        traverseTimes[p] += endIterator - startIterator;
        creatingTimes[p] += endCreate - startCreate;
    }

    private void addNumbers(int n) {
        Random random = new Random(7);
        for (int i = 0; i < n; i++) {
            tree.add(random.nextInt(n), random.nextInt(n));
        }
    }

    long creatingTimes[];
    long traverseTimes[];

}