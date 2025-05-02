package com.bcp.yamaha.service.showroom;

import com.bcp.yamaha.dto.ShowroomDto;
import com.bcp.yamaha.entity.ShowroomEntity;
import com.bcp.yamaha.repository.showroom.ShowroomRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowroomServiceImpl implements ShowroomService {

    @Autowired
    ShowroomRepository showroomRepository;

    @Transactional
    @Override
    public Boolean addShowroom(ShowroomDto showroomDto) {
        ShowroomEntity showroomEntity = new ShowroomEntity();
        BeanUtils.copyProperties(showroomDto,showroomEntity);
        return showroomRepository.addShowroom(showroomEntity);
    }


    @Override
    public List<ShowroomDto> getAllShowroom() {
        List<ShowroomEntity> showroomEntities = showroomRepository.findAllShowroom()
                .stream()
                .distinct() // Ensure unique entities
                .collect(Collectors.toList());

        List<ShowroomDto> dtoList = new ArrayList<>();

        for (ShowroomEntity entity : showroomEntities) {
            ShowroomDto dto = new ShowroomDto();

            // Manual copy to ensure all fields are set
            dto.setShowroomId(entity.getShowroomId());
            dto.setShowroomName(entity.getShowroomName());
            dto.setShowroomAddress(entity.getShowroomAddress());
            dto.setShowroomPhone(entity.getShowroomPhone());
            dto.setShowroomEmail(entity.getShowroomEmail());
            dto.setShowroomManager(entity.getShowroomManager());
            dto.setBikeCount(entity.getBikeCount());
            dto.setShowroomImg(entity.getShowroomImg());
            // OR Calculate bike count from the relationship
            //dto.setBikeCount(entity.getBikes() != null ? entity.getBikes().size() : 0);
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Override
    public Long getTotalShowroomCount() {
        return showroomRepository.countAllShowroom();
    }

    @Override
    public ShowroomDto getShowroomById(Integer showroomId) {
        if (showroomId == null) return null;
        ShowroomEntity entity = showroomRepository.findById(showroomId);
        if (entity != null) {
            ShowroomDto dto = new ShowroomDto();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        }
        return null;
    }
}
