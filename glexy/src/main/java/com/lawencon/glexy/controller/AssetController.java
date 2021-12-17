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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lawencon.glexy.constant.MessageEnum;
import com.lawencon.glexy.dto.DeleteResDto;
import com.lawencon.glexy.dto.InsertResDataDto;
import com.lawencon.glexy.dto.InsertResDto;
import com.lawencon.glexy.dto.UpdateResDataDto;
import com.lawencon.glexy.dto.UpdateResDto;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.service.AssetService;

@RestController
@RequestMapping("assets")
public class AssetController extends BaseController {

	@Autowired
	private AssetService assetService;

	@GetMapping
	public ResponseEntity<?> getAll() throws Exception {
		List<Asset> result = assetService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Asset result = assetService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/invent/{id}")
	public ResponseEntity<?> getByInvent(@PathVariable("id") String id) throws Exception {
		Asset result = assetService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/brand/{id}")
	public ResponseEntity<?> getByBrandId(@PathVariable("id") String id) throws Exception {
		List<Asset> result = assetService.findByBrandId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/company/{id}")
	public ResponseEntity<?> getByCompanyId(@PathVariable("id") String id) throws Exception {
		List<Asset> result = assetService.findByCompanyId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestPart String data, @RequestPart MultipartFile invoiceImg,
			@RequestPart MultipartFile assetImg) throws Exception {
		Asset asset = assetService.save(convertToModel(data, Asset.class), invoiceImg, assetImg);

		InsertResDataDto id = new InsertResDataDto();
		id.setId(asset.getId());

		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/upload")
	public ResponseEntity<?> uploadFile(@RequestPart MultipartFile file) throws Exception {
		InsertResDto result = assetService.saveExcel(file);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Asset data) throws Exception {
		Asset asset = assetService.update(data);

		UpdateResDataDto ver = new UpdateResDataDto();
		ver.setVersion(asset.getVersion());

		UpdateResDto result = new UpdateResDto();
		result.setData(ver);
		result.setMsg(MessageEnum.UPDATED.getMsg());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		boolean data = assetService.removeById(id);

		DeleteResDto result = new DeleteResDto();

		if (data) {
			result.setMsg(MessageEnum.SUCCESS.getMsg());
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
