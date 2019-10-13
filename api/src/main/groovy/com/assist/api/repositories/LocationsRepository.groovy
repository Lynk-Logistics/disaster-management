package com.assist.api.repositories

import com.assist.api.models.LocationsModel
import org.springframework.data.jpa.repository.JpaRepository

interface LocationsRepository extends JpaRepository<LocationsModel,Long>{
}
