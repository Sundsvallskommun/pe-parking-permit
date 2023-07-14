package se.sundsvall.parkingpermit.businesslogic.worker;

import static se.sundsvall.parkingpermit.Constants.FALSE;
import static se.sundsvall.parkingpermit.Constants.UPDATE_AVAILABLE;

import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.springframework.beans.factory.annotation.Autowired;

import se.sundsvall.parkingpermit.businesslogic.handler.FailureHandler;
import se.sundsvall.parkingpermit.integration.camunda.CamundaClient;

public abstract class AbstractWorker implements ExternalTaskHandler {

	@Autowired
	private CamundaClient camundaClient;

	@Autowired
	protected FailureHandler failureHandler;

	protected void clearUpdateAvailable(ExternalTask externalTask) {
		/* Clearing process variable has to be a blocking operation.
		 * Using ExternalTaskService.setVariables() will not work without creating race conditions.
		 */
		camundaClient.setProcessInstanceVariable(externalTask.getProcessInstanceId(), UPDATE_AVAILABLE, FALSE);
	}
}
