package ru.skypro.homework.entity;

import lombok.*;
import ru.skypro.homework.dto.Role;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "email")
    private String email;

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Ad> ads;
    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private List<Comment> comments;

    public User(String email, String firstName, String lastName, String phone, Role role, String image, String encodedPassword,List<Ad> ads,List<Comment> comments) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.role = role;
        this.image = image;
        this.encodedPassword = encodedPassword;
        this.ads = ads;
        this.comments = comments;
    }
}
