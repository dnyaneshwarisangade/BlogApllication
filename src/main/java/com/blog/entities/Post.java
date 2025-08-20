package com.blog.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.blog.entities.Comment;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer PostId;
    
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    
    @Column(name = "description")
    private String content;

    private String image;

    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @OneToMany(mappedBy="post",cascade=CascadeType.ALL)
    private Set<Comment> comments= new HashSet<>(); 

}
