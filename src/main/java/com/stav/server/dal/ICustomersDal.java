package com.stav.server.dal;

import com.stav.server.dto.CustomerDTO;
import com.stav.server.entities.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomersDal extends CrudRepository<Customer, Long> {

    @Query("SELECT NEW com.stav.server.dto.CustomerDTO (cus.id, cus.firstName, cus.lastName, cus.phoneNumber, cus.user.userName, cus.address) " +
            "FROM Customer cus  WHERE cus.id= :customerId")
    CustomerDTO findCustomer(@Param("customerId") long customerId);

    @Query("SELECT NEW com.stav.server.dto.CustomerDTO (cus.id, cus.firstName, cus.lastName, cus.phoneNumber, cus.user.userName, cus.address)" +
            "FROM Customer cus")

    List<CustomerDTO> findAllCustomersByPage (Pageable pageable);


}
