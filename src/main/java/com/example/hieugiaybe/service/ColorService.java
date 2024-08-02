package com.example.hieugiaybe.service;

import com.example.hieugiaybe.dto.ColorAndSizeDto;
import com.example.hieugiaybe.entities.Color;
import com.example.hieugiaybe.exception.EmptyDataException;
import com.example.hieugiaybe.exception.NotFoundException;
import com.example.hieugiaybe.repository.ColorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ColorService {
    private final ColorRepository colorRepository;

    public ColorService(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    public ColorAndSizeDto getColorById(Integer id){
        Optional<Color> checkingColor = colorRepository.findById(id);
        if (checkingColor.isEmpty()){
            throw new NotFoundException("Color does not exist!");
        }
        Color checkedColor = checkingColor.get();
        ColorAndSizeDto colorAndSizeDto = new ColorAndSizeDto(
                checkedColor.getId(),
                checkedColor.getTitle()
        );
        return colorAndSizeDto;
    }

    public List<ColorAndSizeDto> getAllColor(){
        List<Color> listColor = colorRepository.findAll();
        if (listColor.isEmpty()){
            throw new EmptyDataException("Color list does not exist!");
        }
        List<ColorAndSizeDto> response = new ArrayList<>();
        for (Color color : listColor){
            ColorAndSizeDto newColor = new ColorAndSizeDto(
                    color.getId(),
                    color.getTitle()
            );
            response.add(newColor);
        }
        return response;
    }

    public ColorAndSizeDto createColor(ColorAndSizeDto colorAndSizeDto){
        Color newColor = new Color(
                colorAndSizeDto.getId(),
                colorAndSizeDto.getTitle(),
                null
        );

        Color savedColor = colorRepository.save(newColor);
        ColorAndSizeDto response = new ColorAndSizeDto(
                savedColor.getId(),
                savedColor.getTitle()
        );
        return response;
    }

    public ColorAndSizeDto updateColorById(Integer id, ColorAndSizeDto colorAndSizeDto){
        Color checkingColor = colorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Color does not exist!"));
        checkingColor.setId(colorAndSizeDto.getId());
        checkingColor.setTitle(colorAndSizeDto.getTitle());
        Color savedColor = colorRepository.save(checkingColor);
        ColorAndSizeDto response = new ColorAndSizeDto(
                savedColor.getId(),
                savedColor.getTitle()
        );
        return response;
    }

    public String deleteCategoryById(Integer id){
        Color checkingColor = colorRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Color does not exist!"));
        Integer colorId = checkingColor.getId();
        colorRepository.deleteById(colorId);
        return "Color delete with id: " + colorId;
    }
}
