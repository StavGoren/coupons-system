package com.stav.server.dal;

import com.stav.server.dto.CompanyDTO;
import com.stav.server.entities.Company;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICompaniesDal extends CrudRepository<Company, Long> {

    @Query("SELECT new com.stav.server.dto.CompanyDTO (comp.id, comp.name, comp.phoneNumber, comp.address, comp.imageSrc)" +
            "FROM Company comp WHERE comp.id= :companyId")
    CompanyDTO findCompanyById(@Param("companyId") long companyId);

    @Query("SELECT new com.stav.server.dto.CompanyDTO (comp.id, comp.name, comp.phoneNumber, comp.address, comp.imageSrc)" +
            "FROM Company comp")
    List<CompanyDTO> findCompaniesByPage(Pageable pageNumber);

    @Query("SELECT new com.stav.server.dto.CompanyDTO (comp.id, comp.name, comp.imageSrc) " +
            "FROM Company comp")
    List<CompanyDTO> findAllCompanies();

//    @Query("SELECT NEW com.stav.server.dto.CompanyDTO (comp.id, comp.name, comp.phoneNumber.comp.address, comp.imageSrc, cat.name) " +
//            "FROM Company comp JOIN Category cat ON cat.id=comp.category.id " +
//            "WHERE cat.name= :categoryName")
//    List<CompanyDTO> findCompaniesByCategoryName(@Param("categoryName") String categoryName);
}
