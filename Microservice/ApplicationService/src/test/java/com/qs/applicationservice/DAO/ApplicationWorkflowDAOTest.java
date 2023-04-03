package com.qs.applicationservice.DAO;
import com.qs.applicationservice.dao.ApplicationWorkflowDAO;
import com.qs.applicationservice.entity.ApplicationWorkflow;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
class ApplicationWorkflowDAOTest {
    @Mock
    private SessionFactory sessionFactory;
//    @Autowired
    private ApplicationWorkflowDAO applicationWorkflowDAO;

    @BeforeEach
    void setUp() {
        applicationWorkflowDAO = new ApplicationWorkflowDAO(sessionFactory);
    }

    @Test
    void testCreate() {
        // given
        ApplicationWorkflow applicationWorkflow = new ApplicationWorkflow();
        applicationWorkflow.setEmployee_id("123");
        applicationWorkflow.setStatus(ApplicationWorkflow.STATUS_PENDING);

        // When
        int id = applicationWorkflowDAO.create(applicationWorkflow);

        // Then
        Assertions.assertNotEquals(0, id);
    }

}
