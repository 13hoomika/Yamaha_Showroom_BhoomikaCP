package com.bcp.yamaha.repository.showroom;

import com.bcp.yamaha.entity.ShowroomEntity;

import java.util.List;

public interface ShowroomRepository {

    Boolean addShowroom(ShowroomEntity showroomEntity);
    List<ShowroomEntity> findAllShowroom();
    Long countAllShowroom();
    ShowroomEntity findById(Integer showroomId);

    boolean existByName(String showroomName);
    boolean existByEmail(String email);
    boolean existByPhno(String showroomPhone);

    void removeShowroom(ShowroomEntity showroom);
}
