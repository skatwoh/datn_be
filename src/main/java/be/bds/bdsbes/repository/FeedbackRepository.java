package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.FeedBack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FeedbackRepository extends JpaRepository<FeedBack, UUID> {
}