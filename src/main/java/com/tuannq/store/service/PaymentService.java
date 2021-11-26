package com.tuannq.store.service;

import com.mservice.allinone.models.CaptureMoMoResponse;
import com.mservice.allinone.processor.allinone.CaptureMoMo;
import com.mservice.allinone.processor.allinone.QueryStatusTransaction;
import com.tuannq.store.model.request.IpnMomo;
import com.tuannq.store.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.tuannq.store.config.DefaultVariable.ENVIRONMENT_MOMO;

@Service
public class PaymentService {
    private final OrdersRepository ordersRepository;
    @Value("${endpoint}")
    private String ENDPOINT;

    @Autowired
    public PaymentService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    //TODO:Redirect to payment website => make url to redirect
    public String displayPaymentScreen(Long order_Id) {
        String payUrl = null;
        try {
            var orderOpt = ordersRepository.findById(order_Id);
            if (orderOpt.isEmpty()) return null;
            var order = orderOpt.get();

            var requestId = UUID.randomUUID().toString();
            var codeOrderIdMomo = UUID.randomUUID().toString();
            long amount = order.getTotalAmount();
            order.setTransactionCodeMomo(codeOrderIdMomo);
            ordersRepository.save(order);
            String orderInfo = "Thanh toán đơn hàng #".concat(order.getCode());
            String returnURL = ENDPOINT + "/order/payment/response/" + order.getTransactionCodeMomo() + "/" + requestId;
            String notifyURL = ENDPOINT + "/payment/response";
            String extraData = "";
            String bankCode = "SML";

            CaptureMoMoResponse captureMoMoResponse = CaptureMoMo.process(
                    ENVIRONMENT_MOMO,
                    codeOrderIdMomo,
                    requestId,
                    Long.toString(amount),
                    orderInfo,
                    returnURL,
                    notifyURL,
                    extraData
            );
            payUrl = captureMoMoResponse.getPayUrl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return payUrl;
    }

    //Todo: payment process
    public void IPNProcess(IpnMomo response) {
        try {
            System.out.println("Xu li IPN");
            if (response.getErrorCode() == 0) {
                var order = ordersRepository.findByCode(response.getOrderId());
                order.map(o -> {
                    o.setAlreadyPaid(true);
                    return o;
                }).map(ordersRepository::save);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    //Todo: return payment result
    public ResponseEntity<?> displayResultPayment(String orderId, String requestId) {
        try {
            //Transaction Query - Kiểm tra trạng thái giao dịch
            var queryStatusTransactionResponse = QueryStatusTransaction.process(ENVIRONMENT_MOMO, orderId, requestId);
            return ResponseEntity.ok(queryStatusTransactionResponse);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return (ResponseEntity<?>) ResponseEntity.noContent();
    }

}
