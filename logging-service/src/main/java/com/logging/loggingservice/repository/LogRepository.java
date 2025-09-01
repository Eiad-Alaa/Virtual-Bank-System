package com.logging.loggingservice.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.logging.loggingservice.entity.Log;
import java.util.UUID;

@Repository
public interface LogRepository extends CrudRepository<Log, UUID> {
}

