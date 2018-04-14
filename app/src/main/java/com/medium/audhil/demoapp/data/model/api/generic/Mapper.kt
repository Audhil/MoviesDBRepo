package com.medium.audhil.demoapp.data.model.api.generic

/*
 * Created by mohammed-2284 on 11/04/18.
 */

interface Mapper<out OUT, in IN> {
    fun map(input: IN): OUT
}