package jp.ac.it_college.std.s20020.calendar_u

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentCreateTemplateBinding

class CreateTemplate : Fragment() {

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
    var date = ""
    var title = ""
    var s_time = ""
    var e_time = ""
    var memo = ""

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //NumberPickerの値をリストでセット
        binding.sHour2.minValue = 0
        binding.sHour2.maxValue = hour.size - 1
        binding.sHour2.displayedValues = hour

        binding.sMinute2.minValue = 0
        binding.sMinute2.maxValue = minute.size - 1
        binding.sMinute2.displayedValues = minute

        binding.eHour2.minValue = 0
        binding.eHour2.maxValue = hour.size - 1
        binding.eHour2.displayedValues = hour

        binding.eMinute2.minValue = 0
        binding.eMinute2.maxValue = minute.size - 1
        binding.eMinute2.displayedValues = minute

    }
}