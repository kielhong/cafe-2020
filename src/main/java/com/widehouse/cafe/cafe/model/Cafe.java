package com.widehouse.cafe.cafe.model;

import java.time.ZonedDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Cafe {
    private String name;
    private String nickname;
    private String description;
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
