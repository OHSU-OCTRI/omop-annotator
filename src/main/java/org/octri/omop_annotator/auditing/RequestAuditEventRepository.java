package org.octri.omop_annotator.auditing;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestAuditEventRepository extends JpaRepository<RequestAuditEvent, Long> {
}
