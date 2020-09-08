package com.tom.funfacts

import androidx.room.Entity

@Entity
data class EmployeeInfo(val id : Int,
                        val name : String,
                        val salary : Int,
                        val age : Int){

}