package ie.shannen.registry.service;

import ie.shannen.registry.controller.model.Address;
import ie.shannen.registry.controller.model.PersonCountResponse;
import ie.shannen.registry.controller.model.PersonRequest;
import ie.shannen.registry.controller.model.PersonResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RegistryService {
    List<PersonResponse> getPersons();
    PersonResponse addPerson(PersonRequest personRequest);
    PersonResponse editPerson(Long personId, PersonRequest personRequest);
    PersonResponse addAddress(Long personId, Address address);
    PersonCountResponse getPersonCount();
    void deletePerson(Long personId);

    Address editAddress(Long id, Address address);
    void deleteAddress(Long addressId);
}
