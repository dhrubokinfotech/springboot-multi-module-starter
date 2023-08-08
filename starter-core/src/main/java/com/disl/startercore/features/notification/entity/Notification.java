package com.disl.startercore.features.notification.entity;

import com.disl.commons.models.AuditModel;
import com.disl.startercore.constants.AppTables;
import com.disl.startercore.entities.User;
import com.disl.startercore.features.notification.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = AppTables.NOTIFICATION_NAME)
public class Notification extends AuditModel<String> {

    @Column(name = AppTables.NotificationTable.TITLE)
    private String title;

    @Column(name = AppTables.NotificationTable.MESSAGE, columnDefinition = "TEXT")
    private String message;

    @Column(name = AppTables.NotificationTable.TYPE_ID)
    private Long typeId = 0L;

    @Column(name = AppTables.NotificationTable.IS_READ)
    private Boolean isRead = false;

    @Enumerated(EnumType.STRING)
    @Column(name = AppTables.NotificationTable.TYPE)
    private NotificationType type;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {
            "creationDateTimeStamp", "passwordResetToken",
            "lastModifiedDate", "createdBy", "creationDate",
            "roles", "lastModifiedDateTimeStamp", "lastModifiedBy",
    })
    @ManyToOne
    @JoinColumn(name = AppTables.NotificationTable.SENDER_ID)
    private User sender;

    @JsonIgnoreProperties(ignoreUnknown = true, value = {
            "creationDateTimeStamp", "passwordResetToken",
            "lastModifiedDate", "createdBy", "creationDate",
            "roles", "lastModifiedDateTimeStamp", "lastModifiedBy",
    })
    @ManyToOne
    @JoinColumn(name = AppTables.NotificationTable.RECIPIENT_ID)
    private User recipient;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTypeId() {
        return typeId != null ? typeId : 0;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Boolean getRead() {
        return isRead;
    }

    public void setRead(Boolean read) {
        isRead = read;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }
}
