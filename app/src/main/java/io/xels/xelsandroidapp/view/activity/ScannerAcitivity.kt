package io.xels.xelsandroidapp.view.activity

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.util.Log
import com.google.zxing.Result
import io.xels.xelsandroidapp.request_model.ScannerResultModel
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.greenrobot.eventbus.EventBus



class ScannerAcitivity : FragmentActivity(), ZXingScannerView.ResultHandler {


    var TAG: String = "ScannerAcitivity"

    private var mScannerView: ZXingScannerView? = null


    override fun handleResult(p0: Result?) {
        Log.v(TAG, p0?.getText()); // Prints scan results
        Log.v(TAG, p0?.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)


        EventBus.getDefault().post(ScannerResultModel(p0?.getText()))
        mScannerView?.stopCamera()           // Stop camera on pause
        finish()

        // If you would like to resume scanning, call this method below:



    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mScannerView = ZXingScannerView(this);
        setContentView(mScannerView)
        mScannerView?.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView?.startCamera()
        mScannerView?.setAutoFocus(true)
    }


/*    public override fun onResume() {
        super.onResume()
        // Start camera on resume
    }

    public override fun onPause() {
        super.onPause()
        mScannerView?.stopCamera()           // Stop camera on pause
    }*/


}