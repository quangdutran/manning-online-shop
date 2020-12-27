package com.dutran.bakery.service;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class BaseRepository<T, ID> implements JpaRepository<T, ID> {
    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }
}
