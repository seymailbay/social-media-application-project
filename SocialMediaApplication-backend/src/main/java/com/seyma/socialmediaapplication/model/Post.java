package com.seyma.socialmediaapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Entity
@Table(name = "post")
@Data
public class Post {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.EAGER)// bir sürü postun tek bir useri olabilir * fetch type lazy.(postu çektigimde userın objesi gelmesine gerek yok )
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Bir user silindiginde bütün postları silinsin
    User user;

    @Column(columnDefinition = "VARCHAR")
    String title;

    @Column(columnDefinition = "VARCHAR")
    String text;

    @Temporal(TemporalType.TIMESTAMP)
    Date createDate;

}
