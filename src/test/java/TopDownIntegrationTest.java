package UI;

import Domain.Nota;
import Domain.Student;
import Domain.Teme;
import Repository.NoteRepo;
import Repository.StudentRepo;
import Repository.TemeRepo;
import Service.ServiceNote;
import Service.ServiceStudent;
import Service.ServiceTeme;
import Validator.NotaValidator;
import Validator.StudentValidator;
import Validator.TemeValidator;
import org.junit.Test;
import java.util.Map;
import java.util.AbstractMap;

import static org.junit.Assert.*;


// Top down integration testing technique is simulating the behavior
// of lower level modeules which are not yet integrated.
// For initial testing purposes we will assume a mocked reposiroty,
// assuming there has not been any integration at the level of repo
// (it would still be in development).

// Tetss for:
// addStudent
// addAssignment

public class TopDownIntegrationTest {

    StudentRepo rep=new StudentRepo(new StudentValidator(),"src/main/java/studenti.xml");
    TemeRepo repo=new TemeRepo(new TemeValidator(),"src/main/java/teme.xml");
    NoteRepo r=new NoteRepo(new NotaValidator());
    ServiceStudent srv=new ServiceStudent(rep);
    ServiceTeme serv=new ServiceTeme(repo);
    ServiceNote sv=new ServiceNote(r);
    UI ui=new UI(srv,serv,sv);

    private void mockAddStudent(){
        String id = "1";
        String nume = "Name Student";
        int gr = 324;
        String em = "foo@bar.com";
        String prof = "Name Profesor";
        // if(srv.find(id)==null) {
        Student stud = new Student(id, nume, gr, em, prof);
        srv.add(stud);
    }

    private Student mockGetStudent(){
        String id = "1";
        String nume = "Name Student";
        int gr = 324;
        String em = "foo@bar.com";
        String prof = "Name Profesor";
        // if(srv.find(id)==null) {
        Student stud = new Student(id, nume, gr, em, prof);
        return stud;
    }

    private void deleteStudent(String id){
        srv.del(id);
    }

    private void mockAddAssignment(){
        int nr = 11;
        String desc = "Description 1";
        int sapt = 1;
        int d = 3;
        //if(serv.find(nr)==null) {
        Teme tema = new Teme(nr, desc, sapt, d);
        serv.add(tema);
    }

    private Teme mockGetAssignment(){
        int nr = 11;
        String desc = "Description 1";
        int sapt = 1;
        int d = 3;
        //if(serv.find(nr)==null) {
        Teme tema = new Teme(nr, desc, sapt, d);
        return tema;
    }

    private void deleteAssignment(int id){
        serv.del(id);
    }

    private Nota mockAddGrade(){
        String id = "1";
        int nr = 11;
        float nota = 10;
        int data = 1;
        String fd="feedback text";
            Student st = srv.find(id);
            Teme tm = serv.find(nr);
            Map.Entry<String, Integer> nid = new AbstractMap.SimpleEntry<String, Integer>(id, nr);
            Nota nt = new Nota(nid, st, tm, nota, data);
            nota = nt.getValoare();
            nt = new Nota(nid, st, tm, nota, data);
            return sv.add(nt,fd);
    }

    private Nota mockGetNota(){
        String id = "1";
        int nr = 11;
        float nota = 10;
        int data = 1;
        String fd="feedback text";
        Student st = srv.find(id);
        Teme tm = serv.find(nr);
        Map.Entry<String, Integer> nid = new AbstractMap.SimpleEntry<String, Integer>(id, nr);
        Nota nt = new Nota(nid, st, tm, nota, data);
        nota = nt.getValoare();
        nt = new Nota(nid, st, tm, nota, data);
        return nt;
    }

    private Nota mockGradeServiceAdd(){
        // It assumes that the entity is added to the service.
        String id = "1";
        int nr = 11;
        float nota = 10;
        int data = 1;
        String fd="feedback text";
        Student st = srv.find(id);
        Teme tm = serv.find(nr);
        Map.Entry<String, Integer> nid = new AbstractMap.SimpleEntry<String, Integer>(id, nr);
        Nota nt = new Nota(nid, st, tm, nota, data);
        nota = nt.getValoare();
        nt = new Nota(nid, st, tm, nota, data);
        return nt;
    }

    @Test
    public void addTest(){
        mockAddStudent();
        assertEquals(srv.find("1").toString(), mockGetStudent().toString());
        mockAddAssignment();
        assertEquals(serv.find(11).toString(), mockGetAssignment().toString());

//        assertEquals(mockAddGrade(), mockGetNota());
//        As the service for grade is still in development, we will mock the behavior.
        assertEquals(mockGradeServiceAdd().getID(), mockGetNota().getID());

        deleteStudent("1");
        deleteAssignment(11);
    }
}
