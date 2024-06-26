package VOX_Giat_La.Service.BillDetails;

import VOX_Giat_La.DTO.BillDetailsDTO;
import VOX_Giat_La.Exeception.DataNotFoundException;
import VOX_Giat_La.Models.*;
import VOX_Giat_La.Repositories.*;
import VOX_Giat_La.Respones.Bill.BillDetailListRespone;
import VOX_Giat_La.Respones.Bill.BillDetailRespone;
import VOX_Giat_La.Respones.Bill.BillRespones;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillDetailsService implements IBillDetailsService {
    private final BillRepos billRepos;
    private final BillDetailsRepos billDetailsRepos;

    private final Washing_MethodRepos washing_methodRepos;

    @Override
    @Transactional
    public BillDetails createBillDetails(int billID, BillDetailsDTO billDetailsDTO) throws DataNotFoundException {
        Bill Exitingbill = billRepos.findById(billID).orElseThrow(() -> new DataNotFoundException("Không tìm thấy bill với ID: " + billDetailsDTO.getBillID()));
        Washing_Method ExitingWash = washing_methodRepos.findById(billDetailsDTO.getWashID()).orElseThrow(() -> new DataNotFoundException("Không tìm thấy kiểu giặt với ID: " + billDetailsDTO.getWashID()));

        BillDetails newBillDetails = BillDetails.builder()
                .bill(Exitingbill)
                .wash(ExitingWash)
                .description(billDetailsDTO.getDescription())
                .weight(billDetailsDTO.getWeight())
                .billDetailStatus(false)
                .build();
        Exitingbill.addBillDetails(newBillDetails);
        billRepos.save(Exitingbill);
        return billDetailsRepos.save(newBillDetails);
    }

    @Override
    public BillDetails getBillDetailsByID(int billDetailsID) {
        return billDetailsRepos.findById(billDetailsID).orElseThrow(() -> new RuntimeException("Không tìm thấy bill"));
    }

    @Override
    public BillDetailListRespone getBillDetailsByBill(int billID, int page, int size) {
        Bill bill = billRepos.findById(billID).orElseThrow(() -> new RuntimeException("Không tìm thấy bill với ID: " + billID));
        Pageable pageable = PageRequest.of(page, size);
        Page<BillDetails> billDetailsPage = billDetailsRepos.findByBill(bill, pageable);

        List<BillDetailRespone> billDetailResponses = billDetailsPage.getContent().stream()
                .map(BillDetailRespone::fromBillDetail)
                .collect(Collectors.toList());

        //BillRespones billRespones = new BillRespones.(bill.getBillID(),bill.getUser().getUserID(), bill.getUser().getUsername(),bill.getBillDescription(),bill.getSumWeight(),bill.getCost(),bill.getBillCreateDate(),bill.getBillPayDate(), bill.getBillStatus());
        BillRespones billRespones = BillRespones.fromBill(bill);
        return BillDetailListRespone.builder()
                .billRespones(billRespones)
                .billDetail(billDetailResponses)
                .totalPages(billDetailsPage.getTotalPages())
                .build();
    }

    @Override
    @Transactional
    public BillDetails updateBillDetails(int id, BillDetailsDTO billDetailsDTO) throws DataNotFoundException {
        BillDetails billDetailsUpdate = getBillDetailsByID(id);
        Bill Exitingbill = billRepos.findById(billDetailsDTO.getBillID()).orElseThrow(() -> new DataNotFoundException("Không tìm thấy bill với ID: " + billDetailsDTO.getBillID()));
        Washing_Method ExitingWash = washing_methodRepos.findById(billDetailsDTO.getWashID()).orElseThrow(() -> new DataNotFoundException("Không tìm thấy kiểu giặt với ID: " + billDetailsDTO.getWashID()));

        billDetailsUpdate.setBill(Exitingbill);
        billDetailsUpdate.setWash(ExitingWash);


        billDetailsUpdate.setDescription(billDetailsDTO.getDescription());
        billDetailsUpdate.setWeight(billDetailsDTO.getWeight());
        billDetailsUpdate.setBillDetailStatus(billDetailsDTO.getBillDetailStatus());
        Exitingbill.addBillDetails(billDetailsUpdate);
        billRepos.save(Exitingbill);
        return billDetailsRepos.saveAndFlush(billDetailsUpdate);
    }

    @Override
    @Transactional
    public void deleteBillDetails(int id) {
        Optional<BillDetails> optionalBillDetails = billDetailsRepos.findById(id);
        if (optionalBillDetails.isPresent()) {
            BillDetails billDetails = optionalBillDetails.get();
            Bill bill = billDetails.getBill();
            bill.removeBillDetails(billDetails);
            billDetailsRepos.delete(billDetails);
            billRepos.save(bill); // Lưu lại Bill sau khi cập nhật
        }
    }

    @Override
    public Page<BillDetailRespone> getListBillDetails(PageRequest pageRequest) {
        return billDetailsRepos.findAll(pageRequest).map(billDetails -> BillDetailRespone.fromBillDetail(billDetails));
    }
}
