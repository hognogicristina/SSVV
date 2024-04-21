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

public class ServiceIntegrationTest {

    private Validator<Student> studentValidator;

    private Validator<Tema> temaValidator;

    private Validator<Nota> notaValidator;

    private StudentXMLRepository studentXMLRepository;

    private TemaXMLRepository temaXMLRepository;

    private NotaXMLRepository notaXMLRepository;

    private Service service;

    @BeforeEach
    protected void setUp() {
        createStudentsXML();
        createAssignmentsXML();
        createNoteXML();

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

    @org.junit.jupiter.api.Test
    public void testSaveStudent() {
        assertEquals(1, this.service.saveStudent("1", "Maria Giorgila", 500));
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentSaveAssignmentIntegration() {
        assertEquals(1, this.service.saveStudent("1", "Maria Giorgila", 500));
        assertEquals(1, this.service.saveTema("1", "PHP", 12, 1), "meow");
    }

    @org.junit.jupiter.api.Test
    public void testSaveStudentSaveAssignmentSaveGradeIntegration() {
        assertEquals(1, this.service.saveStudent("1", "Maria Giorgila", 500));
        assertEquals(1, this.service.saveTema("1", "PHP", 12, 1));
        assertEquals(1, this.service.saveNota("1", "1", 9.5, 1, "buenos kitkat"), "alahakbar");
    }

}