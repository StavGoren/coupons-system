package com.stav.server.controllers;

import com.stav.server.dto.CompanyDTO;
import com.stav.server.entities.Company;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.CompaniesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompaniesController {

    private CompaniesLogic companiesLogic;

    @Autowired
    public CompaniesController(CompaniesLogic companiesLogic) {
        this.companiesLogic = companiesLogic;
    }

    @PostMapping
    public void createCompany(@RequestBody Company company, @RequestHeader String authorization) throws ServerException {
        companiesLogic.createCompany(company, authorization);
    }


    @PutMapping
    public void updateCompany(@RequestBody Company company, @RequestHeader String authorization) throws ServerException {
        companiesLogic.updateCompany(company, authorization);
    }

    @GetMapping("{companyId}")
    public CompanyDTO getCompany(@PathVariable("companyId")int id) throws ServerException{
        return companiesLogic.getCompany(id);
    }

    @GetMapping("/byPage")
    public List<CompanyDTO> getCompaniesByPage(@RequestParam("pageNumber") int pageNumber) throws ServerException{
        return companiesLogic.getCompaniesByPage(pageNumber);
    }

    @GetMapping
    public List<CompanyDTO> getAllCompanies() throws ServerException{
        return companiesLogic.getAllCompanies();
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@PathVariable("companyId")int id, @RequestHeader String authorization) throws ServerException{
        companiesLogic.deleteCompany(id, authorization);
    }
}