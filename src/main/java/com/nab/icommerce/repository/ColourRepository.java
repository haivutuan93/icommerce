package com.nab.icommerce.repository;

import com.nab.icommerce.entity.Colour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ColourRepository extends JpaRepository<Colour, Integer> {
    @Query( "select c from Colour c where c.name in :coloursName" )
    Set<Colour> findColoursByName(@Param("coloursName") Set<String> coloursName);
}
