package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;
import org.example.psychology_center.config.CustomUserDetails;
import org.example.psychology_center.dao.entity.*;
import org.example.psychology_center.dao.repository.*;
import org.example.psychology_center.dto.request.SubmitDto;
import org.example.psychology_center.exception.NotFoundException;

import org.example.psychology_center.exception.ValidationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final AnswerRepository answerRepository;
    private final ResultRepository resultRepository;

    @Override
    public int submitTest(SubmitDto dto, User user) {


        Test test = testRepository.findById(dto.getTestId())
                .orElseThrow(() -> new NotFoundException("Test not found"));


        List<Answer> answers = answerRepository.findAllById(dto.getAnswerIds());

        if (answers.isEmpty()) {
            throw new NotFoundException("Answers not found");
        }


        boolean invalid = answers.stream()
                .anyMatch(a ->
                        a.getQuestion() == null ||
                                a.getQuestion().getTest() == null ||
                                !a.getQuestion().getTest().getId().equals(test.getId())
                );

        if (invalid) {
            throw new ValidationException("Answers do not belong to this test");
        }


        int totalScore = answers.stream()
                .mapToInt(Answer::getScore)
                .sum();

        Result result = Result.builder()
                .user(user)
                .test(test)
                .totalScore(totalScore)
                .build();

        resultRepository.save(result);

        return totalScore;
    }
}