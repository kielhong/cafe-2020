package com.widehouse.cafe.cafe.model;

import static org.assertj.core.api.BDDAssertions.then;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.util.ReflectionTestUtils;

@DataJpaTest
class CafeRepositoryTest {
    @Autowired
    private CafeRepository repository;
    @Autowired
    private TestEntityManager entityManager;

    Category life;

    @BeforeEach
    void setUp() {
        life = entityManager.persist(CategoryFixtures.life());
    }

    @Test
    void findByNickname_then_returnOptionalCafeOfNickname() {
        // given
        var cafe = CafeFixtures.foo();
        ReflectionTestUtils.setField(cafe, "category", life);
        entityManager.persist(cafe);
        // when
        var result = repository.findByNickname("foo");
        // then
        then(result)
                .isPresent()
                .hasValue(cafe);
    }
}