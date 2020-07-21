package ie.shannen.registry.controller;

import ie.shannen.registry.controller.model.Address;
import ie.shannen.registry.controller.model.PersonCountResponse;
import ie.shannen.registry.controller.model.PersonRequest;
import ie.shannen.registry.controller.model.PersonResponse;
import ie.shannen.registry.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/api"})
public class RegistryController {
    private final RegistryService registryService;

    @Autowired
    public RegistryController(RegistryService registryService) {
        this.registryService = registryService;
    }

    @PostMapping(
            value = {"/person"},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    public PersonResponse addPerson(@RequestBody PersonRequest personRequest) {
        return registryService.addPerson(personRequest);
    }

    @PutMapping(
            value = {"/person/{id}"},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public PersonResponse editPerson(@PathVariable Long id, @RequestBody PersonRequest personRequest) {
        return registryService.editPerson(id, personRequest);
    }

    @DeleteMapping(value = {"/person/{id}"})
    @ResponseStatus(code = HttpStatus.OK)
    public void deletePerson(@PathVariable Long id) {
        registryService.deletePerson(id);
    }

    @GetMapping(value = {"/person/count"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public PersonCountResponse getPersonCount() {
        return registryService.getPersonCount();
    }

    @GetMapping(value = {"/person"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public List<PersonResponse> getPersonList() {
        return registryService.getPersons();
    }

    @PostMapping(
            value = {"/person/{id}/address"},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.CREATED)
    public PersonResponse addAddress(@PathVariable Long id, @RequestBody Address address) {
        return registryService.addAddress(id, address);
    }

    @PutMapping(
            value = {"/address/{id}"},
            produces = {MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(code = HttpStatus.OK)
    public Address editAddress(@PathVariable Long id, @RequestBody Address address) {
        return registryService.editAddress(id, address);
    }

    @DeleteMapping(value = {"/address/{id}"})
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteAddress(@PathVariable Long id) {
        registryService.deleteAddress(id);
    }
}
