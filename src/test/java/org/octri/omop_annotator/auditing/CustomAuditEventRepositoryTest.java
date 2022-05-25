package org.octri.omop_annotator.auditing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.octri.omop_annotator.auditing.RequestAuditEvent.RequestType;
import org.springframework.boot.actuate.audit.AuditEvent;

/**
 * Tests for {@link CustomAuditEventRepository}
 */
@ExtendWith(MockitoExtension.class)
public class CustomAuditEventRepositoryTest {

	private static final String principal = "username";
	private static final String serviceClass = "TestService";
	private static final String serviceMethod = "findAll";
	private static final RequestType requestType = RequestType.ALL;

	@Mock
	RequestAuditEventRepository mockRepository;

	@Test
	public void testAddEvent() {
		CustomAuditEventRepository repository = newRepo();

		repository.add(validAuditEvent());
		ArgumentCaptor<RequestAuditEvent> argument = ArgumentCaptor.forClass(RequestAuditEvent.class);
		Mockito.verify(mockRepository).save(argument.capture());

		assertEquals(serviceClass, argument.getValue().getServiceClass());
		assertEquals(serviceMethod, argument.getValue().getServiceMethod());
		assertEquals(principal, argument.getValue().getUsername());
		assertEquals(requestType, argument.getValue().getRequestType());
	}

	@Test
	public void testItDoesNotAddTheWrongType() {
		CustomAuditEventRepository repository = newRepo();
		repository.add(auditEventWrongType());

		Mockito.verify(mockRepository, Mockito.never()).save(Mockito.any(RequestAuditEvent.class));
	}

	/**
	 * Construct a repository and setup mock dependencies.
	 *
	 * @return
	 */
	private CustomAuditEventRepository newRepo() {
		CustomAuditEventRepository auditEventRepo = new CustomAuditEventRepository();
		auditEventRepo.setRepository(mockRepository);
		return auditEventRepo;
	}

	private AuditEvent validAuditEvent() {
		Map<String, Object> auditData = new HashMap<String, Object>();
		auditData.put(DataRequestAuditingAspect.SERVICE_CLASS, serviceClass);
		auditData.put(DataRequestAuditingAspect.SERVICE_METHOD, serviceMethod);
		auditData.put(DataRequestAuditingAspect.REQUEST_TYPE, requestType);
		auditData.put(DataRequestAuditingAspect.PARAMS, null);

		return new AuditEvent(Instant.now(), principal, DataRequestAuditingAspect.REQUEST_EVENT_TYPE, auditData);
	}

	private AuditEvent auditEventWrongType() {
		Map<String, Object> auditData = new HashMap<String, Object>();
		return new AuditEvent(Instant.now(), principal, "WRONG_TYPE", auditData);
	}
}