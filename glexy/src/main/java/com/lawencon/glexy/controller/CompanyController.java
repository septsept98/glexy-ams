package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.ResDto;
import com.lawencon.glexy.dto.UpdateResDataDto;
import com.lawencon.glexy.dto.UpdateResDto;
import com.lawencon.glexy.model.Company;
import com.lawencon.glexy.service.CompanyService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("companies")
public class CompanyController extends BaseController{
	
	@Autowired
	private CompanyService companyService;
	
	@GetMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Company.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<Company> result = companyService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Company.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Company result = companyService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestPart String data, @RequestPart MultipartFile file) throws Exception {
		Company company = companyService.save(convertToModel(data, Company.class), file);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(company.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> update(@RequestBody Company data) throws Exception {
		data = companyService.update(data);
		
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
		boolean data = companyService.removeById(id);
		
		ResDto result = new ResDto();
		
		if(data) {
			result.setMsg(MessageEnum.SUCCESS.getMsg());
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "pic/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPic(@PathVariable("id") String id) throws Exception {
		return companyService.findById(id).getCompanyImg().getFile();
	}
	
	@GetMapping("search")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Company.class)))
	public ResponseEntity<?> getAllBySearch(@RequestParam ("query") String query) throws Exception {
		List<Company> result = companyService.searchByNameCode(query);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/code/{code}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Company.class)))
	public ResponseEntity<?> getByCode(@PathVariable("code") String code) throws Exception {
		Company result = companyService.findByCode(code);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

}
