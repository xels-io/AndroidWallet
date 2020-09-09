package io.xels.xelsandroidapp.ulits

import android.content.Context
import org.json.JSONObject

object PreferenceManager {
    var instance: PreferenceManager? = null
        private set
    private var m_context: Context? = null

    fun setInstance(context: Context) {
        if (instance == null) {
            m_context = context
            instance = PreferenceManager
        }
    }

    fun updateValue(key: String, value: Boolean?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putBoolean(key, value)
        editor.apply()
    }

    fun updateValue(key: String, value: String?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putString(key, value)


        editor.apply()
    }

    fun updateValue(key: String, value: JSONObject?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putString(key, value.toString())
        editor.apply()
    }

    fun updateValue(key: String, value: Int?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putInt(key, value)
        editor.apply()

    }

    fun updateValue(key: String, value: Long?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val editor = preferences.edit()
        if (value == null)
            editor.remove(key)
        else
            editor.putLong(key, value)
        editor.apply()

    }

    fun getInt(key: String): Int {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }

    fun getLong(key: String): Long {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getLong(key, 0)
    }

    fun getString(key: String): String {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getString(key, "")!!
    }

    @JvmOverloads
    fun getBool(key: String, def: Boolean = false): Boolean {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getBoolean(key, def)
    }

    fun updateValue(key: String, value: Set<String>?) {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        if (value != null) {
            preferences.edit().putStringSet(key, value).apply()
        } else {
            preferences.edit().remove(key).apply()
        }
    }

    fun getStringSet(key: String): Set<String>? {
        val preferences = m_context!!.getSharedPreferences(AppConstance.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        return preferences.getStringSet(key, null)
    }

}