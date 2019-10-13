package com.assist.api.models

import javax.persistence.*

@Entity
@Table(name = "storage_item", schema = "assist")
class StorageItemModel {
    private Long storageId
    private Long centreId
    private String itemName
    private Integer itemQuantity
    private StorageCentreModel storageCentreByCentreId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id", nullable = false)
    Long getStorageId() {
        return storageId
    }

    void setStorageId(Long storageId) {
        this.storageId = storageId
    }

    @Basic
    @Column(name = "centre_id", nullable = false)
    Long getCentreId() {
        return centreId
    }

    void setCentreId(Long centreId) {
        this.centreId = centreId
    }

    @Basic
    @Column(name = "item_name", nullable = false, length = 512)
    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    @Basic
    @Column(name = "item_quantity", nullable = false)
    Integer getItemQuantity() {
        return itemQuantity
    }

    void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        StorageItemModel that = (StorageItemModel) o

        if (storageId != null ? !storageId.equals(that.storageId) : that.storageId != null) return false
        if (centreId != null ? !centreId.equals(that.centreId) : that.centreId != null) return false
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false
        if (itemQuantity != null ? !itemQuantity.equals(that.itemQuantity) : that.itemQuantity != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = storageId != null ? storageId.hashCode() : 0
        result = 31 * result + (centreId != null ? centreId.hashCode() : 0)
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0)
        result = 31 * result + (itemQuantity != null ? itemQuantity.hashCode() : 0)
        return result
    }

    @ManyToOne
    @JoinColumn(name = "centre_id", referencedColumnName = "centre_id", nullable = false,updatable = false,insertable = false)
    StorageCentreModel getStorageCentreByCentreId() {
        return storageCentreByCentreId
    }

    void setStorageCentreByCentreId(StorageCentreModel storageCentreByCentreId) {
        this.storageCentreByCentreId = storageCentreByCentreId
    }
}
