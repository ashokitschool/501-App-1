package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Course;
import in.ashokit.entities.Enquiry;

public interface EnquiryService {
	
    public boolean addEnquiry(EnquiryDto enquiryDto);

    public List<Enquiry> getAllEnquiries(Integer counsellorId);

    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterRequestDto, Integer counsellorId);

    public Enquiry getEnquiryById(Integer enqId);

    public boolean updateEnquiry(EnquiryDto enquiryDto);

}