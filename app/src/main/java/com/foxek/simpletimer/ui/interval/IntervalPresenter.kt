package com.foxek.simpletimer.ui.interval

import com.foxek.simpletimer.data.model.Interval
import com.foxek.simpletimer.ui.base.BasePresenter

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class IntervalPresenter @Inject constructor(
        val interactor: IntervalContact.Interactor
) : BasePresenter<IntervalContact.View>(), IntervalContact.Presenter {

    override fun viewIsReady() {}

    override fun viewIsReady(id: Int) {
        view?.setIntervalList()

        disposable.add(interactor
                .getWorkout(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.setWorkoutName(it.name!!)
                    view?.setVolumeState(it.isVolume)
                }, {

                })
        )

        disposable.add(interactor
                .fetchIntervalList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view?.renderIntervalList(it)
                }, {

                })
        )
    }

    override fun editWorkoutButtonClicked() {
        view?.showWorkoutEditDialog()
    }

    override fun changeVolumeButtonClicked() {
        disposable.add(interactor
                .getVolume()
                .observeOn(AndroidSchedulers.mainThread())
                .flatMapCompletable { isEnabled ->
                    view?.setVolumeState(!isEnabled)
                    interactor.updateVolume(!isEnabled)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        )
    }

    override fun addIntervalButtonClicked() {
        view?.showIntervalCreateDialog()
    }

    override fun saveIntervalButtonClicked(name: String, type:Int, workTime: Int, restTime: Int) {
        disposable.add(interactor.updateInterval(name, type, workTime, restTime))
    }

    override fun createIntervalButtonClicked(name: String, type:Int, workTime: Int, restTime: Int) {
        disposable.add(interactor.addInterval(name, type, workTime, restTime))
    }

    override fun deleteIntervalButtonClicked() {
        disposable.add(interactor.deleteInterval())
    }

    override fun saveWorkoutButtonClicked(name: String) {
        disposable.add(interactor.updateWorkout(name))
        view?.setWorkoutName(name)
    }

    override fun deleteWorkoutButtonClicked() {
        disposable.add(interactor
                .deleteWorkout()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ view?.startWorkoutActivity() }, {})
        )
    }

    override fun startWorkoutButtonClicked() {
        view?.startTimerActivity()
    }

    override fun intervalItemClicked(item: Interval) {
        interactor.setCurrentInterval(item.id)
        view?.showIntervalEditDialog(item)
    }
}