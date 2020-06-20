package bearmaps;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<Node> items;
    private int size;
    private HashMap<T, Integer> map;


    public ArrayHeapMinPQ() {
        size = 0;
        items = new ArrayList<>();
        map = new HashMap<>();
    }

    private class Node {
        private T item;
        private double priority;

        Node(T i, double p) {
            item = i;
            priority = p;
        }
    }

    /* Adds an item with the given priority value. Throws an
     * IllegalArgumentExceptionb if item is already present.
     * You may assume that item is never null. */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("The item is already present");
        } else {
            items.add(new Node(item, priority));
            size++;
            map.put(item, size - 1);
            swim(items.get(size - 1));
        }
    }
    /* Returns true if the PQ contains the given item. */
    @Override
    public boolean contains(T item) {
        return map.containsKey(item);
    }


    /* Returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T getSmallest() {
        return items.get(0).item;
    }
    /* Removes and returns the minimum item. Throws NoSuchElementException if the PQ is empty. */
    @Override
    public T removeSmallest() {
        Collections.swap(items, indOf(items.get(0)), indOf(items.get(size - 1)));
        T l = items.get(0).item;
        T p = items.get(size - 1).item;
        items.remove(items.get(size - 1));
        map.remove(p);
        map.replace(l, 0);
        size--;
        sink(items.get(0));
        return p;
    }
    /* Returns the number of items in the PQ. */
    @Override
    public int size() {
        return size;
    }
    /* Changes the priority of the given item. Throws NoSuchElementException if the item
     * doesn't exist. */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("The element doesn't exist.");
        }

        if (items.get(map.get(item)).priority == priority) {
            return;
        }
        items.get(map.get(item)).priority = priority;

        if (!isRoot(items.get(map.get(item)))) {
            if (priority < parent(items.get(map.get(item))).priority) {
                swim(items.get(map.get(item)));
            }
        } else if (!isLeaf(items.get(map.get(item)))) {
            if (priority > leftChild(items.get(map.get(item))).priority) {
                sink(items.get(map.get(item)));
            } else if (rightChild(items.get(map.get(item))) != null) {
                if (priority > rightChild(items.get(map.get(item))).priority) {
                    sink(items.get(map.get(item)));
                }
            } else {
                return;
            }
        }

        return;
    }

    private void swim(Node item) {
        while (!isRoot(item) && item.priority < parent(item).priority) {
            Node parent = parent(item);
            int k = indOf(item);
            int p = indOf(parent);
            map.replace(parent.item, k);
            map.replace(item.item, p);
            Collections.swap(items, indOf(item), indOf(parent));
            swim(item);
        }
    }

    private void sink(Node item) {
        if (isLeaf(item)) {
            return;
        }

        Node leftChild = leftChild(item);
        Node rightChild = rightChild(item);

        if (rightChild(item) != null) {
            if (rightChild.priority < leftChild.priority) {
                if (rightChild.priority < item.priority) {
                    int k = indOf(item);
                    int p = indOf(rightChild);
                    map.replace(item.item, p);
                    map.replace(rightChild.item, k);
                    Collections.swap(items, indOf(item), indOf(rightChild));
                    sink(item);
                }
            }
        }

        if (leftChild.priority < item.priority) {
            int k = indOf(item);
            int p = indOf(leftChild);
            map.replace(item.item, p);
            map.replace(leftChild.item, k);
            Collections.swap(items, indOf(item), indOf(leftChild));
            sink(item);
        } else {
            return;
        }
    }

    private boolean isRoot(Node item) {
        return indOf(item) == 0;
    }

    private boolean isLeaf(Node item) {
        return leftChild(item) == null && rightChild(item) == null;
    }

    private int indOf(Node item) {
        return map.get(item.item);
    }

    private Node parent(Node item) {
        if (indOf(item) - 1 < 0) {
            return null;
        }
        return items.get((indOf(item) - 1) / 2);
    }

    private Node leftChild(Node item) {
        if (indOf(item) * 2 + 1 > size - 1) {
            return null;
        }
        return items.get(indOf(item) * 2 + 1);
    }

    private Node rightChild(Node item) {
        if (indOf(item) * 2 + 2 > size - 1) {
            return null;
        }
        return items.get(indOf(item) * 2 + 2);
    }
}
