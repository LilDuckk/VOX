package VOX_Giat_La.Respones;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class UserListRespone {
    private List<UserRespone> userResponeList;
    private int totalPages;
}
