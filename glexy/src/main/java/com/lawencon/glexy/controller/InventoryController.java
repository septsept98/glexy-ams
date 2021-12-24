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
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.UpdateResDataDto;
import com.lawencon.glexy.dto.UpdateResDto;
import com.lawencon.glexy.model.Inventory;
import com.lawencon.glexy.service.InventoryService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("inventories")
public class InventoryController {
	
	@Autowired
	private InventoryService inventoryService;
	
	@GetMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Inventory.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<Inventory> result = inventoryService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Inventory.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Inventory result = inventoryService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Inventory.class)))
	public ResponseEntity<?> getByName(@RequestParam (required = false) String name) throws Exception {
		List<Inventory> result = inventoryService.findByName(name);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/code/{code}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Inventory.class)))
	public ResponseEntity<?> getByCode(@PathVariable("code") String code) throws Exception {
		Inventory result = inventoryService.findByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestBody Inventory data) throws Exception {
		data = inventoryService.saveOrUpdate(data);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> update(@RequestBody Inventory data) throws Exception {
		data = inventoryService.saveOrUpdate(data);
		
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
		boolean data = inventoryService.removeById(id);
		
		ResDto result = new ResDto();
		
		if(data) {
			result.setMsg(MessageEnum.SUCCESS.getMsg());
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	

}
