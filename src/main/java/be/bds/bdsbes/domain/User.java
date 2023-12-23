package be.bds.bdsbes.domain;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import be.bds.bdsbes.domain.audit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor

@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends DateAudit {
    @Id
    private Long id;

    @Column(nullable = false, length = 128)
    @NotEmpty(message = ValidationErrorUtil.NotNull)
    @Size(max = 128, message = ValidationErrorUtil.Max)
    private String name;

    @Email(message = ValidationErrorUtil.Email)
    @Column(nullable = false, length = 128)
    @Size(max = 128, message = ValidationErrorUtil.Max)
    private String email;

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @JsonIgnore
    private String password;

    @Column(length = 16)
    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    private String role;

    private String sdt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_khach_hang")
    private KhachHang khachHang;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "khachHang")
    private Set<DatPhong> datPhongs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ThongBao> thongBaos = new LinkedHashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}