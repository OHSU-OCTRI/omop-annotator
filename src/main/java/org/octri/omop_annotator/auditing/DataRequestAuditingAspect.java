package org.octri.omop_annotator.auditing;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.octri.authentication.server.security.AuthenticationUserDetailsService;
import org.octri.omop_annotator.auditing.RequestAuditEvent.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Aspect responsible for auditing data requests that access PHI.
 */
@Aspect
@Component
@ConditionalOnProperty(name = "octri.omop_annotator.request-logging.enabled", havingValue = "true", matchIfMissing = true)
public class DataRequestAuditingAspect implements ApplicationEventPublisherAware {

	public static final String REQUEST_EVENT_TYPE = "REQUEST";

	// Map keys for AuditEvent data
	public static final String SERVICE_CLASS = "serviceClass";
	public static final String SERVICE_METHOD = "serviceMethod";
	public static final String REQUEST_TYPE = "requestType";
	public static final String PARAMS = "params";
	public static final String IP_ADDRESS = "ipAddress";

	private ApplicationEventPublisher publisher;

	@Autowired
	private AuthenticationUserDetailsService userService;

	/**
	 * A join point is in the service layer if the method is defined in a type in org.octri.omop_annotator.service.omop
	 * package
	 */
	@Pointcut("within(org.octri.omop_annotator.service.omop.*)")
	private void inServiceLayer() {
	}

	/**
	 * A join point is a service if it is annotated with {@link org.springframework.stereotype.Service}
	 *
	 * @param service
	 *            the service object handling the request
	 */
	@Pointcut("@target(org.springframework.stereotype.Service) && target(service)")
	private void entityService(Object service) {
	}

	/**
	 * A join point is in an unaudited service if the class is annotated with {@link Unaudited}.
	 */
	@Pointcut("@target(org.octri.omop_annotator.auditing.Unaudited)")
	private void unauditedService() {
	}

	/**
	 * A list operation is the execution of any method where the method name starts with "findAll...".
	 */
	@Pointcut("execution(* findAll*(..))")
	private void listOperation() {
	}

	/**
	 * A show operation is the execution of any method that is not a list operation, and where the method name starts
	 * with "find...".
	 */
	@Pointcut("execution(* find*(..)) && !(execution (* findAll*(..)))")
	private void showOperation() {
	}

	/**
	 * Advice that executes before the show method in an entity service.
	 *
	 * @param joinPoint
	 *            information about the join point
	 * @param service
	 *            the service object handling the request
	 */
	@Before("inServiceLayer() && !unauditedService() && entityService(service) && showOperation()")
	public void auditShow(JoinPoint joinPoint, Object service) {
		auditDataRequest(service.getClass().getSimpleName(), joinPoint.getSignature().getName(), RequestType.ONE,
				joinPoint.getArgs());
	}

	/**
	 * Advice that executes before the list method in an entity service.
	 *
	 * @param joinPoint
	 *            information about the join point
	 * @param service
	 *            the service object handling the request
	 */
	@Before("inServiceLayer() && !unauditedService() && entityService(service) && listOperation()")
	public void auditList(JoinPoint joinPoint, Object service) {
		auditDataRequest(service.getClass().getSimpleName(), joinPoint.getSignature().getName(), RequestType.ALL,
				joinPoint.getArgs());
	}

	/**
	 * Audit data request.
	 *
	 * @param className
	 *            name of the service being accessed.
	 * @param methodName
	 *            name of the service method invoked.
	 * @param queryType
	 *            type of query.
	 * @param queryParams
	 *            any parameters provided, such as an entity id.
	 */
	private void auditDataRequest(String className, String methodName, RequestType queryType, Object... queryParams) {
		Map<String, Object> auditData = new HashMap<String, Object>();
		auditData.put(SERVICE_CLASS, className);
		auditData.put(SERVICE_METHOD, methodName);
		auditData.put(REQUEST_TYPE, queryType);
		auditData.put(PARAMS, this.convertQueryParams(queryParams));

		auditData.put(IP_ADDRESS, userService.getRemoteAddress());

		Assert.notNull(userService.getCurrent(), "All JSON API endpoints are supposed to require authentication");
		String username = userService.getCurrent().getUsername();

		publisher.publishEvent(new AuditApplicationEvent(username, REQUEST_EVENT_TYPE, auditData));
	}

	/**
	 * Converts query parameters to formats that are easier to interpret when serialized to the database.
	 *
	 * @param params
	 * @return
	 */
	private Object[] convertQueryParams(Object[] params) {
		return Arrays.stream(params)
				.map(param -> this.mapParameter(param))
				.toArray(Object[]::new);
	}

	/**
	 * Converts a query parameter to the optimal format for serializing to the database.
	 *
	 * {@link AbstractEntity} objects are converted to a map containing the ID and entity class.
	 * {@link Date} objects are converted to an ISO timestamp.
	 * All other types are returned as-is.
	 *
	 * @param param
	 * @return
	 */
	private Object mapParameter(Object param) {
		if (param instanceof Date) {
			return ((Date) param).toInstant().toString();
		} else {
			return param;
		}
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}

	public void setUserService(AuthenticationUserDetailsService userService) {
		Assert.notNull(userService, "userService may not be null");
		this.userService = userService;
	}

}