package com.healthtunnel.ui.utility

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class Utility {
    companion object {

        fun genderList(): Array<String> {

            return arrayOf("Male", "Female", "Other")
        }

        fun getYearList(): Array<String> {
            var a: MutableList<String> = arrayOf("1955").toMutableList()
            for (i in 56 until 102) {
                val value = 1900 + i
                a.add(value.toString())
            }

            return a.toTypedArray()
        }

        fun getTrueFalse(): Array<String> {
            //return arrayOf("true", "false")
            return arrayOf("Yes", "No")
        }

        fun getGender(): Array<String> {
            return arrayOf("Male", "Female", "Other")
        }

        fun getBloodGroup(): Array<String> {
            return arrayOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        }

        fun getBloodRequestReason(): Array<String> {
            return arrayOf("Accident", "Surgery", "Pregnancy", "Cancer")
        }

        fun getBloodRequestedFor(): Array<String> {
            return arrayOf("Mother", "Father", "Son", "Daughter", "Friend", "Relative", "Others")
        }

        fun getBloodReceiverStatus(): Array<String> {
            return arrayOf("Home", "Hospital")
        }

        fun getYearInYears(selectedVllue: Int): Int? {
            val currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)

            return (currentYear - selectedVllue)
        }

        @JvmStatic
        fun getDistanceInKM(distance: Double): String {

            return (Math.round(distance * 100) / 100).toString()
        }

        fun getDatetime(s: String): String {
            var sdf = SimpleDateFormat("yyyy-M-dd HH:mm:ss")
            var d: Date ?= null
            try {
                d = sdf.parse(s)
            } catch (ex: ParseException) {
            }

            sdf =  SimpleDateFormat("yyyy-M-dd HH:mm:ss");

            return sdf.format(d)

        }

        fun getAmount(quantity: Int, price: Int): Int {

            return quantity*price
        }


    }


    }