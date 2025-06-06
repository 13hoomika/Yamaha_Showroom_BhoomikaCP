package com.bcp.yamaha.service.followup;

import com.bcp.yamaha.dto.FollowUpDto;

import java.util.List;

public interface FollowUpService {

    List<FollowUpDto> getFollowUpLogsByUserId(int userId);
    boolean saveFollowUp(FollowUpDto dto);

    List<FollowUpDto> getAllFollowups();
}
