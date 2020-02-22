package com.example.myapplication.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.navigation.fragment.findNavController
import com.example.myapplication.BridegfyVictim.Dao.AppDatabaseInstance
import com.example.myapplication.BridegfyVictim.Dao.user.UserDetail

import com.example.myapplication.R
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        ArrayAdapter.createFromResource(
                this.requireContext(),
                R.array.gender,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
        register_button.setOnClickListener {
            val name = name_edit_text.editableText.toString()
            val phoneNumber = number_edit_text.editableText.toString().toLong()
            val gender = when(spinner.selectedItemPosition) {
                0 -> UserDetail.Gender.MALE
                1 -> UserDetail.Gender.FEMALE
                else -> UserDetail.Gender.NONE
            }
            val emergencyContact1 = contact_1_text.editableText.toString().toLong()
            val emergencyContact2 = contact_2_text.editableText.toString().toLong()

            CoroutineScope(Dispatchers.IO).launch {
                val userDao = AppDatabaseInstance.getDb(requireContext()).userDao()
                userDao.updateUser(UserDetail(
                        userName = name,
                        phoneNumber = phoneNumber,
                        gender = gender,
                        emergencyContact = UserDetail.EmergencyContact(
                                contact1 =  emergencyContact1,
                                contact2 = emergencyContact2
                        )
                ))

                withContext(Dispatchers.Main) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
            }
        }
    }

}
