package mk.ukim.finki.wpaud.web.rest;

import mk.ukim.finki.wpaud.model.Manufacturer;
import mk.ukim.finki.wpaud.service.ManufacturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerRestController {

    private final ManufacturerService manufacturerService;

    public ManufacturerRestController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
    }

    @GetMapping
    public List<Manufacturer> findAll(){
        return this.manufacturerService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Manufacturer> findByID(@PathVariable Long id){
        return this.manufacturerService.findById(id).map(m -> ResponseEntity.ok().body(m))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Manufacturer> save(@RequestParam String name,@RequestParam String address){
        return manufacturerService.save(name,address).map(m -> ResponseEntity.ok().body(m))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteByID(@PathVariable Long id){
        manufacturerService.deleteByID(id);

        if(!this.manufacturerService.findById(id).isPresent())return ResponseEntity.ok().build();
        return ResponseEntity.badRequest().build();
    }
}
