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

    }
}