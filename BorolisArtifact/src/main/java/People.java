public class People {

    private String id;
    private String category;
    private long incomingTime;


    public People(String id, String cat) {
        this.id = id;
        this.category = cat;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public void setCategory() {
        this.category = category;
    }

    public String getId() {
        return this.id;
    }

    public long getIncomingTime() {
        return this.incomingTime;
    }

    public void setIncomingTime(long _incomingTime) {
        this.incomingTime = _incomingTime;
    }

    public String getCat() {
        return this.category;
    }
}
