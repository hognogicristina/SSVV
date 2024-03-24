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
        assertEquals(1, service.saveStudent("-1", "nume", 1));
        assertEquals(0, service.saveStudent("1", "test", 123));
    }

    public void testSaveStudentSuccess() {
        assertEquals(0, service.saveStudent("2", "Alice", 234));
    }

    public void testSaveStudentWithEmptyId() {
        assertEquals(1, service.saveStudent("", "Bob", 355));
    }

    public void testSaveStudentWithNullId() {
        assertEquals(1, service.saveStudent(null, "Charlie", 444));
    }

    public void testSaveStudentWithDuplicateId() {
        assertEquals(1, service.saveStudent("111", "qwe", 535));
        assertEquals(0, service.saveStudent("111", "qwe", 535));
    }

}
