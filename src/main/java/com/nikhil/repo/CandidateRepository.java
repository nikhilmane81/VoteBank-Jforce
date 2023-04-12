package com.nikhil.repo;

import com.nikhil.model.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

    @Query("SELECT u FROM Candidate u WHERE u.id = ?1")
    public Candidate findbyid(int id);

    @Query(value = "select * from candidate where vote = (select max(vote) from candidate)", nativeQuery = true)
    public Candidate maxVotes();
}
