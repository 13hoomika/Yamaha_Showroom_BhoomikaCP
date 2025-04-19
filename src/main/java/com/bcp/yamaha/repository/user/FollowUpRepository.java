package com.bcp.yamaha.repository.user;

import com.bcp.yamaha.entity.FollowUpEntity;

import java.util.List;

public interface FollowUpRepository {
    boolean save(FollowUpEntity followUpEntity);

    List<FollowUpEntity> findByUserId(int userId);

    List<FollowUpEntity> findAllFollowUps();
}
