package com.foxek.simpletimer.presentation.editworkout

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class ItemTouchHelperCallback(
    private val moveListener: RoundMoveListener
) : ItemTouchHelper.Callback() {

    private var positionFrom = -1

    private var positionTo = -1

    override fun isItemViewSwipeEnabled(): Boolean = false

    override fun isLongPressDragEnabled(): Boolean = true

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int = makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)

    override fun onMove(
        recyclerView: RecyclerView,
        current: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        if (positionFrom == -1) {
            positionFrom = current.adapterPosition
        }
        positionTo = target.adapterPosition

        moveListener.onRoundMove(positionFrom, positionTo)
        return true
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)

        if (positionFrom != -1 && positionTo != -1 && positionFrom != positionTo) {
            moveListener.onRoundMoved(positionFrom, positionTo)
        }
        positionFrom = -1
        positionTo = -1
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
}