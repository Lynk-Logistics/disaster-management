package com.assist.api.repositories

import com.assist.api.models.StorageCentreModel
import org.springframework.data.jpa.repository.JpaRepository

interface StorageCentreRepository extends JpaRepository<StorageCentreModel,Long>{

    List<StorageCentreModel> findByLocationId(long locationId)


}