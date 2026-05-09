package org.example.psychology_center.service;

import org.example.psychology_center.dao.entity.User;
import org.example.psychology_center.dto.request.SubmitDto;

public interface TestService {
    public int submitTest(SubmitDto dto, User user);
}
