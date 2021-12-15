package jp.ac.it_college.std.s20020.calendar_u

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
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
    var i: Long= 0
    var id: Long = 0
    var date = ""
    var title = ""
    var s_hour: Long = 0
    var s_minute: Long = 0
    var e_hour: Long = 0
    var e_minute: Long = 0
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
        binding.sHour.maxValue = 23
        binding.sMinute.minValue = 0
        binding.sMinute.maxValue = 59

        binding.eHour.minValue = 0
        binding.eHour.maxValue = 23
        binding.eMinute.minValue = 0
        binding.eMinute.maxValue = 59


        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())


        val insert = """
            INSERT INTO SCHEDULE
            (_id, date, title, s_hour, s_minute, e_hour, e_minute, memo)
            VALUES(?, ?, ?, ?, ?, ?, ?, ?)
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

        id = i+1
        date = "taiga"
        title = binding.Name.text.toString()
        s_hour = binding.sHour.value.toLong()
        s_minute = binding.sMinute.value.toLong()
        e_hour = binding.sHour.value.toLong()
        e_minute = binding.sMinute.value.toLong()
        memo = binding.Memo.text.toString()

        //データベース接続オブジェクトを取得
        val db = _helper.writableDatabase


        val stmt = db.compileStatement(insert)

        stmt.bindLong(1, id)
        stmt.bindString(2, date)
        stmt.bindString(3, title)
        stmt.bindLong(4, s_hour)
        stmt.bindLong(5,s_minute)
        stmt.bindLong(6, e_hour)
        stmt.bindLong(7, e_minute)
        stmt.bindString(8, memo)
    }
}


