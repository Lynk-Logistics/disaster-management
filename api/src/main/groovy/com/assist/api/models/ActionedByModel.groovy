package com.assist.api.models

import javax.persistence.*
import java.sql.Timestamp

@Entity
@Table(name = "actioned_by", schema = "assist")
@IdClass(ActionedByModelPK.class)
class ActionedByModel {
    private Long actionId
    private Long userId
    private Timestamp actionedOn

    @Id
    @Column(name = "action_id", nullable = false)
    Long getActionId() {
        return actionId
    }

    void setActionId(Long actionId) {
        this.actionId = actionId
    }

    @Id
    @Column(name = "user_id", nullable = false)
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Basic
    @Column(name = "actioned_on", nullable = false)
    Timestamp getActionedOn() {
        return actionedOn
    }

    void setActionedOn(Timestamp actionedOn) {
        this.actionedOn = actionedOn
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        ActionedByModel that = (ActionedByModel) o

        if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false
        if (actionedOn != null ? !actionedOn.equals(that.actionedOn) : that.actionedOn != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = actionId != null ? actionId.hashCode() : 0
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        result = 31 * result + (actionedOn != null ? actionedOn.hashCode() : 0)
        return result
    }
}
