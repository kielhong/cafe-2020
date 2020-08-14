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
    private ZonedDateTime createdAt;
    @Getter(AccessLevel.PRIVATE)
    private boolean visible;

    @Builder
    private Cafe(String name, String nickname, String description, ZonedDateTime createdAt, boolean visible) {
        this.name = name;
        this.nickname = nickname;
        this.description = description;
        this.createdAt = createdAt;
        this.visible = visible;
    }

    public boolean isPublic() {
        return isVisible();
    }
}
