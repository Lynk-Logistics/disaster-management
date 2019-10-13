package com.assist.api.repositories

import com.assist.api.models.OrganisationModel
import org.springframework.data.jpa.repository.JpaRepository

interface OrganisationsRepository extends JpaRepository<OrganisationModel,Long>{

    List<OrganisationModel> findByLocationId(Long locationId)
}