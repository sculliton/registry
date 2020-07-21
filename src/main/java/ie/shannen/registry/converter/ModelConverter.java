package ie.shannen.registry.converter;

import ie.shannen.registry.controller.model.Address;
import ie.shannen.registry.controller.model.PersonResponse;
import ie.shannen.registry.repository.model.AddressEntity;
import ie.shannen.registry.repository.model.PersonEntity;

import java.util.ArrayList;
import java.util.List;

public class ModelConverter {
    public static PersonResponse convertPersonEntityToResponse(PersonEntity personEntity) {
        List<Address> addresses = new ArrayList<>();
        personEntity.getAddresses().forEach(addressEntity -> addresses.add(convertAddressEntityToResponse(addressEntity)));

        return new PersonResponse(
                personEntity.getId(),
                personEntity.getFirstName(),
                personEntity.getLastName(),
                addresses
        );
    }

    public static Address convertAddressEntityToResponse(AddressEntity addressEntity) {
        return new Address(
                addressEntity.getId(),
                addressEntity.getStreet(),
                addressEntity.getCity(),
                addressEntity.getState(),
                addressEntity.getPostalCode());
    }
}
