package in.ashokit.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.entities.Enquiry;
import in.ashokit.repo.CounsellorRepo;
import in.ashokit.repo.EnquiryRepo;
import in.ashokit.service.CounsellorService;

@Service
public class CounsellorServiceImpl implements CounsellorService {

    @Autowired
    private CounsellorRepo counsellorRepo;

    @Autowired
    private EnquiryRepo enquiryRepo;

    @Override
    public boolean register(Counsellor counsellor) {

        Counsellor savedCounsellor = counsellorRepo.save(counsellor);

        if (savedCounsellor.getCounsellorId() != null) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isEmailUnique(String email) {
        Optional<Counsellor> optional = counsellorRepo.findByEmail(email);
        if (optional.isPresent()) {
            return false;
        }
        return true;
    }

    @Override
    public Counsellor login(String email, String pwd) {

        Optional<Counsellor> byEmailAndPwd = counsellorRepo.findByEmailAndPwd(email, pwd);

        if(byEmailAndPwd.isPresent()){
            return byEmailAndPwd.get();
        }

        return null;
    }

    @Override
    public DashboardResponseDto getDashboardInfo(Integer counsellorId) {

        List<Enquiry> enquiryList = enquiryRepo.findByCounsellorCounsellorId(counsellorId);

        int totalEnqs = enquiryList.size();

        Map<String, Long> statusWiseMap = enquiryList.stream()
                .collect(Collectors.groupingBy(Enquiry::getEnqStatus, Collectors.counting()));

        int openCnt = statusWiseMap.getOrDefault("OPEN", 0l).intValue();
        int enrolledCnt = statusWiseMap.getOrDefault("ENROLLED", 0l).intValue();
        int lostCnt = statusWiseMap.getOrDefault("LOST", 0l).intValue();

        DashboardResponseDto dto = DashboardResponseDto.builder()
                .totalEnqs(totalEnqs)
                .enrolledEnqs(enrolledCnt)
                .openEnqs(openCnt)
                .lostEnqs(lostCnt)
                .build();

        return dto;
    }
}













