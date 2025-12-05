package in.ashokit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entities.Course;
import in.ashokit.repo.CourseRepo;
import in.ashokit.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}
