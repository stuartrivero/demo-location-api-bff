package com.example.demolocationapibff.service.database;

import com.example.demolocationapibff.domain.PostcodeSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostcodeSearchRepository extends JpaRepository<PostcodeSearch, Long>{

}