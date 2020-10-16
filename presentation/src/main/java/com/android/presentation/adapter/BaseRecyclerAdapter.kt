package com.android.presentation.adapter

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.domain.entity.DomainObject
import com.android.domain.entity.LoadMoreObject
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hassanalizadeh on 18,February,2019
 */
abstract class BaseRecyclerAdapter() : RecyclerView.Adapter<BaseViewHolder<out DomainObject>>(),
    IRecyclerAdapter {

    private var loadMoreObservable: PublishSubject<LoadMoreState> = PublishSubject.create()
    protected var mItems: MutableList<DomainObject> = mutableListOf()
    var loadMoreState: LoadMoreState = LoadMoreState.NOT_LOAD
    var isFinished: Boolean = false
    private var mConfig: Config? = null
    private var disposable = CompositeDisposable()
    private lateinit var layoutManager: LinearLayoutManager


    constructor(config: Config) : this() {
        mConfig = config
        subscribeLoadMore()
    }

    override fun getItemViewType(position: Int): Int {
        return ViewTypeHolder.getView(mItems[position])
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<out DomainObject>, position: Int) {
        holder.bind(mItems[position])
    }

    private fun loadMore() {
        if (mConfig != null) {
            if (loadMoreState == LoadMoreState.NOT_LOAD &&
                mItems.size > mConfig!!.screenSize &&
                !isFinished &&
                mItems.size >= mConfig!!.preFetchCount &&
                layoutManager.findLastVisibleItemPosition() >= (mItems.size - mConfig!!.preFetchCount)
            ) {
                loadMoreObservable.onNext(LoadMoreState.LOAD)
            }
        }
    }

    override fun <T : DomainObject> addItem(index: Int, item: T) {
        this.mItems.add(index, item)
        notifyItemInserted(index)
    }

    override fun <T : DomainObject> addItem(item: T) {
        this.mItems.add(item)
        notifyItemInserted(mItems.size - 1)
    }

    override fun <T : DomainObject> addItems(items: MutableList<T>) {
        removeAll()
        this.mItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun addItemsList(items: List<*>) {
        removeAll()
        items.forEach {
            if (it is DomainObject)
                this.mItems.add(it)
        }
        notifyDataSetChanged()
    }

    override fun <T : DomainObject> addItemsWithoutClear(items: MutableList<T>) {
        this.mItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun remove(index: Int) {
        if (mItems.size > 0) {
            mItems.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    override fun removeAll() {
        mItems.clear()
    }

    fun getLoadMoreObservable() = loadMoreObservable

    private fun subscribeLoadMore() {
        disposable.add(loadMoreObservable
            .subscribe {
                loadMoreState = it
                if (mConfig?.showLoadMore != false) {
                    if (loadMoreState == LoadMoreState.LOAD && mItems[mItems.size - 1] !is LoadMoreObject) {
                        addLoadMoreToList()
                    } else if (loadMoreState == LoadMoreState.NOT_LOAD && mItems[mItems.size - 1] is LoadMoreObject) {
                        removeLoadMoreFromList()
                    } else if (loadMoreState == LoadMoreState.FINISH) {
                        isFinished = true
                        removeLoadMoreFromList()
                    }
                }
            })
    }

    private val handler = Handler()

    private fun addLoadMoreToList() {
        if (loadMoreState != LoadMoreState.FINISH)
            handler.post(object : Runnable {
                override fun run() {
                    mItems.add(LoadMoreObject())
                    notifyItemInserted(mItems.size - 1)
                    handler.removeCallbacks(this)
                }
            })
    }

    private fun removeLoadMoreFromList() {
        handler.post(object : Runnable {
            override fun run() {
                val position = mItems.size - 1
                mItems.removeAt(position)
                notifyItemRemoved(position)
                handler.removeCallbacks(this)
            }
        })
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.layoutManager = recyclerView.layoutManager as LinearLayoutManager

        recyclerView.addOnScrollListener(MyScrollListener { loadMore() })
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        disposable.clear()
    }

    class Config private constructor(
        val preFetchCount: Int,
        val screenSize: Int,
        val showLoadMore: Boolean
    ) {
        class Builder {
            private var prefetchDistance = 0
            private var screenSize = 0
            private var showLoadMore = true
            fun setPreFetch(size: Int) = apply { prefetchDistance = size }
            fun setScreenSize(size: Int) = apply { screenSize = size }
            fun setShowLoadMore(showLoadMore: Boolean) = apply { this.showLoadMore = showLoadMore }
            fun build() = Config(prefetchDistance, screenSize, showLoadMore)
        }
    }

}