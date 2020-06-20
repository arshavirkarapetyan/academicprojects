public class ArrayDeque<T> implements Deque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        nextFirst = 8 / 2;
        nextLast = nextFirst + 1;
        size = 0;
    }

    public ArrayDeque(ArrayDeque other) {
        nextFirst = other.nextFirst;
        nextLast = other.nextLast;
        items = (T []) new Object[other.items.length];
        size = other.size;
        System.arraycopy(other.items, 0, items, 0, other.items.length);
    }

    @Override
    public void addFirst(T i) {
        if (nextFirst < 0) {
            resize(items.length * 2);
        }
        items[nextFirst] = i;
        nextFirst = nextFirst - 1;
        size += 1;
    }

    @Override
    public void addLast(T i) {
        if (nextLast == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = i;
        nextLast = nextLast + 1;
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
        int i = nextFirst + 1;
        while (i < nextLast) {
            System.out.print(items[i] + " ");
            i++;
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            if ((double) (size) / items.length < 0.25) {
                resize(items.length / 2);
            }
            nextFirst = nextFirst + 1;
            T p = items[nextFirst];
            size -= 1;
            return  p;
        }
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            if ((double) (size) / items.length < 0.25) {
                resize(items.length / 2);
            }
            nextLast = nextLast - 1;
            T p = items[nextLast];
            size -= 1;
            return  p;
        }
    }

    private void resize(int target) {
        T[] p = (T []) new Object[target];
        System.arraycopy(items, nextFirst + 1, p, target / 4, size);
        items = p;
        nextFirst = target / 4 - 1;
        nextLast = target / 4 + size;
    }

    @Override
    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return items[nextFirst + index + 1];
        }
    }

}
