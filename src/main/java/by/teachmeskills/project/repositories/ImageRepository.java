package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Image;
import by.teachmeskills.project.exception.EntityOperationException;

public interface ImageRepository extends BaseRepository<Image>{
    Image getImageById(Integer id) throws EntityOperationException;
}
