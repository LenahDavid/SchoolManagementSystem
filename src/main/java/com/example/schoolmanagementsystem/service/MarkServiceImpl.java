package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.MarkRequest;
import com.example.schoolmanagementsystem.entity.Mark;
import com.example.schoolmanagementsystem.entity.Student;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.repository.MarkRepository;
import com.example.schoolmanagementsystem.repository.StudentRepository;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarkServiceImpl implements MarkService {
private final MarkRepository markRepository;
private final SubjectRepository subjectRepository;
private final StudentRepository studentRepository;
@Autowired
public MarkServiceImpl(MarkRepository markRepository,SubjectRepository subjectRepository,StudentRepository studentRepository) {
    this.markRepository = markRepository;
    this.subjectRepository = subjectRepository;
    this.studentRepository = studentRepository;
}
@Override
    public Mark addMark(MarkRequest markRequest) {
    Optional<Subject> subjectOptional = subjectRepository.findById(markRequest.getSubjectId());
    Subject subject = subjectOptional.orElseThrow(() -> new IllegalArgumentException("Subject with ID " + markRequest.getSubjectId() + " not found."));

    Optional<Student> studentOptional = studentRepository.findById(markRequest.getStudentId());
    Student student = studentOptional.orElseThrow(() -> new IllegalArgumentException("Student with ID " + markRequest.getStudentId() + " not found."));

        Mark mark = new Mark();
        mark.setMarkValue(markRequest.getMarkValue());
        mark.setSubject(subject);
        mark.setStudent(student);
        return markRepository.save(mark);
    }



    public List<Mark> getMarksByStudent(Long studentId) {
        return markRepository.findByStudentId(studentId);
    }
@Override
    public Mark updateMark(Long id, MarkRequest markRequest) {
        Optional<Mark> markOptional = markRepository.findById(id);
        Mark mark = markOptional.orElseThrow(() -> new IllegalArgumentException("Mark with ID " + id + " not found."));
        mark.setMarkValue(markRequest.getMarkValue());
        return markRepository.save(mark);
    }
    @Override
    public void deleteMark(Long id) {
       Mark mark = markRepository.findById(id)
               .orElseThrow(() -> new IllegalArgumentException("Mark with ID " + id + " not found."));
       markRepository.delete(mark);
    }
}
