package com.lawencon.glexy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.model.StatusTransaction;
import com.lawencon.glexy.model.TrackAsset;
import com.lawencon.glexy.service.TrackAssetService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("track-assets")
public class TrackAssetController {

	@Autowired
	private TrackAssetService trackAssetService;
	
	@GetMapping()
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TrackAsset.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<TrackAsset> result = trackAssetService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TrackAsset.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		TrackAsset result = trackAssetService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TrackAsset.class)))
	public ResponseEntity<?> getByAssetTr(@RequestParam(required = false) String assetCode, String trCode) throws Exception {
		List<TrackAsset> result = trackAssetService.findByAssetTr(assetCode, trCode);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/assets/{assetCode}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = TrackAsset.class)))
	public ResponseEntity<?> getByAsset(@PathVariable("assetCode") String assetCode) throws Exception {
		List<TrackAsset> result = trackAssetService.findByAsset(assetCode);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestBody TrackAsset data) throws Exception {
		trackAssetService.saveOrUpdate(data);
		
		InsertResDataDto id = new InsertResDataDto();
		id.setId(data.getId());
		
		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
