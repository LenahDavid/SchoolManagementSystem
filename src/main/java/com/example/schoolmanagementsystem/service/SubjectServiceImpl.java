package com.example.schoolmanagementsystem.service;

import com.example.schoolmanagementsystem.dto.SubjectRequest;
import com.example.schoolmanagementsystem.entity.Subject;
import com.example.schoolmanagementsystem.entity.Teacher;
import com.example.schoolmanagementsystem.exceptions.SubjectNotFoundException;
import com.example.schoolmanagementsystem.repository.SubjectRepository;
import com.example.schoolmanagementsystem.repository.TeacherRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Autowired
    public SubjectServiceImpl(SubjectRepository subjectRepository, TeacherRepository teacherRepository) {
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Subject addSubject(SubjectRequest subjectRequest) {
        // Obtain the teacher using the provided teacherId
        Optional<Teacher> teacherOptional = teacherRepository.findById(subjectRequest.getTeacherId());

        // Check if the teacher exists
        Teacher teacher = teacherOptional.orElseThrow(() -> new IllegalArgumentException("Teacher with ID " + subjectRequest.getTeacherId() + " not found."));

        // Create a new Subject object and populate it with data from the SubjectRequest
        Subject subject = new Subject();
        subject.setName(subjectRequest.getSubjectName());
        subject.setTeacher(teacher);

        // Save the subject using SubjectRepository
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findSubjectByName(String name) {
        return subjectRepository.findByName(name);
    }
    @Override
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject findById(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject  with id: " + id + " not found"));
    }
    @Override
    public Subject updateSubject(Long id, SubjectRequest subjectRequest) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject  with id: " + id + " not found"));

        existingSubject.setName(subjectRequest.getSubjectName());

        return subjectRepository.save(existingSubject);
    }
    @Override
    public void deleteSubject(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new SubjectNotFoundException("Subject with id: " + id + " not found"));
        subjectRepository.delete(subject);
    }
}

