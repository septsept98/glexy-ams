package com.lawencon.glexy.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.lawencon.glexy.helper.ReportDataExpiredAsset;
import com.lawencon.glexy.model.Asset;
import com.lawencon.glexy.service.AssetService;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping("assets")
public class AssetController extends BaseController {

	@Autowired
	private AssetService assetService;
	
	@Autowired
	private Executor executor;

	@GetMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getAll() throws Exception {
		List<Asset> result = assetService.findAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/deploy-asset")
	public ResponseEntity<?> getAllDeployAsset() throws Exception {
		List<Asset> result = assetService.findAllDeployAsset();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/general-asset")
	public ResponseEntity<?> getAllGeneralAsset() throws Exception {
		List<Asset> result = assetService.findAllGeneralAsset();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getById(@PathVariable("id") String id) throws Exception {
		Asset result = assetService.findById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/invent/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getByInvent(@PathVariable("id") String id) throws Exception {
		List<Asset> result = assetService.findByInvent(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}
	
	@GetMapping("/invoice/{invoId}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getByInvoice(@PathVariable("invoId") String invoId) throws Exception {
		List<Asset> result = assetService.findByInvoice(invoId);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/brand/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getByBrandId(@PathVariable("id") String id) throws Exception {
		List<Asset> result = assetService.findByBrandId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@GetMapping("/company/{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getByCompanyId(@PathVariable("id") String id) throws Exception {
		List<Asset> result = assetService.findByCompanyId(id);
		return new ResponseEntity<>(result, HttpStatus.OK);

	}

	@PostMapping
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public ResponseEntity<?> insert(@RequestPart String data, @RequestPart MultipartFile invoiceImg, @RequestPart MultipartFile assetImg) throws Exception {

		Asset asset = assetService.save(convertToModel(data, Asset.class), invoiceImg, assetImg);

		InsertResDataDto id = new InsertResDataDto();
		id.setId(asset.getId());

		InsertResDto result = new InsertResDto();
		result.setData(id);
		result.setMsg(MessageEnum.CREATED.getMsg());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/upload")
	@ApiResponse(responseCode = "201", description = "successful operation", content = @Content(schema = @Schema(implementation = InsertResDataDto.class)))
	public CompletableFuture<ResponseEntity<?>>  uploadFile(@RequestPart MultipartFile file) throws Exception {

		return CompletableFuture.supplyAsync(() -> {
			
			InsertResDataDto id = new InsertResDataDto();
			
			InsertResDto result = new InsertResDto();
			result.setData(id);
			result.setMsg(MessageEnum.CREATED.getMsg());
			
			try {
				assetService.saveExcel(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return new ResponseEntity<>(result, HttpStatus.OK);

		}, executor);
		
	}

	@PutMapping
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> update(@RequestBody Asset data) throws Exception {
		Asset asset = assetService.update(data);

		UpdateResDataDto ver = new UpdateResDataDto();
		ver.setVersion(asset.getVersion());

		UpdateResDto result = new UpdateResDto();
		result.setData(ver);
		result.setMsg(MessageEnum.UPDATED.getMsg());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/image")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = UpdateResDataDto.class)))
	public ResponseEntity<?> updateImage(@RequestPart String data, MultipartFile file) throws Exception {
		Asset asset = assetService.updateImage(convertToModel(data, Asset.class), file);

		UpdateResDataDto ver = new UpdateResDataDto();
		ver.setVersion(asset.getVersion());

		UpdateResDto result = new UpdateResDto();
		result.setData(ver);
		result.setMsg(MessageEnum.UPDATED.getMsg());

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ResDto.class)))
	public ResponseEntity<?> delete(@PathVariable("id") String id) throws Exception {
		boolean data = assetService.removeById(id);

		ResDto result = new ResDto();

		if (data) {
			result.setMsg(MessageEnum.SUCCESS.getMsg());
		}

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/pdf")
	public byte[] generatePdf() throws Exception, JRException {
		
		byte[] data = assetService.pdfAssetExpired();
		
		return data;
	}
	
	@GetMapping("/send-email")
	public ResponseEntity<?> sendEmailTrackAsset() throws Exception, JRException {
		
		ResDto resDto = assetService.sendEmailAssetExpiredReport();
		
		return new ResponseEntity<>(resDto, HttpStatus.OK);
	}
	
	@GetMapping("search-general-asset/")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getAllBySearch(@RequestParam ("query") String query) throws Exception {
		List<Asset> result = assetService.searchAssetGeneral(query);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("get-invent-brand")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = Asset.class)))
	public ResponseEntity<?> getAssetByInventBrand(@RequestParam("invent-id") String inventId, @RequestParam("brand-id") String brandId) throws Exception {
		List<Asset> result = assetService.findAssetByInventBrand(inventId, brandId);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("expired-asset")
	@ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(implementation = ReportDataExpiredAsset.class)))
	public ResponseEntity<?> getAssetExpired() throws Exception {
		List<ReportDataExpiredAsset> result = assetService.findExpiredAsset();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/excel")
	public ResponseEntity<byte[]> generateExcel() throws Exception, JRException {
		
		byte[] data = assetService.downloadTemplateExcel();
		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=glexy-asset.xlsx");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(data);
	}
	
	@GetMapping("/undeploy-asset")
	public ResponseEntity<?> getAllUndeployAsset() throws Exception {
		List<Asset> result = assetService.findAssetUndeployable();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/pending-asset")
	public ResponseEntity<?> getAllPendingAsset() throws Exception {
		List<Asset> result = assetService.findAssetPending();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/archived-asset")
	public ResponseEntity<?> getAllArchivedAsset() throws Exception {
		List<Asset> result = assetService.findAssetArchived();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping(value = "pic/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] getPic(@PathVariable("id") String id) throws Exception {
		return assetService.findById(id).getAssetImg().getFile();
	}

}
