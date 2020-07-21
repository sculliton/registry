package ie.shannen.registry.controller.model;

import java.util.List;

public class PersonResponse {
    private Long id;

    private String firstName;
    private String lastName;

    private List<Address> addresses;

    public PersonResponse(Long id, String firstName, String lastName, List<Address> addresses) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addressEntities) {
        this.addresses = addressEntities;
    }

    public void addAddress(Address addressResponse) {
        this.addresses.add(addressResponse);
    }

    public void deleteAddress(Address addressResponse) {
        this.addresses.remove(addressResponse);
    }
}
