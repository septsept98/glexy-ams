package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.dto.UpdateResDataDto;
import com.lawencon.glexy.dto.UpdateResDto;
import com.lawencon.glexy.model.Employee;
import com.lawencon.glexy.service.EmployeeService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("employees")
public class EmployeeController extends BaseController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@GetMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Employee.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<Employee> result = employeeService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Employee.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Employee result = employeeService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestBody Employee data) throws Exception {
		employeeService.saveOrUpdateEmployee(data);
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> update(@RequestBody Employee data) throws Exception {
		data = employeeService.saveOrUpdateEmployee(data);
		UpdateResDataDto ver = new UpdateResDataDto();
		ver.setVersion(data.getVersion());
		
		UpdateResDto result = new UpdateResDto();
		result.setData(ver);
		result.setMsg(MessageEnum.UPDATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResDto.class)))
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		boolean data = employeeService.deleteById(id);
		
		ResDto result = new ResDto();
		
		if(data) {
			result.setMsg(MessageEnum.SUCCESS.getMsg());
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("search")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Employee.class)))
	public ResponseEntity<?> getAllBySearch(@RequestParam ("query") String query) throws Exception {
		List<Employee> result = employeeService.search(query);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}


}
