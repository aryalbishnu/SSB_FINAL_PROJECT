package com.example.demo.controller.sujan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.controller.sujan.entity.SujanEntity;

@Repository
public interface SujanRepository extends JpaRepository<SujanEntity, Long> {

}
