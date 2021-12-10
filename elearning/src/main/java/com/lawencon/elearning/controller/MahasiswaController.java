package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.elearning.model.Mahasiswa;
import com.lawencon.elearning.service.MahasiswaService;

@RestController
@RequestMapping("mhs")
public class MahasiswaController {

	@Autowired
	private MahasiswaService mahasiswaService;

	@GetMapping("{id}")
	public ResponseEntity<?> getMhs(@PathVariable("id") String id) throws Exception {
		Mahasiswa mhs = mahasiswaService.findById(id);
		return new ResponseEntity<>(mhs, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<?> insert(@RequestBody Mahasiswa data) throws Exception {
		mahasiswaService.insert(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<?> update(@RequestBody Mahasiswa data) throws Exception {
		mahasiswaService.update(data);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
