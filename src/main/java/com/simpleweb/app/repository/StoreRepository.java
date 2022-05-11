package com.simpleweb.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleweb.app.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long>{

	public Optional<Store> findByCod(String cod);
}
