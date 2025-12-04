package in.ashokit.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Course;
import in.ashokit.entities.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private EnquiryRepo enqRepo;

	@Autowired
	private CounsellorRepo counsellorRepo;

	@Autowired
	private CourseRepo courseRepo;


}
