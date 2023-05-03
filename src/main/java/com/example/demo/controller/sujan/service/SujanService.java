package com.example.demo.controller.sujan.service;

import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.sujan.dto.SujanDto;
import com.example.demo.controller.sujan.entity.SujanEntity;
import com.example.demo.controller.sujan.repository.SujanRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class SujanService {
	@Autowired
	SujanRepository sujanRepository;

	@Autowired
	private ModelMapper modelMapper;

	// insert data entry table
	public void insertData(SujanDto sujanDto) {

		sujanRepository.save(insert(sujanDto));
	}

	// date update

	private SujanEntity insert(SujanDto sujanDto) {
		Date now = new Date();
		SujanEntity dataEntry = new SujanEntity();
		modelMapper.map(sujanDto, dataEntry);
		dataEntry.setCreateDate(now);
		dataEntry.setUpdateDate(now);
		return dataEntry;

	}
}
