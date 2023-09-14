package be.bds.bdsbes.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import be.bds.bdsbes.service.dto.audit.DateAuditDTO;

import javax.persistence.MappedSuperclass;

@Setter
@Getter
@MappedSuperclass
@JsonIgnoreProperties(
        value = {"createdBy", "updatedBy"},
        allowGetters = true
)
public abstract class UserDateAuditDTO extends DateAuditDTO {
    private Long createdBy;
    private Long updatedBy;
}