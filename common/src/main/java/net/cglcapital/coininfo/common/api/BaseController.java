package net.cglcapital.coininfo.common.api;

import net.cglcapital.coininfo.common.model.BaseResponse;
import net.cglcapital.coininfo.common.model.Meta;
import org.springframework.http.ResponseEntity;

import static net.cglcapital.coininfo.common.constant.ErrorCode.SERVICE_CODE;

public abstract class BaseController {

    public ResponseEntity<Object> ok() {
        BaseResponse response = new BaseResponse(null, new Meta(200, 200, null, SERVICE_CODE, null, null));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> ok(Object payload) {
        BaseResponse response = new BaseResponse(payload, new Meta(200, 200, null, SERVICE_CODE, null, null));
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<Object> ok(Object payload, Meta meta) {
        BaseResponse response = new BaseResponse(payload, meta);
        return ResponseEntity.ok(response);
    }

}
