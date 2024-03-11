package service;

import domain.Student;
import junit.framework.TestCase;
import repository.StudentXMLRepository;
import validation.StudentValidator;
import validation.Validator;

public class ServiceTest extends TestCase {

    public void testSaveStudent() {
        Validator<Student> studentValidator = new StudentValidator();
        StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
        Service service = new Service(fileRepository1, null, null);

        assertEquals(1, service.saveStudent("-1", "nume", 1));

        assertEquals(0, service.saveStudent("1", "test", 123));
    }
}