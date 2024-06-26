package VOX_Giat_La.Service.Storage;

import VOX_Giat_La.DTO.StorageDTO;
import VOX_Giat_La.Models.Storage;
import VOX_Giat_La.Respones.Storage.StorageRespone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IStorageService {
    Storage createStorage(StorageDTO storageDTO) throws Exception;
    Storage getStoragebyID(int id);
    Storage updateStorage(int id, StorageDTO storageDTO) throws Exception;
    void deleteStorage(int id);
    Page<StorageRespone> getListStorage(PageRequest pageRequest);
}
