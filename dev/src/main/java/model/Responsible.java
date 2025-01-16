package model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;
public class Responsible {
    private final String idResponsible;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String domain;


    // Constructor
    public Responsible() {
        this.idResponsible = UUID.randomUUID().toString();
        this.firstName = "unknown";
        this.lastName = "unknown";
        this.email = "unknown";
        this.phoneNumber = "unknown";
        this.domain = "unknown";

    }
    public Responsible(String firstName, String lastName, String email,
                       String phoneNumber, String domain) {
        this.idResponsible =UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.domain = domain;
    }

    public String getIdResponsible() {
        return idResponsible;
    }

    public Responsible(String idAccount, String firstName, String lastName, String email,
                       String phoneNumber, String domain) {
        this.idResponsible = idAccount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.domain = domain;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }





    @Override
    public String toString() {
        return "Responsible{" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", domain='" + domain + '\'' +

                '}';
    }

    @JsonIgnore
    public String getString() {
        return
                ", idAResponsible='" + idResponsible + '\'' +
                        ", firstName='" + firstName + '\'' +
                        ", lastName='" + lastName + '\'' +
                        ", email='" + email + '\'' +
                        ", phoneNumber='" + phoneNumber + '\'' +
                        ", domain='" + domain + '\''
                ;
    }
}
