package com.assist.api.repositories

import com.assist.api.models.StorageItemModel
import org.springframework.data.jpa.repository.JpaRepository

interface StorageItemRepository extends JpaRepository<StorageItemModel,Long>{

    StorageItemModel findByItemNameAndCentreId(String itemName, Long centreId)
    List<StorageItemModel> findByCentreId(Long centreId)
}
