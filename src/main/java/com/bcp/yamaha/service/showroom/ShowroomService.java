package com.bcp.yamaha.service.showroom;

import com.bcp.yamaha.dto.ShowroomDto;

import java.util.List;

public interface ShowroomService {
    Boolean addShowroom(ShowroomDto showroomDto);
    List<ShowroomDto> getAllShowroom();
    Long getTotalShowroomCount();

    ShowroomDto getShowroomById(Integer showroomId);

    boolean existByName(String showroomName);
    boolean existByEmail(String email);

    boolean existByPhNumber(String showroomPhone);

    void deleteShowroomById(int id, String showroomUploadPath);
}
