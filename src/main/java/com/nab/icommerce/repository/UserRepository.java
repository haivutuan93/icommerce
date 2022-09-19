package com.nab.icommerce.repository;

import com.nab.icommerce.entity.Product;
import com.nab.icommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
