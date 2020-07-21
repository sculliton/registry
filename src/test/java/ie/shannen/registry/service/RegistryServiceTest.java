package ie.shannen.registry.service;

import ie.shannen.registry.controller.model.Address;
import ie.shannen.registry.controller.model.PersonCountResponse;
import ie.shannen.registry.controller.model.PersonRequest;
import ie.shannen.registry.controller.model.PersonResponse;
import ie.shannen.registry.error.NotFoundException;
import ie.shannen.registry.repository.AddressRepository;
import ie.shannen.registry.repository.PersonRepository;
import ie.shannen.registry.repository.model.AddressEntity;
import ie.shannen.registry.repository.model.PersonEntity;
import ie.shannen.registry.service.impl.RegistryServiceImplementation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistryServiceTest {
    private final PersonRepository personRepositoryMock = mock(PersonRepository.class);
    private final AddressRepository addressRepositoryMock = mock(AddressRepository.class);

    private final RegistryService registryService = new RegistryServiceImplementation(personRepositoryMock, addressRepositoryMock);

    @Before
    public void setUp() {
        reset(personRepositoryMock, addressRepositoryMock);
    }

    @Test
    public void getPersonsTest() {
        when(personRepositoryMock.findAll()).thenReturn(Collections.singletonList(getPersonEntity()));

        List<PersonResponse> responses = registryService.getPersons();

        assertEquals(1, responses.size());
    }

    @Test
    public void getPersonCountTest() {
        when(personRepositoryMock.findAll()).thenReturn(Collections.singletonList(getPersonEntity()));

        PersonCountResponse response = registryService.getPersonCount();

        assertEquals(1, response.getCount());
    }

    @Test
    public void addPersonTest() {
        when(personRepositoryMock.save(ArgumentMatchers.any(PersonEntity.class))).thenReturn(getPersonEntity());

        PersonResponse response = registryService.addPerson(getPersonRequest());

        assertEquals(1L, response.getId());
        verify(personRepositoryMock, times(1)).save(ArgumentMatchers.any(PersonEntity.class));
    }

    @Test
    public void editPersonTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(getPersonEntity()));
        when(personRepositoryMock.save(ArgumentMatchers.any(PersonEntity.class))).thenReturn(getPersonEntity());

        PersonResponse response = registryService.editPerson(1L, getPersonRequest());

        assertEquals(1L, response.getId());
        verify(personRepositoryMock, times(1)).save(getPersonEntity());
    }

    @Test(expected = NotFoundException.class)
    public void editPersonNotFoundTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        registryService.editPerson(1L, getPersonRequest());
        verify(personRepositoryMock, times(1)).save(getPersonEntity());
    }

    @Test
    public void addAddressTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(getPersonEntity()));
        when(personRepositoryMock.save(ArgumentMatchers.any(PersonEntity.class))).thenReturn(getPersonEntity());
        when(addressRepositoryMock.save(ArgumentMatchers.any(AddressEntity.class))).thenReturn(getAddressEntity());

        PersonResponse response = registryService.addAddress(1L, getAddress());

        assertEquals(1L, response.getId());
        verify(personRepositoryMock, times(1)).save(ArgumentMatchers.any(PersonEntity.class));
        verify(addressRepositoryMock, times(1)).save(ArgumentMatchers.any(AddressEntity.class));
    }

    @Test(expected = NotFoundException.class)
    public void addAddressNotFoundTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        registryService.addAddress(1L, getAddress());
    }

    @Test
    public void editAddressTest() {
        when(addressRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(getAddressEntity()));
        when(addressRepositoryMock.save(ArgumentMatchers.any(AddressEntity.class))).thenReturn(getAddressEntity());

        Address response = registryService.editAddress(1L, getAddress());

        assertEquals(2L, response.getId());
        verify(addressRepositoryMock, times(1)).save(getAddressEntity());
    }

    @Test(expected = NotFoundException.class)
    public void editAddressNotFoundTest() {
        when(addressRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        registryService.editAddress(1L, getAddress());
    }

    @Test
    public void deletePersonExistsTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(getPersonEntity()));

        registryService.deletePerson(1L);

        verify(personRepositoryMock, times(1)).deleteById(1L);
        verify(addressRepositoryMock, times(1)).deleteById(2L);
    }

    @Test
    public void deletePersonNotExistsTest() {
        when(personRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        registryService.deletePerson(1L);

        verify(personRepositoryMock, times(0)).deleteById(1L);
    }

    @Test
    public void deleteExistingAddress() {
        when(addressRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(getAddressEntity()));

        registryService.deleteAddress(2L);

        verify(addressRepositoryMock, times(1)).deleteById(2L);
    }

    @Test
    public void deleteNotExistingAddress() {
        when(addressRepositoryMock.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.empty());

        registryService.deleteAddress(2L);

        verify(addressRepositoryMock, times(0)).deleteById(2L);
    }

    private PersonEntity getPersonEntity() {
        PersonEntity personEntity = new PersonEntity("TestFirst", "TestSecond");
        personEntity.setId(1L);
        personEntity.addAddress(getAddressEntity());

        return personEntity;
    }

    private AddressEntity getAddressEntity() {
        AddressEntity addressEntity = new AddressEntity(
                "testStreet",
                "testCity",
                "testState",
                "testPostalCode"
        );
        addressEntity.setId(2L);

        return addressEntity;
    }

    private PersonRequest getPersonRequest() {
        PersonRequest personRequest = new PersonRequest();
        personRequest.setFirstName("TestFirst");
        personRequest.setLastName("TestSecond");

        return personRequest;
    }

    private Address getAddress() {
        return new Address(
                null,
                "testStreet",
                "testCity",
                "testState",
                "testPostalCode"
        );
    }
}
