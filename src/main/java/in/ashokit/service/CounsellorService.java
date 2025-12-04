package in.ashokit.service;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;

public interface CounsellorService {

        public boolean register(Counsellor counsellor);

        public boolean isEmailUnique(String email);

        public Counsellor login(String email, String pwd);

        public DashboardResponseDto getDashboardInfo(Integer counsellorId);

}