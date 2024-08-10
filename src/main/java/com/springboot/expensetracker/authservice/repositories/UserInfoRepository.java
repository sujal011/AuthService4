package com.springboot.expensetracker.authservice.repositories;

import com.springboot.expensetracker.authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {

    UserInfo findByUsername(String username);

}
