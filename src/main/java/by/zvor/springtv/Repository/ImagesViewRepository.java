package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.ImagesView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ImagesViewRepository extends JpaRepository<ImagesView, Integer> {

    @Procedure(name = "AddNewImage")
    void addNewImage(@Param("imageName") String name, @Param("image") byte[] image, @Param("ImType") String type);


}