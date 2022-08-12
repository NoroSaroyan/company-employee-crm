package ru.project.entity;

import com.sun.istack.NotNull;
import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Collection;
import java.util.Optional;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id", unique = true)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Column(name = "email")
    @Email
    private String email;
    @NotNull
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Collection<Role> roles;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!id.equals(user.id)) return false;
        if (!email.equals(user.email)) return false;
        return password.equals(user.password);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        return result;
    }

    public Collection<Role> getRoles() {
        return this.roles;
    }
}
