package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Image;

import java.util.Optional;

public interface ImageService extends BaseService<Image>{
    Optional<Image> getImageById(Integer id);
}
