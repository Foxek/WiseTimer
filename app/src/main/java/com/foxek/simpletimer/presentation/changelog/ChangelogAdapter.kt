package com.foxek.simpletimer.presentation.changelog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Changelog
import com.foxek.simpletimer.presentation.base.BaseAdapter
import kotlinx.android.synthetic.main.item_changelog.view.*

class ChangelogAdapter : BaseAdapter<Changelog, ChangelogAdapter.ChangelogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChangelogViewHolder {
        return with(parent) {
            LayoutInflater.from(context).inflate(R.layout.item_changelog, parent, false)
        }.run(::ChangelogViewHolder)
    }

    inner class ChangelogViewHolder(view: View) : BaseViewHolder<Changelog>(view) {
        override fun bind(model: Changelog) {
            with(itemView) {
                changelog_title.text = model.title
                changelog_description.text = model.description
            }
        }
    }
}
