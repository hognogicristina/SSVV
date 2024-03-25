package service;

import domain.Student;
import junit.framework.TestCase;
import repository.StudentXMLRepository;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest extends TestCase {

    private Validator<Student> studentValidator;
    private StudentXMLRepository fileRepository1;
    private Service service;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        studentValidator = new StudentValidator();
        fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        service = new Service(fileRepository1, null, null);
    }

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

}
