public class LinkedListDeque<T> implements Deque<T> {

    private class TNode {
        private T item;
        private TNode prev;
        private TNode next;

        private TNode(TNode p, T i, TNode n) {
            prev = p;
            item = i;
            next = n;
        }

    }

    private TNode sentinel;
    private int size;


    public LinkedListDeque() {
        sentinel = new TNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(LinkedListDeque other) {
        sentinel = new TNode(null, null, null);
        TNode p = sentinel;
        TNode o = other.sentinel;
        size = other.size;
        for (int i = 0; i < size; i++) {
            p.next = new TNode(p, o.next.item, sentinel);
            p = p.next;
            o = o.next;
        }
    }

    @Override
    public void addFirst(T i) {
        TNode p = sentinel.next;
        sentinel.next = new TNode(sentinel, i, sentinel.next);
        p.prev = sentinel.next;
        size += 1;
    }

    @Override
    public void addLast(T i) {
        TNode p = sentinel.prev;
        sentinel.prev = new TNode(sentinel.prev, i, sentinel);
        p.next = sentinel.prev;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        TNode p = sentinel;
        for (int i = 0; i < size; i++) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            TNode p = sentinel.next;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return p.item;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            TNode p = sentinel.prev;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return p.item;
        }
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            TNode p = sentinel;
            for (int i = 0; i < index; i++) {
                p = p.next;
            }
            return p.next.item;
        }
    }

    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        TNode p = sentinel;
        return helper(sentinel.next, index);
    }

    private T helper(TNode p, int index) {
        if (index == 0) {
            return p.item;
        } else {
            return helper(p.next, index - 1);
        }
    }

}
