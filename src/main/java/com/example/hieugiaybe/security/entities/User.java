package com.example.hieugiaybe.security.entities;

import com.example.hieugiaybe.entities.Orders;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @NotBlank(message = "This field can't be blank!")
    private String name;
    @NotBlank(message = "This field can't be blank!")
    @Column(unique = true)
    private String username;
    @NotBlank(message = "This field can't be blank!")
    @Column(unique = true)
    @Email(message = "Please enter email in proper format")
    private String email;
    @NotBlank(message = "This field can't be blank!")
    @Size(message = "The password must have at least 5 characters")
    private String password;
    @Column(nullable = false, length = 10)
    @NotNull
    private String phone;
    @Column(nullable = false)
    @NotBlank(message = "This field can't be blank!")
    private String address;
    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(nullable = true, length = 200)
    @NotBlank(message = "Please provide product's image url!")
    private String image;
    @OneToOne(mappedBy = "user")
    private ForgotPassword forgotPassword;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<Orders> orders;


    public String getRealUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

