package com.healthtunnel.data.network


abstract class ApiCallback {
    abstract fun onSuccess(obj: Any?)

    open fun onError(message: String?) {
        NetworkResponseActionHandler.showServerError(message)
        onHandledError()
    }

    open fun onUnAuthentic() {
        NetworkResponseActionHandler.onUnAuthenticDo()
        onHandledError()
    }

    open fun onNetworkError() {
        NetworkResponseActionHandler.networkErrorPage()
        onHandledError()
    }

    abstract fun onHandledError()
}
