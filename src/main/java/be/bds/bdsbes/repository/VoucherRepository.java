package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
}