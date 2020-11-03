package com.rohan.movieshd.data.repository

enum class Status {
    RUNNING, //RUNNING
    SUCCESS, //SUCCESS
    FAILED //FAILED
    ,
    EOFLIST
}


class NetworkState(val status: Status, val msg: String) {

    companion object {
        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val EOLIST: NetworkState //EndOfList

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "RUNNING")
            ERROR = NetworkState(Status.FAILED, "Success")
            EOLIST = NetworkState(Status.EOFLIST, "Success")
        }


    }


}