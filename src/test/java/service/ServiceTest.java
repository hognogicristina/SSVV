package service;

import domain.Student;
import domain.Tema;
import junit.framework.TestCase;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

public class ServiceTest extends TestCase {

    private Validator<Student> studentValidator;
    
    private Validator<Tema> temaValidator;
    
    private StudentXMLRepository studentXMLRepository;
    
    private TemaXMLRepository temaXMLRepository;
    
    private Service service;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studentValidator = new StudentValidator();
        temaValidator = new TemaValidator();
        
        studentXMLRepository = new StudentXMLRepository(studentValidator, "studenti.xml");
        temaXMLRepository = new TemaXMLRepository(temaValidator, "teme.xml");
        
        service = new Service(studentXMLRepository, temaXMLRepository, null);
    }

    /** STUDENT **/
    public void testSaveStudent() {
        assertEquals(0, service.saveStudent("-1", "Ana", 933));
        assertEquals(1, service.saveStudent("1", "Ana", 933));
    }

    public void testSaveStudentSuccess() {
        assertEquals(1, service.saveStudent("2", "Ana", 933));
    }

    public void testSaveStudentWithEmptyId() {
        assertEquals(0, service.saveStudent("", "Ana", 933));
    }

    public void testSaveStudentWithNullId() {
        assertEquals(0, service.saveStudent(null, "Ana", 933));
    }

    public void testSaveStudentWithDuplicateId() {
        assertEquals(1, service.saveStudent("3", "Ana", 933));
        assertEquals(0, service.saveStudent("3", "Ana", 933));
    }

    public void testSaveStudentWithEmptyName() {
        assertEquals(0, service.saveStudent("4", "", 933));
    }

    public void testSaveStudentWithNullName() {
        assertEquals(0, service.saveStudent("4", null, 933));
    }

    public void testSaveStudentWithNegativeGroup() {
        assertEquals(0, service.saveStudent("4", "Ana", -933));
    }

    /** ASSIGNMENT **/
    public void testSaveAssignmentWithNullId() {
        assertEquals(0, service.saveTema(null, "test", 12, 10));
    }

    public void testSaveAssignmentWithEmptyId() {
        assertEquals(0, service.saveTema("", "test", 12, 10));
    }

    public void testSaveAssignmentWithNullDescription() {
        assertEquals(0, service.saveTema("1", null, 12, 10));
    }

    public void testSaveAssignmentWithEmptyDescription() {
        assertEquals(0, service.saveTema("1", "", 12, 10));
    }

    public void testSaveAssignmentWithInvalidDeadlineLessThanOne() {
        assertEquals(0, service.saveTema("1", "test", 0, 10));
    }

    public void testSaveAssignmentWithInvalidDeadlineGreaterThanFourteen() {
        assertEquals(0, service.saveTema("1", "test", 15, 10));
    }

    public void testSaveAssignmentWithInvalidDeadlineBeforeStartline() {
        assertEquals(0, service.saveTema("1", "test", 5, 10));
    }

    public void testSaveAssignmentWithInvalidStartlineLessThanOne() {
        assertEquals(0, service.saveTema("1", "test", 12, 0));
    }

    public void testSaveAssignmentWithInvalidStartlineGreaterThanFourteen() {
        assertEquals(0, service.saveTema("1", "test", 12, 15));
    }

    public void testSaveAssignmentWithInvalidStartlineAfterDeadline() {
        assertEquals(0, service.saveTema("1", "test", 12, 15));
    }

    public void testSaveAssignmentWithDuplicateId() {
        assertEquals(1, service.saveTema("2", "test", 12, 10));
        assertEquals(0, service.saveTema("2", "test", 12, 10));
    }

    public void testSaveAssignment() {
        assertEquals(0, service.saveTema("-1", "test", 12, 10));
        assertEquals(1, service.saveTema("3", "test", 12, 10));
    }
}
