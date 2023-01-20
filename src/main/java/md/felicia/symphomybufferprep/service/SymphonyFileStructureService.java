package md.felicia.symphomybufferprep.service;

import md.felicia.symphomybufferprep.entity.SymphonyFileStructure;
import md.felicia.symphomybufferprep.repository.SymphonyFileStructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class SymphonyFileStructureService {
    private final SymphonyFileStructureRepository symphonyFileStructureRepository;

    @Autowired
    public SymphonyFileStructureService(SymphonyFileStructureRepository symphonyFileStructureRepository) {
        this.symphonyFileStructureRepository = symphonyFileStructureRepository;
    }

    public SymphonyFileStructure getSymphonyFileStructure(String fileName, String fieldName){
        return symphonyFileStructureRepository.findByFileNameIgnoreCaseAndFieldNameIgnoreCase(fileName, fieldName);
    }
    @Transactional
    public void updateSymphonyFileStructure(SymphonyFileStructure symphonyFileStructure){
        symphonyFileStructureRepository.save(symphonyFileStructure);
    }
}
