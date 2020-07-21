package ie.shannen.registry.controller.model;

public class PersonCountResponse {
    private int count;

    public PersonCountResponse(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
