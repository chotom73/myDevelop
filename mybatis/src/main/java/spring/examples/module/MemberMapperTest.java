package spring.examples.module;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import spring.examples.DatabaseConfig;
import spring.examples.EnvironmentInitializer;
import spring.examples.MyBatisConfig;
import spring.examples.model.Member;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author arawn.kr@gmail.com
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DatabaseConfig.class, MyBatisConfig.class }
                    , initializers = EnvironmentInitializer.class)
@Transactional
public class MemberMapperTest {

	@Autowired MemberMapper memberMapper;

  @Test
  @Rollback(false)
  public void testCRUD() {
	  Member member = new Member();
    member.setEmail("arawn.kr@gmail.com");
    member.setName("Arawn Park");

    memberMapper.insert(member);
        		
    System.out.println("member.getId() : " + member.getId());
        

    Member findOne = memberMapper.findById(member.getId());
    assertThat(member.getId(), is(findOne.getId()));
    assertThat(member, is(findOne));
  }  
}