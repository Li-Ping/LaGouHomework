import com.lagou.edu.dao.ResumeDao;
import com.lagou.edu.pojo.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.Optional;

/**
 * @author:LiPing
 * @description：
 * @date:Created in 14:39 2020/1/18 0018
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {

    @Autowired
    private ResumeDao resumeDao;

    @Test
    public void test01(){
        resumeDao.deleteByAddress("1");
    }

    @Test
    public void test02(){
        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");
                Predicate predicate = criteriaBuilder.equal(name, "张三");
                Predicate predicate1 = criteriaBuilder.like(address.as(String.class), "北京");
                Predicate and = criteriaBuilder.and(predicate, predicate1);

                return and;
            }
        };

        Optional<Resume> one = resumeDao.findOne(specification);
        System.out.println(one.get());
    }

    @Test
    public void test03(){
        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");
                //Predicate predicate = criteriaBuilder.equal(name, "张三");
                Predicate predicate1 = criteriaBuilder.like(address.as(String.class), "%1");
               // Predicate and = criteriaBuilder.and(predicate, predicate1);

                return predicate1;
            }
        };

        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 1, sort);
        Page<Resume> all = resumeDao.findAll(specification, pageable);
        for (Resume resume : all) {
            System.out.println(resume);
        }
    }
}
