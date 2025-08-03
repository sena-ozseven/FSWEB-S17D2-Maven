package rest;

import jakarta.annotation.PostConstruct;
import model.Developer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tax.Taxable;

import java.util.Map;
@RestController
@RequestMapping("/developers")
public class DeveloperController {
    private Map<Integer, Developer> developers;
    private Taxable taxable;



    @PostConstruct
    @Autowired
    public DeveloperController (Map<Integer, Developer> developers, Taxable taxable){
        this.developers = developers;
        this.taxable = taxable;
    }

}
