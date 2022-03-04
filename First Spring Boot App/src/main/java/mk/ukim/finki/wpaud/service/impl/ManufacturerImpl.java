package mk.ukim.finki.wpaud.service.impl;

import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.repository.impl.InMemoryManufacturerRepository;
import mk.ukim.finki.wpaud.repository.impl.jpa.ManufacturerRepository;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerImpl implements ManufacturerService {
    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public List<Manufacturer> findAll() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        return manufacturerRepository.findById(id);
    }

    @Override
    public Optional<Manufacturer> save(String name, String address) {
        return Optional.of(manufacturerRepository.save(new Manufacturer(name,address)));
    }

    @Override
    public void deleteByID(Long id) {
         manufacturerRepository.deleteById(id);
    }


}
