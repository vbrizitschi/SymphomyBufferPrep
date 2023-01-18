package md.felicia.symphomybufferprep.repository;

import md.felicia.symphomybufferprep.entity.SymphonyFileStructure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SymphonyFileStructureRepository extends JpaRepository<SymphonyFileStructure, Integer> {
    @Query("select p from SymphonyFileStructure p " +
            "where p.fileName = :fileName and p.fieldName = :fieldName")
    SymphonyFileStructure findByFileNameIgnoreCaseAndFieldNameIgnoreCase(@Param("fileName") String fileName, @Param("fieldName") String fieldName);

    @Query("update SymphonyFileStructure  p set p.avoidWhenUpdate = :updateFlag where upper(p.fieldName) = :fieldName and upper(p.fileName) = :fileName")
    @Modifying
    void setAvoidWhenUpdate(@Param("fileName") String fileName, @Param("fieldName") String fieldName, @Param("updateFlag") int updateFlag);
}
