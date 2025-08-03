package com.workintech.s17d2.model;

import com.workintech.s17d2.tax.Taxable;

public class DeveloperFactory {
    public static Developer createDeveloper(Developer developer, Taxable taxable) {
        Developer developer1 = null;
         if (developer.getExperience().equals(Experience.JUNIOR)) {
             developer1 = new JuniorDeveloper(developer.getId(), developer.getName(), developer.getSalary()-(developer.getSalary()*taxable.getSimpleTaxRate())/100);
         } else if (developer.getExperience().equals(Experience.MID)) {
             developer1 = new MidDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary() * taxable.getMiddleTaxRate())/100);
         } else if (developer.getExperience().equals(Experience.SENIOR)) {
             developer1 = new SeniorDeveloper(developer.getId(), developer.getName(), developer.getSalary() - (developer.getSalary()*taxable.getUpperTaxRate())/100);
         } else {
             System.out.println("Unknown experience type.");
             return null;
         }
        return developer1;
    }

}
