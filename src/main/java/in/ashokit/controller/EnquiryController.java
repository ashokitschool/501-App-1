package in.ashokit.controller;

import java.util.List;

import in.ashokit.service.CourseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.ashokit.dto.EnqFilterRequestDto;
import in.ashokit.dto.EnquiryDto;
import in.ashokit.entities.Enquiry;
import in.ashokit.service.EnquiryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enqService;

	@Autowired
	private CourseService courseService;

	@GetMapping("/enquiry")
	public String enquiryForm(Model model){

		EnquiryDto enquiryDtoObj = new EnquiryDto();

		model.addAttribute("enquiry", enquiryDtoObj);
		model.addAttribute("courses", courseService.getCourses());

		return "add-enq";
	}

	@PostMapping("/enquiry")
	public String addEnquiry(EnquiryDto enquiryDto, Model model, HttpServletRequest request){

		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		boolean status = enqService.addEnquiry(enquiryDto, cid);

		if(status){
			model.addAttribute("smsg", "Enquiry Added");
		}else{
			model.addAttribute("emsg", "Enquiry Not Added");
		}

		model.addAttribute("courses", courseService.getCourses());

		return "add-enq";

	}

	@GetMapping("/view-enquiries")
	public String viewEnquiries(Model model, HttpServletRequest request){

		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		model.addAttribute("filterRequestDto", new EnqFilterRequestDto());
		model.addAttribute("enqs", enqService.getAllEnquiries(cid));
		model.addAttribute("courses", courseService.getCourses());

		return "view-enqs";

	}

	@PostMapping("/filter-enquiries")
	public String viewEnquiries(EnqFilterRequestDto filterRequestDto,Model model, HttpServletRequest request){

		HttpSession session = request.getSession(false);
		Integer cid = (Integer) session.getAttribute("CID");

		List<Enquiry> enquiriesWithFilter = enqService.getEnquiriesWithFilter(filterRequestDto, cid);

		model.addAttribute("enqs", enquiriesWithFilter);
		model.addAttribute("courses", courseService.getCourses());

		return "view-enqs";

	}

	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Integer enqId, Model model){
		Enquiry enquiryById = enqService.getEnquiryById(enqId);

		EnquiryDto dto = new EnquiryDto();
		BeanUtils.copyProperties(enquiryById, dto);
		dto.setCourseId(enquiryById.getCourse().getCourseId());

		model.addAttribute("enqDto", dto);
		model.addAttribute("courses", courseService.getCourses());

		return "add-enq";
	}
}
