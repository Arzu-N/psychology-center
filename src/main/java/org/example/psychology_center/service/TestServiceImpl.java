package org.example.psychology_center.service;

import lombok.RequiredArgsConstructor;

import org.example.psychology_center.dao.entity.Answer;
import org.example.psychology_center.dao.entity.Test;
import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dao.repository.AnswerRepository;
import org.example.psychology_center.dao.repository.TestRepository;
import org.example.psychology_center.dao.repository.UserRepository;
import org.example.psychology_center.dto.request.SubmitDto;
import org.example.psychology_center.exception.TestNotFound;
import org.example.psychology_center.exception.UserNotFound;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestRepository testRepository;
    private final UserRepository userRepository;
private final AnswerRepository answerRepository;

    @Override
    public int submitTest(SubmitDto dto) {
        Test test = testRepository.findById(dto.getUserId())
                .orElseThrow(() -> new TestNotFound("Test not found"));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFound("User not found"));
       int totalScore= answerRepository.findAllById(dto.getAnswerIds()).stream()
                .mapToInt(Answer::getScore)
                .sum();
       return totalScore;
    }
}
