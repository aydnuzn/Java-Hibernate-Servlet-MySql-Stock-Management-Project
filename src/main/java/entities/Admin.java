package entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int aid;
    private String fullName;
    private String email;
    @Column(length = 32)
    private String password;
    @Column(length = 50)
    private String uuid;
}
