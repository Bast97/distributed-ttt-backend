package de.dttt.redis.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.dttt.redis.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, String> {}
