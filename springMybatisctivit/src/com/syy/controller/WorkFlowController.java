package com.syy.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.GetDeploymentProcessDiagramCmd;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import com.syy.service.impl.ProcessInstanceDiagramCmd;

@Controller
public class WorkFlowController {
	
	@RequestMapping("/deploy")
	public ModelAndView getDeployXml(HttpServletRequest req,String xml) throws UnsupportedEncodingException{
		ModelAndView mv = new ModelAndView();
		System.out.println(xml);
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		RepositoryService repositoryService = processEngine.getRepositoryService();
		repositoryService.createDeployment().addInputStream("process.bpmn20.xml", new ByteArrayInputStream(xml.getBytes("UTF-8"))).deploy();
		/*RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		List<ProcessDefinition> processDefinition = repositoryService.createProcessDefinitionQuery().list();
		mv.addObject("processList", processDefinition);*/
		mv.setViewName("redirect:/flowList");
		return mv;
	}
	
	@RequestMapping("/flowList")
	public ModelAndView getWorkFlowList(HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		TaskService taskService = processEngine.getTaskService();
		List<ProcessDefinition> processDefinition = repositoryService.createProcessDefinitionQuery().list();
		List<ProcessInstance> instanceList = runtimeService.createProcessInstanceQuery().list();
		List<Task> taskList = taskService.createTaskQuery().list();
		mv.addObject("processList", processDefinition);
		mv.addObject("processInstance", instanceList);
		mv.addObject("taskService",taskList);
		mv.setViewName("workFlow");
		return mv;
	}
	
	@RequestMapping("/start")
	public ModelAndView startWorkFlow(HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		RuntimeService runtimeService = processEngine.getRuntimeService();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("assign", "Lingo");
		params.put("participants", "user1,user2");
		runtimeService.startProcessInstanceById(req.getParameter("id"), params);
		mv.setViewName("redirect:/flowList");
		return mv;
	}
	
	@RequestMapping("/graph")
	public void showGraph(HttpServletRequest req,HttpServletResponse resp,String processDefinitionId,String type) throws IOException{
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService = processEngine.getRuntimeService();
		/*--------*/
		/*ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processDefinitionId).singleResult();
		BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
		List<String> activitys= runtimeService.getActiveActivityIds(processDefinitionId);
		InputStream is = processEngine.getProcessEngineConfiguration().getProcessDiagramGenerator().generateDiagram(bpmnModel, "png", activitys);*/
		
		/*--------*/
		Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngine.getProcessEngineConfiguration());
		
		Command<InputStream> cmd = null;
		if("start".equals(type)){
			cmd = new GetDeploymentProcessDiagramCmd(processDefinitionId);
		}
		if("instance".equals(type)){
			cmd = new ProcessInstanceDiagramCmd(processDefinitionId);
		}
		if("complete".equals(type)){
			Task task = processEngine.getTaskService().createTaskQuery().taskId(processDefinitionId).singleResult();
			if(task != null){
				cmd = new ProcessInstanceDiagramCmd(task.getProcessInstanceId());
			}
		}
		if(cmd != null){
			InputStream imageStream = processEngine.getManagementService().executeCommand(cmd);
			byte[] b = new byte[1024];
			int len = 0 ;
			while((len = imageStream.read(b,0,1024)) != -1){
				resp.getOutputStream().write(b,0,len);
			}
		}
		resp.getOutputStream().close();
	}
	
	@RequestMapping("/complete")
	public ModelAndView completeWorkFlow(HttpServletRequest req){
		ModelAndView mv = new ModelAndView();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(req.getServletContext());
		ProcessEngine processEngine = (ProcessEngine) ctx.getBean("processEngine");
		TaskService taskService = processEngine.getTaskService();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("superior", "superior");
		taskService.complete(req.getParameter("processDefinitionId"), params);
		mv.setViewName("redirect:/flowList");
		return mv;
	}
}
