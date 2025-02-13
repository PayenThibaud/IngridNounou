package org.example.msenfant.Repository;

import org.example.msenfant.Entity.Enfant;
import org.springframework.data.repository.CrudRepository;

public interface EnfantRepository extends CrudRepository<Enfant, String> {
}
