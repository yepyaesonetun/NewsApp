package com.ypst.primeyz.newsapp.persistence.typeconverters

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ypst.primeyz.newsapp.data.vos.DummySourceVO

/**
 * Created by yepyaesonetun on 11/25/18.
 **/
class DummySourceTypeConverter{

    @TypeConverter
    fun toString(dummySourceVO: DummySourceVO): String {
        return Gson().toJson(dummySourceVO)
    }

    @TypeConverter
    fun toAuthor(dummySourceJson : String) : DummySourceVO {
        val authorType = object : TypeToken<DummySourceVO>(){}.type
        return Gson().fromJson(dummySourceJson, authorType)
    }
}