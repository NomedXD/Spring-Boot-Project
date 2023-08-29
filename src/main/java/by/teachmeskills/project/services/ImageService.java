package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Image;
import by.teachmeskills.project.exception.EntityOperationException;

public interface ImageService extends BaseService<Image>{
    Image getImageById(Integer id) throws EntityOperationException;
}
