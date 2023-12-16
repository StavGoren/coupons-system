package com.stav.server.logic;

import com.stav.server.consts.Constants;
import com.stav.server.dal.ICategoriesDal;
import com.stav.server.dto.CategoryDTO;
import com.stav.server.entities.Category;
import com.stav.server.enums.ErrorType;
import com.stav.server.enums.UserType;
import com.stav.server.exceptions.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.stav.server.utils.JWTUtils.validatePermissionByUserType;

@Service
public class CategoriesLogic {
    private ICategoriesDal categoriesDal;

    @Autowired
    public CategoriesLogic(ICategoriesDal categoriesDal)
    {
        this.categoriesDal = categoriesDal;
    }


    /*******************************
     Public methods and actions
     *******************************/

    public void createCategory(Category category, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        validateNameLength(category.getName());

        try {
            categoriesDal.save(category);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to insert category", e);
        }
    }

    public void updateCategory(Category category, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        validateNameLength(category.getName());

        try {
            categoriesDal.save(category);
        }catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " failed to update category", e);
        }
    }

    public CategoryDTO getCategory(long id) throws ServerException {
        try {
            return categoriesDal.findCategory(id);
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCategory()'", e);
        }
    }

//    public List<CategoryDTO> getAllCategories() throws ServerException {
//        try{
//            return categoriesDal.findAllCategories();
//        } catch (Exception e){
//            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getAllCategories()'", e);
//        }
//    }

    public List<CategoryDTO> getCategoriesByPage(int pageNumber) throws ServerException {
        Pageable pagination = PageRequest.of(pageNumber, Constants.AMOUNT_OF_ITEMS_PER_PAGE);
        try {
            return categoriesDal.findCategoriesByPage(pagination);
        } catch (Exception e){
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getCategoriesByPage()'", e);
        }
    }

    public List<CategoryDTO> getAllCategories() throws ServerException {
        try{
            return categoriesDal.findAllCategories();
        } catch (Exception e) {
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'getAllCategories()'", e);
        }
    }

    @Transactional(rollbackOn = {Exception.class})
    public void deleteCategory(long id, String authorization) throws ServerException {
        validatePermissionByUserType(UserType.Admin, authorization);
        try {
            categoriesDal.deleteById(id);
        } catch (Exception e){
            throw new ServerException(ErrorType.GENERAL_ERROR, " an error occurred while executing 'deleteCategory()'", e);
        }
    }


    /*****************
      Validations
     *****************/


    private void validateNameLength(String name) throws ServerException {
        if (name.isBlank()) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too short");
        }
        if (name.length() > 40) {
            throw new ServerException(ErrorType.INVALID_NAME_LENGTH, " name too long");
        }
    }
}