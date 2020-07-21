package ie.shannen.registry.service.impl;

import ie.shannen.registry.controller.model.Address;
import ie.shannen.registry.controller.model.PersonCountResponse;
import ie.shannen.registry.controller.model.PersonRequest;
import ie.shannen.registry.controller.model.PersonResponse;
import ie.shannen.registry.converter.ModelConverter;
import ie.shannen.registry.error.NotFoundException;
import ie.shannen.registry.repository.AddressRepository;
import ie.shannen.registry.repository.PersonRepository;
import ie.shannen.registry.repository.model.AddressEntity;
import ie.shannen.registry.repository.model.PersonEntity;
import ie.shannen.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RegistryServiceImplementation implements RegistryService {
    private final PersonRepository personRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public RegistryServiceImplementation(PersonRepository personRepository, AddressRepository addressRepository) {
        this.personRepository = personRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public List<PersonResponse> getPersons() {
        List<PersonResponse> personResponseList = new ArrayList<>();
        personRepository.findAll().forEach(personEntity -> personResponseList.add(ModelConverter.convertPersonEntityToResponse(personEntity)));

        return personResponseList;
    }

    @Override
    public PersonResponse addPerson(PersonRequest personRequest) {
        PersonEntity personEntity = new PersonEntity(personRequest.getFirstName(), personRequest.getLastName());
        return ModelConverter.convertPersonEntityToResponse(personRepository.save(personEntity));
    }

    @Override
    public PersonResponse editPerson(Long personId, PersonRequest personRequest) {
        PersonEntity personEntity = findPersonById(personId);
        personEntity.setFirstName(personRequest.getFirstName());
        personEntity.setLastName(personRequest.getLastName());

        return ModelConverter.convertPersonEntityToResponse(personRepository.save(personEntity));
    }

    @Override
    public PersonResponse addAddress(Long personId, Address address) {
        PersonEntity personEntity = findPersonById(personId);

        AddressEntity addressEntity = new AddressEntity(address.getStreet(), address.getCity(), address.getState(), address.getPostalCode());
        addressEntity = addressRepository.save(addressEntity);

        personEntity.addAddress(addressEntity);

        return ModelConverter.convertPersonEntityToResponse(personRepository.save(personEntity));
    }

    @Override
    public PersonCountResponse getPersonCount() {
        List<PersonEntity> personEntities = new ArrayList<>();
        personRepository.findAll().forEach(personEntities::add);

        return new PersonCountResponse(personEntities.size());
    }

    @Override
    public void deletePerson(Long personId) {
        try {
            PersonEntity personEntity = findPersonById(personId);
            personEntity.getAddresses().forEach(addressEntity -> addressRepository.deleteById(addressEntity.getId()));
            personRepository.deleteById(personId);
        } catch (NotFoundException ignored) {
            //it's already not there
        }
    }

    @Override
    public Address editAddress(Long addressId, Address address) {
        AddressEntity addressEntity = findAddressById(addressId);

        addressEntity.setCity(address.getCity());
        addressEntity.setPostalCode(address.getPostalCode());
        addressEntity.setState(address.getState());
        addressEntity.setStreet(address.getStreet());

        return ModelConverter.convertAddressEntityToResponse(addressRepository.save(addressEntity));
    }

    @Override
    public void deleteAddress(Long addressId) {
        try {
            findAddressById(addressId);
            addressRepository.deleteById(addressId);
        } catch (NotFoundException ignored) {
            //it's already not there
        }
    }

    private PersonEntity findPersonById(Long id) {
        Optional<PersonEntity> personEntityOptional = personRepository.findById(id);
        if (personEntityOptional.isEmpty()) {
            throw new NotFoundException("Person not found");
        }

        return personEntityOptional.get();
    }

    private AddressEntity findAddressById(Long id) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findById(id);
        if (addressEntityOptional.isEmpty()) {
            throw new NotFoundException("Address not found");
        }

        return addressEntityOptional.get();
    }
}
