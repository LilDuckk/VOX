package VOX_Giat_La.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Salary")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Salary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salaryID;
    @OneToOne
    @JoinColumn(name = "salaryDetailID")
    private SalaryDetail salaryDetail;
    @OneToOne
    @JoinColumn
    private Work_Schedule schedule;
    @Column(name = "salaryPerDay")
    private float salaryPerDay;
    @Column(name = "salaryTotal")
    private float salaryTotal;
    @Column(name = "salaryCreateDate")
    private LocalDateTime salaryCreateDate;
    @PrePersist
    protected void onCreate() {
        salaryCreateDate = LocalDateTime.now();
    }
}
