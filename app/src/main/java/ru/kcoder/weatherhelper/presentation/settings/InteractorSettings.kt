package ru.kcoder.weatherhelper.presentation.settings

import ru.kcoder.weatherhelper.data.reposiries.settings.SettingsRepository
import ru.kcoder.weatherhelper.domain.common.AbstractInteractor

class InteractorSettings(
    settingsRepository: SettingsRepository
) : AbstractInteractor(settingsRepository), SettingsContract.Intractor {


}