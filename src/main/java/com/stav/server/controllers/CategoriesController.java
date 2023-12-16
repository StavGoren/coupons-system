package com.stav.server.controllers;

import com.stav.server.dto.CategoryDTO;
import com.stav.server.entities.Category;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import com.stav.server.logic.CategoriesLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoriesController {

    private CategoriesLogic categoriesLogic;

    @Autowired
    public CategoriesController(CategoriesLogic categoriesLogic){
        this.categoriesLogic = categoriesLogic;
    }

    @PostMapping
    public void createCategory(@RequestBody Category category, @RequestHeader String authorization) throws ServerException {
        categoriesLogic.createCategory(category, authorization);
    }

    @PutMapping
    public void updateCategory(@RequestBody Category category, @RequestHeader String authorization) throws ServerException {
        categoriesLogic.updateCategory(category, authorization);
    }

    @GetMapping("{categoryId}")
    public CategoryDTO getCategory(@PathVariable("categoryId") long id) throws ServerException {
        return categoriesLogic.getCategory(id);
    }

    @GetMapping("/byPage")
    public List<CategoryDTO> getCategoriesByPage(@RequestParam("pageNumber") int pageNumber) throws ServerException{
        return categoriesLogic.getCategoriesByPage(pageNumber);
    }

    @GetMapping
    public List<CategoryDTO> getAllCategories() throws ServerException {
        return  categoriesLogic.getAllCategories();
    }

//    @GetMapping
//    public List<CategoryDTO> getAllCategories() throws ServerException {
//        return categoriesLogic.getAllCategories();
//    }


    @DeleteMapping("{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") long id, @RequestHeader String authorization) throws ServerException {
        categoriesLogic.deleteCategory(id, authorization);
    }
}