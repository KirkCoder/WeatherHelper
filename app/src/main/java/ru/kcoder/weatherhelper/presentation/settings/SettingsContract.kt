package ru.kcoder.weatherhelper.presentation.settings

import ru.kcoder.weatherhelper.presentation.common.BaseInteractor
import ru.kcoder.weatherhelper.presentation.common.BaseViewModel

interface SettingsContract {
    interface Intractor: BaseInteractor

    abstract class ViewModel(
        interactor: Intractor
    ): BaseViewModel(interactor)
}