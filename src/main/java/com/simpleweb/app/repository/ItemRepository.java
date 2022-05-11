package com.simpleweb.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleweb.app.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>{
	
	public Optional<Item> findByCod(String cod);
}
