package jp.ac.it_college.std.s20020.calendar_u

import android.app.ActionBar
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentNewCreateBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTemplateBinding
import kotlin.io.path.createTempDirectory

class TemplateFragment : Fragment(){
    private lateinit var _helper: DatabaseHelper

    private var _binding : FragmentTemplateBinding? = null
    private val binding get() = _binding!!


    var _id : Long = 0
    var date = ""
    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    //データをスケジュールデータベースに格納する命令。
    val insert = """
            INSERT INTO SCHEDULE
            (_id, date, title, s_time, e_time, memo)
            VALUES(?, ?, ?, ?, ?, ?)
        """.trimIndent()

    var list = arrayListOf<List<String>>()

    val args: TemplateFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTemplateBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //DatabaseHelperオブジェクトを生成
        _helper = DatabaseHelper(requireContext())

        binding.AddTemplate.setOnClickListener {
            // NavHostの取得
            val navHostFragment =
                requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            // NavController取得
            val navController = navHostFragment.navController

            //アクション
            val action = TemplateFragmentDirections.actionTemplateFragmentToCreateTemplate()
            navController.navigate(action)



        }

        binding.tBack.setOnClickListener{
            findNavController().popBackStack()
        }

        //テンプレートを表示
        templateShow()


    }

    fun templateShow() {
        val db = _helper.writableDatabase


        val select = """
            SELECT _id, title, s_time, e_time, memo FROM TEMPLATE
        """.trimIndent()

        val c = db.rawQuery(select, null)

        while(c.moveToNext()){

            val c_id = c.getColumnIndex("_id")
            _id = c.getLong(c_id)
            val cTitle = c.getColumnIndex("title")
            title = c.getString(cTitle)
            val cStime = c.getColumnIndex("s_time")
            s_time = c.getString(cStime)
            val cEtime = c.getColumnIndex("e_time")
            e_time = c.getString(cEtime)
            val cMemo = c.getColumnIndex("memo")
            memo = c.getString(cMemo)

            val a = listOf<String>(_id.toString(), title, s_time, e_time, memo)
            list.add(a)
        }

        println("taiga")

        println(list)



        val cc = mutableListOf<Map<String, String>>()

        var i = 0
        while(i < list.size) {
            val map = mapOf<String, String>("title" to list[i][1], "date" to "${list[i][2]}〜${list[i][3]}")
            cc.add(map)

            i++
        }

        println(cc)


        val viewManager = LinearLayoutManager(context)
        val viewAdapter = CustomAdapter(cc)

        binding.tempList.layoutManager = viewManager
        binding.tempList.adapter = viewAdapter

        viewAdapter.setCallback {
            databaseInsert(it)
        }





    }

    //SCHEDULEデータベースに値を入れる
    fun databaseInsert(position: Int) {
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
        title = list[position][1]
        s_time = list[position][2]
        e_time = list[position][3]
        memo = list[position][4]

        //データベース接続オブジェクトを取得
        val db = _helper.writableDatabase


        //SQL文字列をもとにプリペアドステートメントを取得
        val stmt = db.compileStatement(insert)


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