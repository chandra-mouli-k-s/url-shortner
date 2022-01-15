package com.chandramouli.urlshortner.repository;

import com.chandramouli.urlshortner.entity.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepo extends JpaRepository<Link, String> {
}