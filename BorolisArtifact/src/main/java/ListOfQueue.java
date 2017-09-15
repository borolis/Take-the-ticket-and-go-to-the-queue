import java.util.LinkedList;
import java.util.List;

public class ListOfQueue {
    Integer queueMaxSize = 30;
    LinkedList<LinkedQueue<ClientQueue>> queueList;

    public ListOfQueue() {
        queueList = new LinkedList<>();
    }

    public LinkedQueue<ClientQueue> getQueue(int index) {
        return queueList.get(index);
    }

    public void addQueue(LinkedQueue<ClientQueue> newQueue) {
        queueList.add(newQueue);
    }

    public void removeQueue(int index) {
        queueList.remove(index);
    }

    public Integer getSize() {
        return queueList.size();
    }

    public LinkedQueue<ClientQueue> getQueueByCategory(String category) {
        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).getCategory().equals(category)) {
                return queueList.get(i);
            }
        }
        return null;
    }
}
