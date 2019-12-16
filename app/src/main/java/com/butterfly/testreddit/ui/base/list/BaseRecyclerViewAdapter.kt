package com.butterfly.testreddit.ui.base.list

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView

/**
 * Base adapter for recycler view.
 */
abstract class BaseRecyclerViewAdapter<TData,
        TViewHolder : RecyclerView.ViewHolder>(context: Context, data: List<TData> = listOf()) :
    RecyclerView.Adapter<TViewHolder>() {

    protected val context: Context = context.applicationContext

    protected val inflater: LayoutInflater = LayoutInflater.from(context)

    protected val data: MutableList<TData> = data.toMutableList()

    /**
     * @return the list.
     */
    val all: List<TData>
        get() = data

    /**
     * @return a copy of the list.
     */
    val snapshot: List<TData>
        get() = data.toMutableList()

    /**
     * @return item count of this list.
     */
    override fun getItemCount() = data.size

    /**
     * @return an element from the specified [position] the list.
     */
    @Throws(ArrayIndexOutOfBoundsException::class)
    fun getItem(position: Int): TData = data[position]

    /**
     * @return true if list is empty.
     */
    fun isEmpty() = data.isEmpty()

    /**
     * @return true if list is not empty.
     */
    fun isNotEmpty() = data.isNotEmpty()

    /**
     * Inserts an element into the list.
     */
    fun add(item: TData) = data.add(item)

    /**
     * Replace an element position in this list.
     */
    fun replace(oldPosition: Int, newPosition: Int) = data.add(newPosition, remove(oldPosition))

    /**
     * Replaces the element at the specified [position] in this list with the specified [item].
     *
     * @return the element previously at the specified position.
     */
    operator fun set(position: Int, item: TData): TData = data.set(position, item)

    /**
     * Removes an element at the specified [position] from the list.
     *
     * @return the element that has been removed.
     */
    fun remove(position: Int): TData = data.removeAt(position)

    /**
     * Removes all elements from this list.
     */
    fun clear() {
        data.clear()
    }

    /**
     * Inserts all of the elements in the specified [collection] into this list.
     *
     * @return `true` if the list was changed as the result of the operation.
     */
    fun addAll(collection: Collection<TData>) = data.addAll(collection)
}
