package com.seyma.socialmediaapplication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "p_like")
@Data
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;


    @ManyToOne(fetch = FetchType.LAZY)// bir sürü postun tek bir useri olabilir * fetch type lazy.(postu çektigimde userın objesi gelmesine gerek yok )
    @JoinColumn(name = "post_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Bir user silindiginde bütün postları silinsin
    @JsonIgnore
    Post post;

    @ManyToOne(fetch = FetchType.LAZY)// bir sürü postun tek bir useri olabilir * fetch type lazy.(postu çektigimde userın objesi gelmesine gerek yok )
    @JoinColumn(name = "user_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Bir user silindiginde bütün postları silinsin
    @JsonIgnore
    User user;

}
