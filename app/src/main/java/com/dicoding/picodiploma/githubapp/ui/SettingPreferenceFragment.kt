package com.dicoding.picodiploma.githubapp.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreference
import com.dicoding.picodiploma.githubapp.R
import com.dicoding.picodiploma.githubapp.receiver.AlarmReceiver

class SettingPreferenceFragment : PreferenceFragmentCompat(),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var reminder: String
    private lateinit var language: String

    private lateinit var reminderPreferences: SwitchPreference
    private lateinit var languagePreference: Preference
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.setting_preferences)

        init()

        languagePreference.setOnPreferenceClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
            return@setOnPreferenceClickListener true
        }
    }

    private fun init() {
        reminder = resources.getString(R.string.key_reminder)
        language = resources.getString(R.string.key_language)

        reminderPreferences = findPreference<SwitchPreference>(reminder) as SwitchPreference
        languagePreference = findPreference<Preference>(language) as Preference

        alarmReceiver = AlarmReceiver()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {

        if (key == reminder) {
            reminderPreferences.isChecked = sharedPreferences.getBoolean(reminder, false)
        }

        val state: Boolean = PreferenceManager.getDefaultSharedPreferences(context)
            .getBoolean(key, false)
        setReminder(state)
    }

    private fun setReminder(state: Boolean) {
        when (state) {
            true -> alarmReceiver.setReminder(
                context, "09:00",
                getString(R.string.reminder_message)
            )
            false -> alarmReceiver.cancelReminder(context)
        }
    }
}