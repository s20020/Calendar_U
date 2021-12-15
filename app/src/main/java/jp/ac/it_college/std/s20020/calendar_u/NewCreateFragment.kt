package jp.ac.it_college.std.s20020.calendar_u

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentNewCreateBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding
import java.util.*


class NewCreateFragment : Fragment() {
    val minute = arrayOf("00", "05", "15", "20", "25", "30", "35", "40", "45", "50", "55")
    val hour = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")
    var _id: Long = 0
    var date = ""
    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""


    private lateinit var _helper: DatabaseHelper

    private var _binding : FragmentNewCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewCreateBinding.inflate(inflater, container, false)
        return binding.root

    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.sHour.minValue = 0
        binding.sHour.maxValue = hour.size - 1
        binding.sHour.displayedValues = hour


        binding.sMinute.minValue = 0
        binding.sMinute.maxValue = minute.size -1
        binding.sMinute.displayedValues = minute


        binding.eHour.minValue = 0
        binding.eHour.maxValue = hour.size -1
        binding.eHour.displayedValues = hour

        binding.eMinute.minValue = 0
        binding.eMinute.maxValue = minute.size -1
        binding.eMinute.displayedValues = minute

        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())


        val insert = """
            INSERT INTO SCHEDULE
            (_id, date, title, s_time, e_time, memo)
            VALUES(?, ?, ?, ?, ?, ?)
        """.trimIndent()

        binding.newCreateOk.setOnClickListener{
            dateInsert(insert)
        }



    }

    override fun onDestroy() {
        //DBヘルパーオブジェクトの解放
        _helper.close()
        super.onDestroy()
    }

    fun dateInsert(insert: String) {

    val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)

//        val editor = sharedPref.edit()
//        editor.putLong("INDEX", 1)
//        editor.apply()


        println(sharedPref.getLong("INDEX", 0).plus(1) )

         val a = sharedPref.getLong("INDEX", 0)


        _id = a + 1
        date = "taiga"
        title = binding.Name.text.toString()
        s_time = "${binding.sHour.value}:${binding.sMinute.value}"
        e_time = "${binding.eHour.value}:${binding.eMinute.value}"
        memo = binding.Memo.text.toString()

        //データベース接続オブジェクトを取得
        val db = _helper.writableDatabase


        val stmt = db.compileStatement(insert)

        stmt.bindLong(1, _id)
        stmt.bindString(2, date)
        stmt.bindString(3, title)
        stmt.bindString(4, s_time)
        stmt.bindString(5,e_time)
        stmt.bindString(6, memo)

        stmt.executeInsert()
    }



}


