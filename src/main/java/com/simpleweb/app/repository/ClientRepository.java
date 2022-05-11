package com.simpleweb.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simpleweb.app.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	public boolean existsByDni(String dni);
	public Optional<Client> findByDni(String dni);
	public void deleteByDni(String dni);
}
