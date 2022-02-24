package jp.ac.it_college.std.s20020.calendar_u

import android.content.ContentValues
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentEditBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayScheduleBinding


class EditFragment : Fragment() {

    val minute = arrayOf("00", "05", "15", "20", "25", "30", "35", "40", "45", "50", "55")
    val hour = arrayOf("00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23")


    private lateinit var _helper: DatabaseHelper

    val args: EditFragmentArgs by navArgs()

    private var _binding : FragmentEditBinding? = null
    private val binding get() = _binding!!

    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    var list = arrayListOf<List<String>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflater.inflate(R.layout.fragment_today_schedule, container, false)
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.delete.setOnClickListener{
            databaseDelete()
        }


        //NumberPickerの値をリストでセット
        binding.EsHour.minValue = 0
        binding.EsHour.maxValue = hour.size - 1
        binding.EsHour.displayedValues = hour

        binding.EsMinute.minValue = 0
        binding.EsMinute.maxValue = minute.size -1
        binding.EsMinute.displayedValues = minute

        binding.EeHour.minValue = 0
        binding.EeHour.maxValue = hour.size -1
        binding.EeHour.displayedValues = hour

        binding.EeMinute.minValue = 0
        binding.EeMinute.maxValue = minute.size -1
        binding.EeMinute.displayedValues = minute

        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())

        binding.update.setOnClickListener{
            databaseUpdate()
        }


        binding.Eback.setOnClickListener {
            findNavController().popBackStack()
        }

        println(args.id)

        val select = """
            SELECT title, s_time, e_time, memo FROM SCHEDULE
            WHERE _id = ${args.id}
        """.trimIndent()

        val db = _helper.writableDatabase


        val c = db.rawQuery(select, null)

        while(c.moveToNext()){

            val cTitle = c.getColumnIndex("title")
            title = c.getString(cTitle)
            val cStime = c.getColumnIndex("s_time")
            s_time = c.getString(cStime)
            val cEtime = c.getColumnIndex("e_time")
            e_time = c.getString(cEtime)
            val cMemo = c.getColumnIndex("memo")
            memo = c.getString(cMemo)


        }



        binding.Etitle.setText(title)
        binding.Ememo.setText(memo)
    }


    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    fun databaseDelete() {
        val db = _helper.writableDatabase
        db.delete("SCHEDULE", "_id = ?", arrayOf(args.id.toString()))
        findNavController().popBackStack()
    }

    fun databaseUpdate() {
        val values = ContentValues()
        val title = binding.Etitle.text.toString()
        val memo = binding.Ememo.text.toString()

        val s_time = "${hour[binding.EsHour.value]}:${minute[binding.EsMinute.value]}"
        val e_time = "${hour[binding.EeHour.value]}:${minute[binding.EeMinute.value]}"

        values.put("title", title)
        values.put("memo", memo)
        values.put("s_time", s_time)
        values.put("e_time", e_time)

        val db = _helper.writableDatabase
        db.update("SCHEDULE", values, "_id = ?", arrayOf(args.id.toString()))
        findNavController().popBackStack()
    }

}