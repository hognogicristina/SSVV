package service;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigBangIntegrationTest {
    private Service service;
    private StudentXMLRepository studentXMLRepository;
    private TemaXMLRepository temaXMLRepository;
    private NotaXMLRepository notaXMLRepository;

    @BeforeEach
    void setUp() {
        createStudentsXML();
        createAssignmentsXML();
        createNoteXML();

        Validator<Student> studentValidator = new StudentValidator();
        Validator<Tema> temaValidator = new TemaValidator();
        Validator<Nota> notaValidator = new NotaValidator();

        studentXMLRepository = new StudentXMLRepository(studentValidator, "studentiT.xml");
        temaXMLRepository = new TemaXMLRepository(temaValidator, "temeT.xml");
        notaXMLRepository = new NotaXMLRepository(notaValidator, "noteT.xml");

        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
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

    @Test
    public void testSaveStudent() {
        assertDoesNotThrow(() -> service.saveStudent("10", "iakab", 345));

        Iterable<Student> students = studentXMLRepository.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "10");
        assertEquals(studentList.get(0).getNume(), "iakab");
        assertEquals(studentList.get(0).getGrupa(), 345);
    }

    @Test
    public void testSaveTema() {
        assertDoesNotThrow(() -> service.saveTema("10", "buna ziua", 5, 2));

        Iterable<Tema> assignments = temaXMLRepository.findAll();
        ArrayList<Tema> assignmentList = new ArrayList<>();
        assignments.forEach(assignmentList::add);

        assertEquals(assignmentList.size(), 1);
        assertEquals(assignmentList.get(0).getID(), "10");
        assertEquals(assignmentList.get(0).getDescriere(), "buna ziua");
        assertEquals(assignmentList.get(0).getDeadline(), 5);
        assertEquals(assignmentList.get(0).getStartline(), 2);
    }

    @Test
    public void testSaveNota() {
        assertEquals(1, service.saveStudent("10", "Ana", 345));
        assertEquals(1, service.saveTema("10", "test", 2, 1));
        assertEquals(1, service.saveNota("10", "10", 2, 5, "meow"));
    }

    @Test
    public void testAll() {
        assertDoesNotThrow(() -> service.saveStudent("10", "iakab", 345));
        assertDoesNotThrow(() -> service.saveTema("10", "buna ziua", 2, 1));
        assertDoesNotThrow(() -> service.saveNota("10", "10", 2, 5, "meow"));

        Iterable<Student> students = studentXMLRepository.findAll();
        ArrayList<Student> studentList = new ArrayList<>();
        students.forEach(studentList::add);

        Iterable<Tema> assignments = temaXMLRepository.findAll();
        ArrayList<Tema> assignmentList = new ArrayList<>();
        assignments.forEach(assignmentList::add);

        Iterable<Nota> grades = notaXMLRepository.findAll();
        ArrayList<Nota> gradeList = new ArrayList<>();
        grades.forEach(gradeList::add);

        assertEquals(studentList.size(), 1);
        assertEquals(studentList.get(0).getID(), "10");
        assertEquals(studentList.get(0).getNume(), "iakab");
        assertEquals(studentList.get(0).getGrupa(), 345);

        assertEquals(assignmentList.size(), 1);
        assertEquals(assignmentList.get(0).getID(), "10");
        assertEquals(assignmentList.get(0).getDescriere(), "buna ziua");
        assertEquals(assignmentList.get(0).getDeadline(), 2);
        assertEquals(assignmentList.get(0).getStartline(), 1);

        assertEquals(gradeList.size(), 1);
        assertEquals(gradeList.get(0).getID(), new Pair<>("10", "10"));
        assertEquals(gradeList.get(0).getNota(), 1);
        assertEquals(gradeList.get(0).getSaptamanaPredare(), 5);
        assertEquals(gradeList.get(0).getFeedback(), "meow");
    }
}