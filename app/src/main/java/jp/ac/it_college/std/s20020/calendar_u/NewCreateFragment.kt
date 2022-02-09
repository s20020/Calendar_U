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
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentNewCreateBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding
import java.util.*


class NewCreateFragment : Fragment() {
    val minute = arrayOf("00", "05", "15", "20", "25", "30", "35", "40", "45", "50", "55")
    val hour = arrayOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")
    var _id: Long = 0
    var date = ""
    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    val args: NewCreateFragmentArgs by navArgs()


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
        //NumberPickerの値をリストでセット
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

        //完了ボタン
        binding.newCreateOk.setOnClickListener {
            dateInsert(insert)
            findNavController().popBackStack()




        }

        //戻るボタン
        binding.Back.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.SelectTemplete.setOnClickListener{
            // NavHostの取得
            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            // NavController取得
            val navController = navHostFragment.navController

            //アクション
            val action = NewCreateFragmentDirections.actionNewCreateFragmentToTemplateFragment()
            navController.navigate(action)

        }


    }

    override fun onDestroy() {
        //DBヘルパーオブジェクトの解放
        _helper.close()
        super.onDestroy()
    }

    //完了ボタン 新規日程をデータベースへ格納
    fun dateInsert(insert: String) {

        //_idの連番のための保存領域インスタンスを生成。
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //insertが実行されるたびに_idを+1
        val a = sharedPref.getLong("INDEX", 0).plus(1)
        editor.putLong("INDEX", a)
        editor.apply()

        println(a)


        //month,dayを０で穴埋め
        val month = args.month.toString().padStart(2,'0')
        val day = args.day.toString().padStart(2,'0')



        //データベース各項目の値を取得
        _id = a
        date = "${args.year}-${month}-${day}"
        title = binding.Name.text.toString()
        s_time = "${hour[binding.sHour.value]}:${minute[binding.sMinute.value]}"
        e_time = "${hour[binding.eHour.value]}:${minute[binding.eMinute.value]}"
        memo = binding.Memo.text.toString()

        //データベース接続オブジェクトを取得
        val db = _helper.writableDatabase


        //SQL文字列をもとにプリペアドステートメントを取得
        val stmt = db.compileStatement(insert)

        //
        stmt.bindLong(1, _id)
        stmt.bindString(2, date)
        stmt.bindString(3, title)
        stmt.bindString(4, s_time)
        stmt.bindString(5,e_time)
        stmt.bindString(6, memo)

        //データベースへのinsert実行。
        stmt.executeInsert()
    }


}

