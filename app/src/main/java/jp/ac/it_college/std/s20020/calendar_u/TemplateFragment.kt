package jp.ac.it_college.std.s20020.calendar_u

import android.app.ActionBar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.SupportActionModeWrapper
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentNewCreateBinding
import jp.ac.it_college.std.s20020.calendar_u.databinding.FragmentTemplateBinding
import kotlin.io.path.createTempDirectory

class TemplateFragment : Fragment(){
    private lateinit var _helper: DatabaseHelper

    private var _binding : FragmentTemplateBinding? = null
    private val binding get() = _binding!!





    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTemplateBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
    }
}