package com.tuannq.store.entity;

import com.tuannq.store.entity.core.BaseEntity;
import com.tuannq.store.entity.Users;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Table(name = "messages")
public class ChatMessage extends BaseEntity implements Comparable<ChatMessage> {

    @Column
    private Date createdAt;

    @Column(name = "message")
    private String message;

    @ManyToOne
    @JoinColumn(name = "id_author")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "id_appointment")
    private Appointment appointment;

    public ChatMessage() {

    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    @Override
    public int compareTo(ChatMessage o) {
        return this.createdAt.compareTo(o.getCreatedAt());
    }
}
