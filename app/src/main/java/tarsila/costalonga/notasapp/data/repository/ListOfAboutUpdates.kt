package tarsila.costalonga.notasapp.data.repository

import tarsila.costalonga.notasapp.R

object ListOfAboutUpdates {

    fun getAboutUpdatesList(): List<TimelineEvent> {
        val list = mutableListOf<TimelineEvent>()

        list.add(TimelineEvent(1, R.string.about_aug2020, R.string.about_aug2020_done))
        list.add(TimelineEvent(2, R.string.about_sept2020, R.string.about_sept2020_done))
        list.add(TimelineEvent(3, R.string.about_oct2020, R.string.about_oct2020_done))
        list.add(TimelineEvent(4, R.string.about_jul2022, R.string.about_jul2022_done))
        list.add(TimelineEvent(5, R.string.about_aug2022, R.string.about_aug2022_done))
        list.add(TimelineEvent(6, R.string.about_oct2022, R.string.about_oct2022_done))
        list.add(TimelineEvent(7, R.string.about_dec2022, R.string.about_dec2022_done))
        list.add(TimelineEvent(8, R.string.about_jun2023, R.string.about_jun2023_done))
        list.add(TimelineEvent(9, R.string.about_mar2024, R.string.about_mar2024_done))
        list.add(TimelineEvent(10, R.string.about_apr2024, R.string.about_apr2024_done))
        list.add(TimelineEvent(11, R.string.about_aug2024, R.string.about_aug2024_done))
        list.add(TimelineEvent(12, R.string.about_dec2024, R.string.about_dec2024_done))
        list.add(TimelineEvent(13, R.string.about_feb2025, R.string.about_feb2025_done))
        list.add(TimelineEvent(14, R.string.about_aug2026, R.string.about_aug2026_done))

        return list
    }
}
