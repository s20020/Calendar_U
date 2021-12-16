package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListAdapter
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentStartBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTodayScheduleBinding


class TodayScheduleFragment : BottomSheetDialogFragment() {
    private lateinit var _helper: DatabaseHelper

    private var _binding : FragmentTodayScheduleBinding? = null
    private val binding get() = _binding!!

    var year = 0
    var month = 0
    var day = 0

    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    var list = arrayListOf<List<String>>()

    val args: TodayScheduleFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        inflater.inflate(R.layout.fragment_today_schedule, container, false)
        _binding = FragmentTodayScheduleBinding.inflate(inflater, container, false)

        // 選ばれた日付ごとのタイトルを設定。
        setTodayTitle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // NavHostの取得
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // NavController取得
        val navController = navHostFragment.navController

        binding.newCreate.setOnClickListener {
            //アクション
            val action = TodayScheduleFragmentDirections.actionTodayScheduleFragmentToNewCreateFragment(year, month, day)
            navController.navigate(action)
        }

        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())
        databaseSelect()

    }

    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }

    fun setTodayTitle() {
        year = args.year
        month = args.month
        day = args.day
        binding.todaytitle.text = "${year}年${month}月${day}日の日程"


    }

    fun databaseSelect() {
        val db = _helper.writableDatabase

        val myArray = args.myArray



        var i = 0
        while(i < myArray.size) {

            val select = """
            SELECT title, s_time, e_time, memo FROM SCHEDULE
            WHERE _id = ${myArray[i]}
        """.trimIndent()

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

                val a = listOf<String>(title, s_time, e_time, memo)
                list.add(a)
            }
            i++
        }
        println(list)

        val a = arrayOf("taiga", "akito", "ken")

        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CustomAdapter(a)

        binding.toDoList.layoutManager = viewManager
        binding.toDoList.adapter = viewAdapter




    }

}