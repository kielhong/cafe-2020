package com.widehouse.cafe.cafe.model;

import java.time.ZonedDateTime;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Cascade;

@Entity
@Getter
public class Cafe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String nickname;
    private String description;
    @ManyToOne
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
