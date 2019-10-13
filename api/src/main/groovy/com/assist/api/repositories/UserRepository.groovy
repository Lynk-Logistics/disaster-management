package com.assist.api.repositories

import com.assist.api.models.UserModel
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<UserModel,Long>{

    Optional<UserModel> findByEmail(String email)
    Optional<UserModel> findByMobile(String mobile)
}
