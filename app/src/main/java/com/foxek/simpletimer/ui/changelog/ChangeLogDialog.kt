package com.foxek.simpletimer.ui.changelog

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.foxek.simpletimer.R
import com.foxek.simpletimer.data.model.Changelog
import com.foxek.simpletimer.ui.base.BaseDialog
import kotlinx.android.synthetic.main.dialog_changelog.*

class ChangeLogDialog : BaseDialog() {

    override var dialogTitle = R.string.dialog_changelog_title

    override fun getLayout() = R.layout.dialog_changelog

    private val changelogAdapter: ChangelogAdapter by lazy { ChangelogAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureChangeLogList()

        changelog_accept_btn.setOnClickListener {
            dismiss()
        }
    }

    private fun configureChangeLogList() {
        changelog_list.apply {
            adapter = changelogAdapter.apply {
                setItems(createChangelog())
            }
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun createChangelog(): List<Changelog> {
        return context!!.resources.run {
            (getStringArray(R.array.dialog_changelog_list)
                .zip(getStringArray(R.array.dialog_changelog_description_list), ::Changelog))

        }
    }

    companion object {
        fun newInstance(): ChangeLogDialog = ChangeLogDialog()
    }
}