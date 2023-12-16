package com.stav.server.controllers;

import com.stav.server.dto.UserDTO;
import com.stav.server.dto.UserLoginDetails;
import com.stav.server.entities.User;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.UsersLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersLogic usersLogic;

    @Autowired
    public UsersController(UsersLogic usersLogic) {
        this.usersLogic = usersLogic;
    }

    @PostMapping
    public void createUser(@RequestBody User user) throws ServerException {
        this.usersLogic.createUser(user);
    }

    @PutMapping
    public void updateUser(@RequestBody User user, @RequestHeader String authorization) throws ServerException {
        this.usersLogic.updateUser(user, authorization);
    }

    @GetMapping("{id}")
    public UserDTO getUser(@PathVariable("id") long id, @RequestHeader String authorization) throws Exception {
        return this.usersLogic.getUser(id, authorization);
    }

    @GetMapping("/byPage")
    public List<UserDTO> getUsersByPage(@RequestParam("pageNumber") int pageNumber, @RequestHeader String authorization) throws ServerException {
        return usersLogic.getUsersByPage(pageNumber, authorization);
    }

    @GetMapping("/byCompanyId")
    public List<UserDTO> getUsersByCompanyId(@RequestParam("companyId") long companyId, @RequestParam("pageNumber") int pageNumber, @RequestHeader String authorization) throws ServerException {
        return usersLogic.getUsersByCompanyId(companyId, pageNumber, authorization);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") int id, @RequestHeader String authorization) throws ServerException{
        usersLogic.removeUser(id, authorization);
    }


    @PostMapping("/login")
    public String login(@RequestBody UserLoginDetails userLoginDetails) throws Exception {
        String token = usersLogic.login(userLoginDetails);
        return token;
    }
}
