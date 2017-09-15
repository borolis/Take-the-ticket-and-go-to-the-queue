import java.util.LinkedList;

public class LinkedQueue<T> {
    String category = "";
    LinkedList<T> queue;

    public LinkedQueue(String _category) {
        this.queue = new LinkedList<>();
        this.category = _category;
    }

    public boolean isEmpty() {
        if (size() > 0) return false;
        return true;
    }

    public T top() {
        return queue.getFirst();
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public T pop() {
        T result = null;
        if (queue.size() > 0) {
            result = queue.getFirst();
            queue.pop();
        }
        return result;
    }

    public void push(T value) {
        queue.add(value);
    }

    public T get(int index) {
        return queue.get(index);
    }

    public int size() {
        return queue.size();
    }

    public void clear() {
        queue.clear();
    }
}

