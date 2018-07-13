package io.loot.lootsdk.models.data.signup;

import android.support.annotation.Nullable;

import okhttp3.ResponseBody;

public class FailedScansContainer {

    private ResponseBody idScanBody;
    private ResponseBody backIdScanBody;
    private ResponseBody faceScanBody;

    public FailedScansContainer(ResponseBody idScanBody, ResponseBody backIdScanBody, ResponseBody faceScanBody) {
        this.idScanBody = idScanBody;
        this.backIdScanBody = backIdScanBody;
        this.faceScanBody = faceScanBody;
    }

    public FailedScansContainer() {

    }

    @Nullable
    public ResponseBody getBackIdScanBody() {
        return backIdScanBody;
    }

    public void setBackIdScanBody(ResponseBody backIdScanBody) {
        this.backIdScanBody = backIdScanBody;
    }

    @Nullable
    public ResponseBody getFaceScanBody() {
        return faceScanBody;
    }

    public void setFaceScanBody(ResponseBody faceScanBody) {
        this.faceScanBody = faceScanBody;
    }

    @Nullable
    public ResponseBody getIdScanBody() {
        return idScanBody;
    }

    public void setIdScanBody(ResponseBody idScanBody) {
        this.idScanBody = idScanBody;
    }
}
