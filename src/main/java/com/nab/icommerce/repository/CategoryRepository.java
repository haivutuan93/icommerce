package com.nab.icommerce.repository;

import com.nab.icommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query( "select c from Category c where c.name in :categoriesName" )
    Set<Category> findCategoriesByName(@Param("categoriesName") Set<String> categoriesName);
}

