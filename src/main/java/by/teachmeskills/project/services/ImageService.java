package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Image;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.Optional;

public interface ImageService extends BaseService<Image>{
    Optional<Image> getImageById(Integer id) throws EntityOperationException;
}
