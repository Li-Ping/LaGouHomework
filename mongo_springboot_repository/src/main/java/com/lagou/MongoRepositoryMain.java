package com.lagou;

import com.lagou.bean.Resume;
import com.lagou.repository.ResumeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class MongoRepositoryMain {
    public static void main(String[] args) {
        ApplicationContext  applicationContext = SpringApplication.run(MongoRepositoryMain.class,args);
        ResumeRepository  resumeRepository = applicationContext.getBean(ResumeRepository.class);

        /*List<Resume> all = resumeRepository.findAll();
        for (Resume resume : all) {
            System.out.println(resume);
        }*/

        Resume resume  = new Resume();
        resume.setName("temp");
        resume.setExpectSalary(1);
        resume.setCity("bj");
        resumeRepository.save(resume);

        System.out.println(resumeRepository.findByNameEquals("temp"));
    }
}
