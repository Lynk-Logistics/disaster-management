package com.assist.api.pogo.request

class StorageInventoryUpdationRequest {

    private Long storageId
    private Long centreId
    private String itemName
    private Integer itemQuantity

    Long getStorageId() {
        return storageId
    }

    void setStorageId(Long storageId) {
        this.storageId = storageId
    }

    Long getCentreId() {
        return centreId
    }

    void setCentreId(Long centreId) {
        this.centreId = centreId
    }

    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    Integer getItemQuantity() {
        return itemQuantity
    }

    void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity
    }
}
