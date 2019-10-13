package com.assist.api.models

import javax.persistence.Column
import javax.persistence.Id
import java.io.Serializable

class ActionedByModelPK implements Serializable {
    private Long actionId
    private Long userId

    @Column(name = "action_id", nullable = false)
    @Id
    Long getActionId() {
        return actionId
    }

    void setActionId(Long actionId) {
        this.actionId = actionId
    }

    @Column(name = "user_id", nullable = false)
    @Id
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        ActionedByModelPK that = (ActionedByModelPK) o

        if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = actionId != null ? actionId.hashCode() : 0
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        return result
    }
}
