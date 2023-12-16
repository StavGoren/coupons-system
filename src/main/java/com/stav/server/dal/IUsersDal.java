package com.stav.server.dal;

import com.stav.server.dto.SuccessfulLoginDetails;
import com.stav.server.dto.UserDTO;
import com.stav.server.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IUsersDal extends CrudRepository<User, Long>{

    @Query("SELECT new com.stav.server.dto.UserDTO (u.id, u.userName, u.userType, u.company.name)" +
            "FROM User u WHERE u.id= :userId")
    UserDTO findUser(@Param("userId") long userId);

    @Query("SELECT new com.stav.server.dto.UserDTO (u.id, u.userName, u.userType, comp.name) " +
            "FROM User u LEFT JOIN Company comp ON comp.id = u.company.id")
    List<UserDTO> findUsersByPage(Pageable pagination);

    @Query("SELECT new com.stav.server.dto.UserDTO (u.id, u.userName, u.userType, comp.name) " +
            "FROM User u JOIN Company comp on  u.company.id = comp.id " +
            "WHERE comp.id= :companyId")
    List<UserDTO> findUsersByCompanyId(@Param("companyId") long companyId, Pageable pagination);


    @Query("SELECT new com.stav.server.dto.SuccessfulLoginDetails (u.id, u.userName, u.company.id, u.userType)" +
            "FROM User u WHERE u.userName= :userName " +
            "AND u.password= :password")
    SuccessfulLoginDetails login(@Param("userName") String userName, @Param("password") String password);


    /************ Methods from Avi - very important !!!
     *
     @Query("select u from users u where user_name =:userName and password = :password")
     public SuccessfulLoginData login(@Param("userName") String userName, @Param("password") String password);

     @Query("select u from users u where user_name =:userName")
     public User isUserExist(@Param("userName") String userName);

     ******************/


//    @Query("SELECT u.userName, u.password, u.userType FROM User u WHERE u.userType= :userType")
//    List<UserDTO> findUsersByUserType(@Param("userType") UserType userType);
//
//    @Query("SELECT u.userName, u.password, u.userType FROM User u")
//    List<UserDTO> findAllUsersNoIds();
//
//    @Query("SELECT u.id, u.userName, u.password, u.userType FROM User u")
//    List<User> findAllUsersIncludingIds();
//
//    @Query("SELECT new com.stav.server.dto.UserDTO (u.userName, u.userType, c.name) " +
//            "FROM User u JOIN Company c on u.company.id = c.id WHERE u.company.id= :companyId")
//    List<UserDTO> findUsersByCompanyId(@Param("companyId") int companyId);
//
//    @Query("SELECT new com.stav.server.dto.UserDTO (u.userName, u.userType, c.name) " +
//            "FROM User u JOIN Company c on u.company.id = c.id")
//    List<UserDTO> findUsersByPage(Pageable pageNumber);
//
//
//    public interface IUserDal extends CrudRepository<User, Long> {
//
//        @Query("select u from users u where company_id =:u.companyId")
//        public ArrayList<User> getUsersByCompanyID(@Param("companyId") long companyId);
//
//        @Query("select u from users u where user_name =:userName and password = :password")
//        public SuccessfulLoginData login(@Param("userName") String userName, @Param("password") String password);
//
//        @Query("select u from users u where user_name =:userName")
//        public User isUserExist(@Param("userName") String userName);
//
//    }




}
