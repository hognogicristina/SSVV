package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ServiceTest {

    private Validator<Student> studentValidator;

    private Validator<Tema> temaValidator;

    private Validator<Nota> notaValidator;

    private StudentXMLRepository studentXMLRepository;

    private TemaXMLRepository temaXMLRepository;

    private NotaXMLRepository notaXMLRepository;

    private Service service;

    @BeforeEach
    protected void setUp() {
        this.studentValidator = new StudentValidator();
        this.temaValidator = new TemaValidator();
        this.notaValidator = new NotaValidator();

        this.studentXMLRepository = new StudentXMLRepository(this.studentValidator, "studentiT.xml");
        this.temaXMLRepository = new TemaXMLRepository(this.temaValidator, "temeT.xml");
        this.notaXMLRepository = new NotaXMLRepository(this.notaValidator, "noteT.xml");

        this.service = new Service(this.studentXMLRepository, this.temaXMLRepository, this.notaXMLRepository);
    }

    @AfterEach
    protected void removeXML() {
        new File("studentiT.xml").delete();
        new File("temeT.xml").delete();
        new File("noteT.xml").delete();
    }

    static protected void createStudentsXML() {
        File xml = new File("studentiT.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<inbox>\n" + "\n" + "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static protected void createAssignmentsXML() {
        File xml = new File("temeT.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<inbox>\n" + "\n" + "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static protected void createNoteXML() {
        File xml = new File("noteT.xml");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(xml))) {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + "<inbox>\n" + "\n" + "</inbox>");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * STUDENT
     **/
    @org.junit.jupiter.api.Test
    public void testSaveStudent() {
        assertEquals(0, this.service.saveStudent("-1", "Ana", 933));
        assertEquals(1, this.service.saveStudent("1", "Ana", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentSuccess() {
        assertEquals(1, this.service.saveStudent("2", "Ana", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithEmptyId() {
        assertEquals(0, this.service.saveStudent("", "Ana", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithNullId() {
        assertEquals(0, this.service.saveStudent(null, "Ana", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithDuplicateId() {
        assertEquals(1, this.service.saveStudent("3", "Ana", 933));
        assertEquals(0, this.service.saveStudent("3", "Ana", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithEmptyName() {
        assertEquals(0, this.service.saveStudent("4", "", 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithNullName() {
        assertEquals(0, this.service.saveStudent("4", null, 933));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentWithNegativeGroup() {
        assertEquals(0, this.service.saveStudent("4", "Ana", -933));
    }

    /**
     * ASSIGNMENT
     **/
    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithNullId() {
        assertEquals(0, this.service.saveTema(null, "test", 12, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithEmptyId() {
        assertEquals(0, this.service.saveTema("", "test", 12, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithNullDescription() {
        assertEquals(0, this.service.saveTema("1", null, 12, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithEmptyDescription() {
        assertEquals(0, this.service.saveTema("1", "", 12, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidDeadlineLessThanOne() {
        assertEquals(0, this.service.saveTema("1", "test", 0, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidDeadlineGreaterThanFourteen() {
        assertEquals(0, this.service.saveTema("1", "test", 15, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidDeadlineBeforeStartline() {
        assertEquals(0, this.service.saveTema("1", "test", 5, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidStartlineLessThanOne() {
        assertEquals(0, this.service.saveTema("1", "test", 12, 0));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidStartlineGreaterThanFourteen() {
        assertEquals(0, this.service.saveTema("1", "test", 12, 15));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithInvalidStartlineAfterDeadline() {
        assertEquals(0, this.service.saveTema("1", "test", 12, 15));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignmentWithDuplicateId() {
        assertEquals(1, this.service.saveTema("2", "test", 12, 10));
        assertEquals(0, this.service.saveTema("2", "test", 12, 10));
    }

    @org.junit.jupiter.api.Test
    public void testSaveAssignment() {
        assertEquals(0, this.service.saveTema("-1", "test", 12, 10));
        assertEquals(1, this.service.saveTema("3", "test", 12, 10));
    }
}
