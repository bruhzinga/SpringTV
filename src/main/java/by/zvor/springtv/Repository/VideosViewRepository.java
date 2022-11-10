package by.zvor.springtv.Repository;

import by.zvor.springtv.Entity.VideosView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface VideosViewRepository extends JpaRepository<VideosView, Integer> {
    @Procedure(name = "AddNewVideo")
    void addNewVideo(@Param("videoName") String name, @Param("video") byte[] video, @Param("videoType") String type);
}