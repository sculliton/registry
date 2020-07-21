package ie.shannen.registry.config;

import ie.shannen.registry.repository.AddressRepository;
import ie.shannen.registry.repository.PersonRepository;
import ie.shannen.registry.service.RegistryService;
import ie.shannen.registry.service.impl.RegistryServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Bean
    public RegistryService registryService() {
        return new RegistryServiceImplementation(personRepository, addressRepository);
    }
}
