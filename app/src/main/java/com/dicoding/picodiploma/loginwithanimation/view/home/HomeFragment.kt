package com.dicoding.picodiploma.loginwithanimation.view.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.R
import com.dicoding.picodiploma.loginwithanimation.data.ResultState
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignWordCategory.DetailSignWordCategoryActivity
import com.dicoding.picodiploma.loginwithanimation.data.SignCategory
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserModel
import com.dicoding.picodiploma.loginwithanimation.data.remote.response.ListKategoriItem
import com.dicoding.picodiploma.loginwithanimation.databinding.FragmentHomeBinding
import com.dicoding.picodiploma.loginwithanimation.view.DetailSignLanguage.DetailSignLanguageActivity
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "token"
private const val ARG_PARAM2 = "namaUser"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    val listGambar = listOf(R.drawable.angka_logo, R.drawable.abc_logo)

    private val signCategoryViewModel: SignCategoryViewModel by activityViewModels() {
        ViewModelFactory.getInstance(requireActivity())
    }


    private lateinit var binding: FragmentHomeBinding

    // TODO: Rename and change types of parameters
    private var token: String? = null
    private var namaUser: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            token = it.getString(ARG_PARAM1)
            namaUser = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvCategory.layoutManager = layoutManager

        binding.tvUsername.text = "Hai, "+namaUser

        //hilangkan garis pemisah
//        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
//        binding.rvCategory.addItemDecoration(itemDecoration)

        //set recycler view
        signCategoryViewModel.getAllKategori(token!!).observe(viewLifecycleOwner) { result ->
            run {
                if (result != null) {
                    when (result) {
                        is ResultState.Loading -> {
                            showLoading(true)
                        }

                        is ResultState.Success -> {
                            val message = "Berhasil Perbarui Data"
                            val listKategori = result.data.listKategori
                            val signCategory = listKategori.mapIndexed { index, element ->
                                SignCategory(listGambar[index], "", element.namaKategori!!, 10)
                            }
                            setSignCategoryData(signCategory)
                            showToast(message)
                            showLoading(false)

                        }

                        is ResultState.Error -> {
                            showToast(result.error)
                            showLoading(false)
                        }
                    }
                }
            }

//        setSignCategoryData(listCourseSignCategory)
        }

        SignCategoryAdapter.setOnItemClickCallback(object :
            SignCategoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: SignCategory) {
                showSelectedCategory(data)
            }
        })
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param token Parameter 1.
         * @param namaUser Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(token: String, namaUser: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, token)
                    putString(ARG_PARAM2, namaUser)
                }
            }
    }

    private fun setSignCategoryData(signCategory: List<SignCategory>) {
        val adapter = SignCategoryAdapter()
        adapter.submitList(signCategory)
        binding.rvCategory.adapter = adapter
    }

    private fun showSelectedCategory(signCategory: SignCategory) {
        if (signCategory.titleCategory == "Kata") {
            val intentWithStringData = Intent(requireActivity(), DetailSignWordCategoryActivity::class.java)
            intentWithStringData.putExtra(DetailSignWordCategoryActivity.TOKEN_KEY, token)
            requireActivity().startActivity(intentWithStringData)
        } else {
            val intent = Intent(requireActivity(), DetailSignLanguageActivity::class.java)
            requireActivity().startActivity(intent)
        }


//        Toast.makeText(requireActivity(), "Kamu memilih " + signCategory.titleCategory, Toast.LENGTH_SHORT).show()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}