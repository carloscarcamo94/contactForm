package com.contactForm.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "ContactForm")
public class ContactForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MessageId")
    private Integer messageId;

    @NotBlank
    @Size(max = 50)
    @Column(name = "ContactName", nullable = false, length = 50)
    private String contactName;

    @NotBlank
    @Size(max = 8, min = 8) // Nos aseguramos que sean exactamente los 8 dígitos
    @Pattern(regexp = "^[0-9]{8}$", message = "El teléfono debe contener exactamente 8 números")
    @Column(name = "ContactPhone", nullable = false, length = 8)
    private String contactPhone;

    @NotBlank
    @Email
    @Size(max = 70)
    @Column(name = "ContactEmail", nullable = false, length = 70)
    private String contactEmail;

    @NotBlank
    @Column(name = "ContactMessage", nullable = false, columnDefinition = "TEXT")
    private String contactMessage;

    @Column(name = "MessageSentDate")
    private LocalDateTime messageSentDate;
    
    @PrePersist
    protected void onCreate() {
        this.messageSentDate = LocalDateTime.now();
    }

    
	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactMessage() {
		return contactMessage;
	}

	public void setContactMessage(String contactMessage) {
		this.contactMessage = contactMessage;
	}

	public LocalDateTime getMessageSentDate() {
		return messageSentDate;
	}

	public void setMessageSentDate(LocalDateTime messageSentDate) {
		this.messageSentDate = messageSentDate;
	}
}