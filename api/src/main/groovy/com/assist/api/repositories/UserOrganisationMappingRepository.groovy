package com.assist.api.repositories

import com.assist.api.models.UserOrganisationMappingModel
import com.assist.api.models.UserOrganisationMappingModelPK
import org.springframework.data.jpa.repository.JpaRepository

interface UserOrganisationMappingRepository extends JpaRepository<UserOrganisationMappingModel,UserOrganisationMappingModelPK>{
}
