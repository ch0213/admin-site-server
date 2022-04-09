package admin.adminsiteserver.gallery.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Long> {
    Page<Gallery> findAll(Pageable pageable);
}
