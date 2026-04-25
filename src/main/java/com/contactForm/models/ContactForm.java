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
    @Size(max = 15, min = 9)
    // Expresión regular que exige iniciar con '+' seguido de 1 a 14 dígitos
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "El teléfono debe utilizar el formato internacional válido")
    @Column(name = "ContactPhone", nullable = false, length = 15)
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