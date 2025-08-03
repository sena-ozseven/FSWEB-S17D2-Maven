package com.workintech.s17d2.rest;

import com.workintech.s17d2.dto.DeveloperResponse;
import com.workintech.s17d2.model.DeveloperFactory;
import jakarta.annotation.PostConstruct;
import com.workintech.s17d2.model.Developer;
import com.workintech.s17d2.model.SeniorDeveloper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.workintech.s17d2.tax.Taxable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//Hepsi Component annotationından üretilen annotation'lar. yani annotationlar arasında da parent-child ilişkisi vardır.
//bunların farklılaşma sebebi: bazen biz controller'a özel exceptionlar üretmek isteriz.
//controller'a özel hata mekanizmaları, özel davranıslar olusmasını isteriz.
//çünkü bir hata oldugunda gördüğümüz exceptionda nerede hata oldugunu bu sekilde anlayabiliyoruz.
//katmanlı bir mimari geliştirme --> hangi katmanda bir sorun oldugunu kolay tespit etmek için bu sekilde katmanlar geliştirilmiş.
//buna ek olarak kodun okunabilirliği de artyor.


@RestController
@RequestMapping("/developers")
public class DeveloperController {

    //testlerden gecmesi için public yapıldı. yoksa encapsulationı korumak için private olması lazım.
    public Map<Integer, Developer> developers;
    private Taxable taxable; // = new DeveloperTax(); yapıyor constructor'da.


    //INJECTION --> Taxable interface'i gidip @Component annotationlu DeveloperTax'ı inject ediyor.
    @Autowired
    public DeveloperController (Taxable taxable){
        this.taxable = taxable;
    }

    //TO INITIALIZE THE MAP
    @PostConstruct //uygulama daha ayağa kalkarken yapmasını istediğimiz seyleri yapıyor.
    public void init() {
        this.developers = new HashMap<>();
        this.developers.put(1, new SeniorDeveloper(1, "sena", 100000d));
    }


    //CRUD İŞLEMLERİ ---------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) //defaultta başarılı oldugunda API 200 dönüyor. created 201 dönmesini sağlıyor.
    public DeveloperResponse save(@RequestBody Developer developer) {
        Developer createdDeveloper =  DeveloperFactory.createDeveloper(developer, taxable);
        //Validation
         if (Objects.nonNull(createdDeveloper)) {
              developers.put(createdDeveloper.getId(), createdDeveloper);
         }
         return new DeveloperResponse(createdDeveloper, HttpStatus.CREATED.value(), "developer has been created.");
    }

    @GetMapping
    public List<Developer> getAll() {
        //STREAM API --> JAVA 8 API
        return developers.values().stream().toList();
    }

    @GetMapping("/{id}")
    public DeveloperResponse getById(@PathVariable("id") int id) {
        Developer foundDeveloper = this.developers.get(id);
        if (foundDeveloper == null) {
             return new DeveloperResponse(null , HttpStatus.NOT_FOUND.value(), "developer with " + id + " id not found.");
        }
        return new DeveloperResponse(foundDeveloper, HttpStatus.OK.value(), "developer has been found.");
    }


    @PutMapping("/{id}")              //eski kaydın id'sini alıyor ve o id ile yeni kayıt yapıyor
    public DeveloperResponse putById(@PathVariable("id") int id, @RequestBody Developer developer) {
        developer.setId(id);
        Developer newDeveloper = DeveloperFactory.createDeveloper(developer, taxable);
        this.developers.put(id, newDeveloper);
        return new DeveloperResponse(newDeveloper, HttpStatus.OK.value(), "update successful.");
    }

    @DeleteMapping("/{id}")
    public DeveloperResponse deleteById(@PathVariable("id") int id) {
        Developer removedDeveloper = this.developers.get(id);
        this.developers.remove(id);
        return new DeveloperResponse(removedDeveloper, HttpStatus.NO_CONTENT.value(), "developer has been deleted");
    }
}
