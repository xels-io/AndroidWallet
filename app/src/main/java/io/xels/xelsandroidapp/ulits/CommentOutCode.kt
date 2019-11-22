package io.xels.xelsandroidapp.ulits

class CommentOutCode {

    //, Callback<NodeStatusApiResponse> in Login


    /*override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_USE_CAMERA -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "permission was granted! Do your stuff");

                } else {
                    Log.d(TAG, "permission denied! Disable the function related with permission.");
                }
            }
        }
    }*/

    /*override fun onFailure(call: Call<NodeStatusApiResponse>, t: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onResponse(call: Call<NodeStatusApiResponse>, response: Response<NodeStatusApiResponse>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }*/

    /*fun checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission not available requesting permission");
            ActivityCompat.requestPermissions(
                this,
                Array<String>(1) { Manifest.permission.CAMERA }, MY_PERMISSIONS_REQUEST_USE_CAMERA
            );
        } else {
            Log.d(TAG, "Permission has already granted");
        }
    }*/

    /*private fun checkNodeStatus(apiInterface: ApiInterface) {
        apiInterface.nodeStatusApi().enqueue(object : Callback<NodeStatusApiResponse?> {
            override fun onFailure(call: Call<NodeStatusApiResponse?>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<NodeStatusApiResponse?>,
                response: Response<NodeStatusApiResponse?>
            ) {

            }
        })
    }*/


    /*private fun callApi() {
       progress?.show()
       Log.d(TAG, "data: " + walletEditTxt.text.toString() + " " + passwordEditTxt.text.toString())

       val apiInterface: ApiInterface? = ApiClient.getClient()?.create(ApiInterface::class.java)
       Log.e(TAG, "request: " + AppConstance.load + walletEditTxt.text.toString() + passwordEditTxt.text.toString())
       apiInterface?.loadApiCall(AppConstance.load, walletEditTxt.text.toString(), passwordEditTxt.text.toString())
           ?.enqueue(
               object : Callback<LoadApiResponseModel> {
                   override fun onFailure(call: Call<LoadApiResponseModel>, t: Throwable) {
                       println(t?.localizedMessage)
                       progress?.dismiss()
                       Utils.showError(this@LoginActivity)
                   }

                   override fun onResponse(
                       call: Call<LoadApiResponseModel>,
                       response: Response<LoadApiResponseModel>
                   ) {
                       if (response.isSuccessful) {
                           println("got it")
                           PreferenceManager.updateValue(AppConstance.walletName, walletEditTxt.text.toString())
                           PreferenceManager.updateValue(AppConstance.isLogin, true)
                           PreferenceManager.updateValue(AppConstance.password, passwordEditTxt.text.toString())
                           gotoDashBoard()
                           progress?.dismiss()
                       } else {
                           progress?.dismiss()
                           println("try later")
                           Utils.handleErrorResponse(response, this@LoginActivity, response.code())
                       }
                   }
               })

   }*/
}