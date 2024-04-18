package VOX_Giat_La.Respones;

import VOX_Giat_La.Models.Bill;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillRespones {
    private int userID;
    private String userName;

    private String billDescription;

    private float sumWeight;
    private Boolean billStatus;

    private float cost;

    private String image;

    private Date billPayDate;

    private LocalDateTime billCreateDate;

    public static BillRespones fromBill(Bill bill) {
        BillRespones billRespones =BillRespones.builder()
                .userID(bill.getUser().getUserID())
                .userName(bill.getUser().getUserName())
                .billDescription(bill.getBillDescription())
                .sumWeight(bill.getSumWeight())
                .cost(bill.getCost())
                .billStatus(bill.getBillStatus())
                .image(bill.getImage())
                .billPayDate(bill.getBillPayDate())
                .build();
        billRespones.setBillCreateDate(bill.getBillCreateDate());
        billRespones.setBillPayDate(bill.getBillPayDate());
        return billRespones;
    }

}
