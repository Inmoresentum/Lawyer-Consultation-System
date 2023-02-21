package com.cse471.project.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;
import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@ToString
public class User extends AbstractEntity {
    @Size(max = 12, min = 4)
    @Column(unique = true)
    private String username;
    @Column(nullable = false)
    private String name;
    @JsonIgnore
    @Column(nullable = false)
    @Size(max = 356, min = 4)
    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;
    @Lob
    @Column(length = 1000000)
    @Nullable
    private byte[] profilePicture;
    @Nullable
    //Todo: Must need to fix the size later
    @Size(min = 4, max = 34)
    private String phoneNumber;
    @Size(min = 5, max = 65)
    @Column(nullable = false, unique = true)
    private String email;

    private boolean isActive = false;
}
