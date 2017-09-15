public class ClientQueue {
    private String id="";
    private String category="";

    public ClientQueue(String _id, String _category)
    {
        this.id = _id;
        this.category = _category;
    }

    public String getCategory() {
        return category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
