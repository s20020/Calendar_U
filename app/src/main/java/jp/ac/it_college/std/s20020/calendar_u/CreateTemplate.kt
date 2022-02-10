package jp.ac.it_college.std.s20020.calendar_u

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentCreateTemplateBinding

class CreateTemplate : Fragment() {

    private lateinit var _helper: DatabaseHelper

    private var _binding: FragmentCreateTemplateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateTemplateBinding.inflate(inflater, container, false)
        return binding.root
    }


    val minute = arrayOf("00", "05", "15", "20", "25", "30", "35", "40", "45", "50", "55")
    val hour = arrayOf(
        "00",
        "01",
        "02",
        "03",
        "04",
        "05",
        "06",
        "07",
        "08",
        "09",
        "10",
        "11",
        "12",
        "13",
        "14",
        "15",
        "16",
        "17",
        "18",
        "19",
        "20",
        "21",
        "22",
        "23"
    )

    var _id: Long = 0
    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //NumberPickerの値をリストでセット
        binding.TsHour.minValue = 0
        binding.TsHour.maxValue = hour.size - 1
        binding.TsHour.displayedValues = hour

        binding.TsMinute.minValue = 0
        binding.TsMinute.maxValue = minute.size - 1
        binding.TsMinute.displayedValues = minute

        binding.TeHour.minValue = 0
        binding.TeHour.maxValue = hour.size - 1
        binding.TeHour.displayedValues = hour

        binding.TeMinute.minValue = 0
        binding.TeMinute.maxValue = minute.size - 1
        binding.TeMinute.displayedValues = minute

        val insert = """
            INSERT INTO TEMPLATE
            (_id, title, s_time, e_time, memo)
            VALUES(?, ?, ?, ?, ?)
        """.trimIndent()


        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())

        binding.Tapply.setOnClickListener{
            dataInsert(insert)
            findNavController().popBackStack()
        }

        binding.Tback.setOnClickListener{
            findNavController().popBackStack()
        }



    }

    override fun onDestroy() {
        //DBヘルパーオブジェクトの解放
        _helper.close()
        super.onDestroy()
    }


    //新しく作成したテンプレートをデータベースに格納
    fun dataInsert(insert: String) {
        //_idの連番のための保存領域インスタンスを生成。
        val sharedPref = this.requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()

        //insertが実行されるたびに_idを+1
        val a = sharedPref.getLong("INDEX_P", 0).plus(1)
        editor.putLong("INDEX_P", a)
        editor.apply()

        _id = a
        title = binding.TTitle.text.toString()
        s_time = "${hour[binding.TsHour.value]}:${minute[binding.TsMinute.value]}"
        e_time = "${hour[binding.TeHour.value]}:${minute[binding.TeMinute.value]}"
        memo = binding.Tmemo.text.toString()

        //データベース接続オブジェクトを取得
        val db = _helper.writableDatabase


        //SQL文字列をもとにプリペアドステートメントを取得
        val stmt = db.compileStatement(insert)


        stmt.bindLong(1, _id)
        stmt.bindString(2, title)
        stmt.bindString(3, s_time)
        stmt.bindString(4,e_time)
        stmt.bindString(5, memo)

        //データベースへのinsert実行。
        stmt.executeInsert()

    }
}