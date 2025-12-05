package in.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.ashokit.entities.Counsellor;

import java.util.Optional;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {

    // select * from counsellor_tbl where email=:email
    public Optional<Counsellor> findByEmail(String email);

    // select * from counsellor_tbl where email=:email and pwd=:pwd
    public Optional<Counsellor> findByEmailAndPwd(String email, String pwd);
}
