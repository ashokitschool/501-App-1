package in.ashokit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.dto.DashboardResponseDto;
import in.ashokit.entities.Counsellor;
import in.ashokit.service.CounsellorService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class CounsellorController {

    @Autowired
    private CounsellorService counsellorService;

    @GetMapping("/")
    public String index(Model model) {

        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);

        return "index";
    }

    @PostMapping("/login")
    public String login(Counsellor counsellor, Model model, HttpServletRequest request) {

        Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPwd());

        if (c == null) {
            model.addAttribute("emsg", "Invalid Credentials");
            return "index";
        } else {
            HttpSession session = request.getSession(true);
            session.setAttribute("CID", c.getCounsellorId());
            return "redirect:/dashboard";
        }
    }

    @GetMapping("/dashboard")
    public String buildDashboard(Model model, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        Integer cid = (Integer) session.getAttribute("CID");

        DashboardResponseDto dashboardInfoDto = counsellorService.getDashboardInfo(cid);
        model.addAttribute("dashboardInfo", dashboardInfoDto);

        return "dashboard";

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect : /";
    }

    @GetMapping("/register")
    public String register(Model model) {

        Counsellor cobj = new Counsellor();
        model.addAttribute("counsellor", cobj);

        return "register";

    }

    @PostMapping("/register")
    public String handleRegistration(Counsellor counsellor, Model model) {

        if (!counsellorService.isEmailUnique(counsellor.getEmail())) {
            model.addAttribute("emsg", "Duplicate Email Found");
            return "register";
        }
        boolean registered = counsellorService.register(counsellor);

        if (registered) {
            model.addAttribute("smsg", "Registration successful, please login");
        } else {
            model.addAttribute("emsg", "Registration failed, try again");
        }

        return "register";
    }

}
