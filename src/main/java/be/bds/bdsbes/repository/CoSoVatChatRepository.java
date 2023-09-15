package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.CoSoVatChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoSoVatChatRepository extends JpaRepository<CoSoVatChat, UUID> {
}