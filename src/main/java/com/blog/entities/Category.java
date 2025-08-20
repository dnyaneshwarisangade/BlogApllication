package com.blog.entities;

import java.util.ArrayList;
import java.util.List;

import com.blog.entities.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer CategoryId;
    @Column(name = "title", length = 100, nullable = false)
    private String CategoryTitle;
    @Column(name = "decription")
    private String CategoryDescription;

   @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>(); 

}
