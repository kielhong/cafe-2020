package com.widehouse.cafe.cafe.model;

import java.time.ZonedDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Cafe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String description;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Category category;
    private ZonedDateTime createdAt;
    @Getter(AccessLevel.PRIVATE)
    private boolean visible;

    @Builder
    private Cafe(String name, String nickname, String description, Category category, boolean visible,
                 ZonedDateTime createdAt) {
        this.name = name;
        this.nickname = nickname;
        this.description = description;
        this.category = category;
        this.visible = visible;
        this.createdAt = createdAt;
    }

    public boolean isPublic() {
        return isVisible();
    }
}
