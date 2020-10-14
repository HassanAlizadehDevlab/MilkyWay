package com.android.presentation.common.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.common.error.ErrorHandler
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base class for all ViewModels.
 */
abstract class BaseViewModel : ViewModel() {

    /**
     *  [messageObservable] is Used for error handling and it Observed in each fragment
     * */
    val messageObservable: MutableLiveData<MessageData> = MutableLiveData()
    private val disposable = CompositeDisposable()

    /**
     * For getting error for each [Flowable] request
     * */
    protected fun <T> Flowable<T>.onError(): Flowable<T> =
        this.doOnError(::handleError)

    /**
     * For getting error for each [Observable] request
     * */
    protected fun <T> Observable<T>.onError(): Observable<T> =
        this.doOnError(::handleError)

    /**
     * For getting error for each [Single] request
     * */
    protected fun <T> Single<T>.onError(): Single<T> =
        this.doOnError(::handleError)

    /**
     * For getting error for each [Completable] request
     * */
    protected fun Completable.onError(): Completable =
        this.doOnError(::handleError)

    /**
     * For getting error for each [Maybe] request
     * */
    protected fun <T> Maybe<T>.onError(): Maybe<T> =
        this.doOnError(::handleError)


    private fun handleError(error: Throwable) {
        val errorMessage: String? = ErrorHandler.getError(error)
        messageObservable.value = MessageData(message = errorMessage)
    }

    protected fun Disposable.track() {
        disposable.add(this)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}