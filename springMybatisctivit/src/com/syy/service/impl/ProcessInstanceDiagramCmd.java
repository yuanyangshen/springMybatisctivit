package com.syy.service.impl;

import java.io.InputStream;
import java.util.List;


import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.impl.cmd.GetBpmnModelCmd;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityManager;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;

public class ProcessInstanceDiagramCmd implements Command<InputStream> {

	protected String processInstanceId;
	
	public ProcessInstanceDiagramCmd(String processInstanceId){
		this.processInstanceId = processInstanceId;
	}
	
	@Override
	public InputStream execute(CommandContext commandContext) {
		ExecutionEntityManager executionEntityManager = commandContext.getExecutionEntityManager();
		ExecutionEntity executionEntity = executionEntityManager.findExecutionById(processInstanceId);
		List<String> activityIds = executionEntity.findActiveActivityIds();
		String processDefinitionId = executionEntity.getProcessDefinitionId();
		GetBpmnModelCmd getBpmnModelCmd = new GetBpmnModelCmd(processDefinitionId);
		BpmnModel bpmnModel = getBpmnModelCmd.execute(commandContext);
		ProcessDiagramGenerator pd = new DefaultProcessDiagramGenerator();
		InputStream is = pd.generateDiagram(bpmnModel, "png", activityIds);
		return is;
	}

}
