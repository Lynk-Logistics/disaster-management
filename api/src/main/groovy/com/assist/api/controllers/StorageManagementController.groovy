package com.assist.api.controllers

import com.assist.api.models.StorageCentreModel
import com.assist.api.pogo.dto.StorageCentreDto
import com.assist.api.pogo.dto.StorageInventoryDto
import com.assist.api.pogo.request.StorageCentreSaveRequest
import com.assist.api.pogo.request.StorageInventoryUpdationRequest
import com.assist.api.security.CurrentUser
import com.assist.api.security.UserPrincipal
import com.assist.api.services.StorageCentreService
import com.assist.api.services.StorageItemService
import com.assist.api.util.StorageCentreUtils
import com.assist.api.util.StorageItemUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/storage')
class StorageManagementController {

    @Autowired
    private StorageCentreService storageCentreService
    @Autowired
    private StorageItemService storageItemService

    @PostMapping('/')
    StorageCentreDto save(@RequestBody StorageCentreSaveRequest storageCentreSaveRequest,@CurrentUser UserPrincipal userPrincipal){
        StorageCentreModel storageCentreModel = StorageCentreUtils.create(storageCentreSaveRequest,userPrincipal.userId)
        return new StorageCentreDto(storageCentreService.save(storageCentreModel))
    }

    @GetMapping('/{id}')
    StorageCentreDto view(@PathVariable('id') long storageCentreId){
        return new StorageCentreDto(storageCentreService.findById(storageCentreId))
    }

    @PostMapping('/item/save')
    StorageInventoryDto updateItem(@RequestBody StorageInventoryUpdationRequest storageInventoryUpdationRequest){
        return new StorageInventoryDto(storageItemService.save(StorageItemUtil.convert(storageInventoryUpdationRequest)))
    }

    @GetMapping('/item/show/{id}')
    List<StorageInventoryDto> getInventory(@PathVariable('id') long storageCentreId){
        return storageItemService.getDetails(storageCentreId).collect{new StorageInventoryDto(it)}
    }

    @GetMapping('/location/{id}')
    List<StorageCentreDto> findAllStorageInArea(@PathVariable('id') long locationId){
        return storageCentreService.findByLocation(locationId).collect{new StorageCentreDto(it)}
    }
}
