package in.ashokit.service.impl;

import java.util.List;
import java.util.Optional;

import io.micrometer.common.util.StringUtils;
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


    @Override
    public boolean addEnquiry(EnquiryDto enquiryDto, Integer counsellorId) {

        Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();

        Course course = courseRepo.findById(enquiryDto.getCourseId()).orElseThrow();

        Enquiry entity = new Enquiry();

        BeanUtils.copyProperties(enquiryDto, entity);

        entity.setCourse(course);
        entity.setCounsellor(counsellor);

        Enquiry savedEnq = enqRepo.save(entity);

        return savedEnq.getEnqId() != null;
    }

    @Override
    public List<Enquiry> getAllEnquiries(Integer counsellorId) {
        return enqRepo.findByCounsellorCounsellorId(counsellorId);
    }

    @Override
    public List<Enquiry> getEnquiriesWithFilter(EnqFilterRequestDto filterRequestDto, Integer counsellorId) {
        Enquiry entity = new Enquiry();

        Counsellor counsellor = counsellorRepo.findById(counsellorId).orElseThrow();
        entity.setCounsellor(counsellor);

        // if classMode selected then add in where clause
        if(!StringUtils.isEmpty(filterRequestDto.getClassMode())){
            entity.setClassMode(filterRequestDto.getClassMode());
        }

        // if enqStatus selected then add in where clause
        if(!StringUtils.isEmpty(filterRequestDto.getEnqStatus())){
            entity.setClassMode(filterRequestDto.getEnqStatus());
        }

        // if course selected then add in where clause
        if(filterRequestDto.getCourseId()!=null && filterRequestDto.getCourseId()>0){
            Course course = courseRepo.findById(filterRequestDto.getCourseId()).orElseThrow();
            entity.setCourse(course);
        }
        return enqRepo.findAll(Example.of(entity)); //QBE
    }

    @Override
    public Enquiry getEnquiryById(Integer enqId) {
        return enqRepo.findById(enqId).orElseThrow();
    }

    @Override
    public boolean updateEnquiry(EnquiryDto enquiryDto) {
        Optional<Enquiry> byId = enqRepo.findById(enquiryDto.getEnqId());
        if (byId.isPresent()) {
            Enquiry enquiry = byId.get();
            enquiry.setStuName(enquiryDto.getStuName());
            enquiry.setStuPhno(enquiryDto.getStuPhno());
            enquiry.setEnqStatus(enquiryDto.getEnqStatus());
            enqRepo.save(enquiry);
            return true;
        }
        return false;
    }
}
