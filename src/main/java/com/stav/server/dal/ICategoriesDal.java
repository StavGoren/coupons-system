package com.stav.server.dal;

import com.stav.server.dto.CategoryDTO;
import com.stav.server.entities.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoriesDal extends CrudRepository<Category, Long> {


    @Query("SELECT new com.stav.server.dto.CategoryDTO (cat.id, cat.name, cat.imageSrc) FROM Category cat WHERE cat.id= :categoryId")
    CategoryDTO findCategory(@Param("categoryId") long categoryId);

    @Query("SELECT new com.stav.server.dto.CategoryDTO (cat.id, cat.name, cat.imageSrc) FROM Category cat")
    List<CategoryDTO> findCategoriesByPage(Pageable page);

    @Query("SELECT new com.stav.server.dto.CategoryDTO (cat.id, cat.name, cat.imageSrc) FROM Category cat")
    List<CategoryDTO> findAllCategories();

//    @Query("SELECT new com.stav.server.dto.CategoryDTO (cat.id, cat.name) FROM Category cat")
//    List<CategoryDTO> findAllCategories();
}
